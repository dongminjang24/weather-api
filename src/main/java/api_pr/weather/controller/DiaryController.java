package api_pr.weather.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api_pr.weather.domain.Diary;
import api_pr.weather.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DiaryController {

	private final DiaryService diaryService;

	@Operation(
		summary = "diary 생성 api: 일기 텍스트와 날씨를 이용해서 DB에 일기 저장"
	)
	@PostMapping("/diary")
	public void createDiary(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Parameter(description = "날짜 형식: yyyy-mm-dd",example = "2024-09-03") LocalDate localDate,

		@RequestBody String text) {
		diaryService.createDiary(localDate, text);
	}

	@Operation(
		summary = "diary 읽기 api: 선택한 날짜의 모든 일기 데이터를 가져옵니다."
	)
	@GetMapping("/diary")
	public List<Diary> readDiary(
		@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@Parameter(description = "날짜 형식: yyyy-mm-dd",example = "2024-09-03") LocalDate localDate) {
		return diaryService.readDiary(localDate);
	}

	@Operation(
		summary = "diary 읽기 api: 선택한 기간 중의 모든 일기 데이터를 가져옵니다."
	)
	@GetMapping("/diaryList")
	public List<Diary> readDiaryList(
		@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@Parameter(description = "조회할 기간의 첫번째 날",example = "2024-09-03") LocalDate startDate,
		@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@Parameter(description = "조회할 기간의 마지막 날",example = "2024-09-03") LocalDate endDate) {
		return diaryService.readDiaryList(startDate, endDate);
	}

	@PutMapping("/diary")
	public Diary updateDiary(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@Parameter(description = "날짜 형식: yyyy-mm-dd",example = "2024-09-03") LocalDate localDate,@RequestBody String text) {
		return diaryService.updateDiary(localDate, text);
	}

	@DeleteMapping("/diary")
	public void deleteDiary(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@Parameter(description = "날짜 형식: yyyy-mm-dd",example = "2024-09-03") LocalDate localDate) {
		diaryService.deleteDiary(localDate);
	}
}
