package com.algamoney.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlgamoneyApiApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "application-local");
		SpringApplication.run(AlgamoneyApiApplication.class, args);
	}
}
