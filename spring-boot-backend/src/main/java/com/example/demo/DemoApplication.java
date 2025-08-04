package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import io.github.cdimascio.dotenv.Dotenv;
=======
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
>>>>>>> dbf6df4963426cc10e0ea9a4b452cf2dc50a67a8

// so this is where everything runs.
@SpringBootApplication
public class DemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
<<<<<<< HEAD
		Dotenv dotenv = Dotenv.configure().load();
		System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
		System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
		System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
		System.setProperty("SPRING_JPA_HIBERNATE", dotenv.get("SPRING_JPA_HIBERNATE"));
		SpringApplication.run(DemoApplication.class, args
		);
=======
		logger.info("🚀 Starting Student Management System...");
		logger.info("📚 Spring Boot version: 3.5.3");
		logger.info("☕ Java version: 21");
		
		SpringApplication.run(DemoApplication.class, args);
>>>>>>> dbf6df4963426cc10e0ea9a4b452cf2dc50a67a8
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
