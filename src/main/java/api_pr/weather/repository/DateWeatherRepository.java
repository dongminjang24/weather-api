package api_pr.weather.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api_pr.weather.domain.DateWeather;
import api_pr.weather.domain.Diary;

@Repository
public interface DateWeatherRepository extends JpaRepository<DateWeather,LocalDate> {
	List<DateWeather> findAllByDate(LocalDate date);


}
