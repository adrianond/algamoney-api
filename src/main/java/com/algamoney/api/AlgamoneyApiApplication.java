package com.algamoney.api;

		import org.springframework.boot.SpringApplication;
		import org.springframework.boot.autoconfigure.SpringBootApplication;
		import org.springframework.context.annotation.Bean;
		import org.springframework.web.servlet.config.annotation.CorsRegistry;
		import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AlgamoneyApiApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "application-local");
		SpringApplication.run(AlgamoneyApiApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins("http://localhost:8080");
			}
		};
	}

}
