package com.algamoney.api;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableCaching
@EnableScheduling
public class AlgamoneyApiApplication {
	private static ApplicationContext APPLICATION_CONTEXT;

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "application-local");
		APPLICATION_CONTEXT = SpringApplication.run(AlgamoneyApiApplication.class, args);
	}

	public static <T> T getBean(Class<T> type) {
		return APPLICATION_CONTEXT.getBean(type);
	}
}
