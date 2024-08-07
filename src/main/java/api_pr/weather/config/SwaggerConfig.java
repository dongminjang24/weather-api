package api_pr.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("날씨 일기 프로젝트 🤭")
				.description("Diary API: 날씨 일기를 CRUD 할 수 있는 백엔드 API.")
				.version("1.0"));
	}
}
