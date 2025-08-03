package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.info("🔧 Configuring CORS settings...");
        
        registry.addMapping("/**")
                .allowedOriginPatterns("*")  // Use patterns instead of origins when allowCredentials is true
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .exposedHeaders("Authorization", "Content-Type", "X-Requested-With")
                .allowCredentials(true)
                .maxAge(3600); // Cache preflight requests for 1 hour
        
        logger.info("✅ CORS configured successfully");
        logger.info("🌐 Allowed origin patterns: All (*)");
        logger.info("📝 Allowed methods: GET, POST, PUT, DELETE, OPTIONS, PATCH");
        logger.info("🔑 Allowed headers: All (*)");
        logger.info("🍪 Credentials: Enabled");
        logger.info("💡 Using allowedOriginPatterns for security with credentials");
    }
}

