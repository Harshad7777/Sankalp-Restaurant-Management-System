package com.restaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.restaurant.security.JwtFilter;

import java.util.List;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    // =====================================================
    // PASSWORD ENCODER
    // =====================================================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // =====================================================
    // SECURITY FILTER CHAIN
    // =====================================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

            // =================================================
            // CORS
            // =================================================

            .cors(cors -> {
            })

            // =================================================
            // CSRF
            // =================================================

            .csrf(csrf -> csrf.disable())

            // =================================================
            // SESSION
            // =================================================

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            // =================================================
            // AUTHORIZATION
            // =================================================

            .authorizeHttpRequests(auth -> auth

                // =================================================
                // PUBLIC
                // =================================================

                .requestMatchers(
                    "/login",
                    "/auth/login",
                    "/auth/forgot-password/**",
                    "/users/register",
                    "/settings/**",
                    "/uploads/**",
                    "/static/**",
                    "/images/**",
                    "/css/**",
                    "/js/**",
                    "/favicon.ico",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/v3/api-docs"
                )
                .permitAll()

                .requestMatchers(
                    HttpMethod.POST,
                    "/reservation",
                    "/reservation/"
                )
                .permitAll()

                .requestMatchers(
                    HttpMethod.GET,
                    "/reservation",
                    "/reservation/**"
                )
                .permitAll()


                // =================================================
                // PROFILE
                // ADMIN + STAFF + CHEF
                // =================================================

                .requestMatchers(
                    "/users/profile",
                    "/users/profile/change-password"
                )
                .hasAnyRole(
                    "ADMIN",
                    "STAFF",
                    "CHEF"
                )


                // =================================================
                // ADMIN ONLY - USERS
                // =================================================

                .requestMatchers("/users/**")
                .hasRole("ADMIN")


                // =================================================
                // CATEGORY
                // GET = PUBLIC (for landing page & customers)
                // POST/PUT/DELETE = ADMIN
                // =================================================

                .requestMatchers(
                    HttpMethod.GET,
                    "/category",
                    "/category/**"
                )
                .permitAll()

                .requestMatchers(
                    HttpMethod.POST,
                    "/category",
                    "/category/**"
                )
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/category",
                    "/category/**"
                )
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/category",
                    "/category/**"
                )
                .hasRole("ADMIN")


                // =================================================
                // TABLE & RESERVATIONS
                // ADMIN + STAFF
                // =================================================

                .requestMatchers(
                    "/table",
                    "/table/**",
                    "/reservation",
                    "/reservation/**"
                )
                .hasAnyRole(
                    "ADMIN",
                    "STAFF"
                )


                // =================================================
                // MENU
                // GET = PUBLIC (for landing page & customers)
                // POST/PUT/DELETE = ADMIN
                // =================================================

                .requestMatchers(
                    HttpMethod.GET,
                    "/menu",
                    "/menu/**"
                )
                .permitAll()

                .requestMatchers(
                    HttpMethod.POST,
                    "/menu",
                    "/menu/**"
                )
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/menu",
                    "/menu/**"
                )
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/menu",
                    "/menu/**"
                )
                .hasRole("ADMIN")


                // =================================================
                // REPORT
                // =================================================

                .requestMatchers("/report/**")
                .hasRole("ADMIN")


                // =================================================
                // UPLOAD
                // =================================================

                .requestMatchers("/upload/**")
                .hasRole("ADMIN")


                // =================================================
                // INVENTORY
                // GET/POST/PUT = ADMIN + STAFF + CHEF
                // DELETE = ADMIN
                // =================================================

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/inventory",
                    "/inventory/**"
                )
                .hasRole("ADMIN")

                .requestMatchers(
                    "/inventory",
                    "/inventory/**"
                )
                .hasAnyRole(
                    "ADMIN",
                    "STAFF",
                    "CHEF"
                )


                // =================================================
                // CUSTOMER
                // ADMIN + STAFF
                // =================================================

                .requestMatchers("/customer/**")
                .hasAnyRole(
                    "ADMIN",
                    "STAFF"
                )


                // =================================================
                // CHEF & KITCHEN ORDER APIs
                // CHEF + ADMIN + STAFF
                // =================================================

                .requestMatchers(
                    "/order/kitchen/**"
                )
                .hasAnyRole(
                    "CHEF",
                    "ADMIN",
                    "STAFF"
                )

                // =================================================
                // NORMAL ORDER APIs
                // ADMIN + STAFF + CHEF
                // =================================================

                .requestMatchers("/order/**")
                .hasAnyRole(
                    "ADMIN",
                    "STAFF",
                    "CHEF"
                )


                // =================================================
                // BILL
                // ADMIN + STAFF
                // =================================================

                .requestMatchers("/bill/**")
                .hasAnyRole(
                    "ADMIN",
                    "STAFF"
                )


                // =================================================
                // DASHBOARD
                // ADMIN + STAFF
                // =================================================

                .requestMatchers("/dashboard/**")
                .hasAnyRole(
                    "ADMIN",
                    "STAFF"
                )


                // =================================================
                // KITCHEN DASHBOARD VIEW
                // ADMIN + CHEF
                // =================================================

                .requestMatchers(
                    HttpMethod.GET,
                    "/kitchen",
                    "/kitchen/**"
                )
                .hasAnyRole(
                    "ADMIN",
                    "CHEF"
                )


                // =================================================
                // KITCHEN ACTIONS
                // CHEF ONLY
                // =================================================

                .requestMatchers(
                    HttpMethod.POST,
                    "/kitchen/**"
                )
                .hasRole("CHEF")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/kitchen/**"
                )
                .hasRole("CHEF")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/kitchen/**"
                )
                .hasRole("CHEF")


                // =================================================
                // EVERYTHING ELSE
                // =================================================

                .anyRequest()
                .authenticated()
            )


            // =================================================
            // JWT FILTER
            // =================================================

            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }


    // =====================================================
    // AUTHENTICATION MANAGER
    // =====================================================

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}