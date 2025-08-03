package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// so this is where everything runs.
@SpringBootApplication
public class DemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		logger.info("🚀 Starting Student Management System...");
		logger.info("📚 Spring Boot version: 3.5.3");
		logger.info("☕ Java version: 21");
		
		SpringApplication.run(DemoApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void applicationReady() {
		logger.info("✅ Application is ready and running!");
		logger.info("🌐 Server started on: http://localhost:8080");
		logger.info("📊 Student Dashboard: http://localhost:8080/dashboard/students");
		logger.info("👨‍🏫 Lecturer Dashboard: http://localhost:8080/dashboard/lecturers");
		logger.info("🗄️  Database: PostgreSQL (software_engineering)");
	}
}
