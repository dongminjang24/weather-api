package api_pr.weather.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "date_weather")
public class DateWeather {

	@Id
	@Column(name = "date",nullable = false)
	private LocalDate date;

	@Column(name = "weather",nullable = false,length = 50)
	private String weather;

	@Column(name = "icon",nullable = false,length = 50)
	private String icon;

	@Column(name = "temperature",nullable = false)
	private Double temperature;


}
