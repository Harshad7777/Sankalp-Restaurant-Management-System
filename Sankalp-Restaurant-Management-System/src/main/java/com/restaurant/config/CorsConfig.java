package com.restaurant.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // =====================================================
        // FRONTEND & LIVE DOMAINS
        // =====================================================
        configuration.setAllowedOriginPatterns(
                List.of(
                        "https://restaurantms.co.in",
                        "http://restaurantms.co.in",
                        "https://www.restaurantms.co.in",
                        "http://www.restaurantms.co.in",
                        "http://localhost:*",
                        "https://localhost:*",
                        "http://127.0.0.1:*",
                        "https://127.0.0.1:*",
                        "*"
                )
        );

        // =====================================================
        // METHODS
        // =====================================================
        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        // =====================================================
        // HEADERS
        // =====================================================
        configuration.setAllowedHeaders(
                List.of("*")
        );

        // =====================================================
        // CREDENTIALS
        // =====================================================
        configuration.setAllowCredentials(true);

        // =====================================================
        // REGISTER CONFIGURATION
        // =====================================================
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}	