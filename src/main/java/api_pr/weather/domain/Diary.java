package api_pr.weather.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "diary")
public class Diary {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;


	@Column(name = "weather",length = 50,nullable = false)
	private String weather;

	@Column(name = "icon",length = 50,nullable = false)
	private String icon;

	@Column(name = "temperature",nullable = false)
	private Double temperature;

	@Column(name = "text",length = 500,nullable = false)
	private String text;

	@Column(name = "date",nullable = false)
	private LocalDate localDate;

	public void setDateWeather(DateWeather dateWeather) {
		this.localDate = dateWeather.getDate();
		this.weather = dateWeather.getWeather();
		this.icon = dateWeather.getIcon();
		this.temperature = dateWeather.getTemperature();
	}
}
