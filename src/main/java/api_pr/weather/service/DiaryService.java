package api_pr.weather.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import api_pr.weather.WeatherApplication;
import api_pr.weather.domain.DateWeather;
import api_pr.weather.domain.Diary;
import api_pr.weather.repository.DateWeatherRepository;
import api_pr.weather.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {

	private final DiaryRepository diaryRepository;
	private final DateWeatherRepository dateWeatherRepository;

	@Value("${openweathermap.key}")
	private String apiKey;

	@Scheduled(cron = "0 0 1 * * *") // 매일 새벽 1시 저장
	@Transactional
	public void saveDailyWeatherDate() {
		DateWeather weatherFromApi = getWeatherFromApi();
		dateWeatherRepository.save(weatherFromApi);
	}

	private static final Logger logger = LoggerFactory.getLogger(WeatherApplication.class);


	private DateWeather getWeatherFromApi() {
		String weatherData = getWeatherString();
		HashMap<String, Object> weather = parsingWeather(weatherData);
		DateWeather dateWeather = new DateWeather();
		dateWeather.setDate(LocalDate.now());
		dateWeather.setWeather(weather.get("main").toString());
		dateWeather.setIcon(weather.get("icon").toString());
		dateWeather.setTemperature((Double)weather.get("temp"));
		return dateWeather;
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void createDiary(LocalDate localDate, String text) {
		logger.info("starting to create diary");
		// diaryRepository.save(diary);
		// String weatherData = getWeatherString();
		// System.out.println("weatherData = " + weatherData);
		// //받아온 날씨 json 파싱
		// HashMap<String, Object> weather = parsingWeather(weatherData);

		DateWeather dateWeather = getDateWeather(localDate);

		//파싱된 데이터 + 일기 값 우리 db에 넣기
		Diary diary = new Diary();
		diary.setDateWeather(dateWeather);
		diary.setText(text);
		diaryRepository.save(diary);
		logger.info("end creating diary");
	}

	private DateWeather getDateWeather(LocalDate date) {
		List<DateWeather> allByDate = dateWeatherRepository.findAllByDate(date);
		if (allByDate.isEmpty()) {
			return getWeatherFromApi();
		} else {
			return allByDate.get(0);
		}
	}

	public List<Diary> readDiary(LocalDate localDate) {
		logger.info("read diary");
		return diaryRepository.findAllByLocalDate(localDate);
	}

	public List<Diary> readDiaryList(LocalDate startDate, LocalDate endDate) {
		return diaryRepository.findAllByLocalDateBetween(startDate, endDate);
	}

	@Transactional
	public Diary updateDiary(LocalDate localDate, String text) {
		Diary firstByLocalDate = diaryRepository.getFirstByLocalDate(localDate);
		firstByLocalDate.setText(text);
		return diaryRepository.save(firstByLocalDate);
	}

	@Transactional
	public void deleteDiary(LocalDate localDate) {
		Diary firstByLocalDate = diaryRepository.getFirstByLocalDate(localDate);
		diaryRepository.delete(firstByLocalDate);
	}

	private HashMap<String, Object> parsingWeather(String jsonString) {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject;
		try {
			jsonObject = (JSONObject)jsonParser.parse(jsonString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		HashMap<String, Object> resultMap = new HashMap<>();

		JSONObject main = (JSONObject)jsonObject.get("main");
		Object temp = main.get("temp");

		resultMap.put("temp", temp);

		JSONArray weather = (JSONArray)jsonObject.get("weather");

		JSONObject weatherData = (JSONObject)weather.get(0);

		resultMap.put("main", weatherData.get("main"));
		resultMap.put("icon", weatherData.get("icon"));
		return resultMap;
	}

	private String getWeatherString() {
		String apiCall = "https://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=" + apiKey;
		System.out.println("apiCall = " + apiCall);

		try {
			URL url = new URL(apiCall);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {  // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			return response.toString();

		} catch (Exception e) {
			return "failed to api call";
		}
	}
}
