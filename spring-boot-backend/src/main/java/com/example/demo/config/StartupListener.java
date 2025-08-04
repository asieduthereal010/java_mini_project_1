package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StartupListener {

    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Autowired
    private Environment environment;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStarted() {
        logger.info("=".repeat(60));
        logger.info("🎯 APPLICATION STARTUP COMPLETE");
        logger.info("=".repeat(60));
        logger.info("📅 Started at: {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        logger.info("🏷️  Application Name: {}", environment.getProperty("spring.application.name"));
        logger.info("🌐 Server Port: {}", environment.getProperty("server.port", "8080"));
        logger.info("🗄️  Database URL: {}", environment.getProperty("spring.datasource.url"));
        logger.info("👤 Database User: {}", environment.getProperty("spring.datasource.username"));
        logger.info("🔧 JPA DDL Mode: {}", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        logger.info("=".repeat(60));
    }
} 