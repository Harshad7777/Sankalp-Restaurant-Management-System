package com.restaurant.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final Logger logger =
            LoggerFactory.getLogger(WebConfig.class);

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        logger.info("Upload dir = {}", uploadDir);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String location = "file:///" + uploadDir.replace("\\", "/") + "/";

        logger.info("Resource location = {}", location);

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(location);
    }

    @org.springframework.context.annotation.Bean
    public org.springframework.context.MessageSource messageSource() {
        org.springframework.context.support.ResourceBundleMessageSource messageSource =
                new org.springframework.context.support.ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
}