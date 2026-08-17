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
                    "/api/login",
                    "/api/auth/login",
                    "/api/users/register",
                    "/uploads/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/v3/api-docs"
                )
                .permitAll()


                // =================================================
                // PROFILE
                // ADMIN + STAFF + CHEF
                // =================================================

                .requestMatchers(
                    "/api/users/profile",
                    "/api/users/profile/change-password"
                )
                .hasAnyRole(
                    "ADMIN",
                    "STAFF",
                    "CHEF"
                )


                // =================================================
                // ADMIN ONLY - USERS
                // =================================================

                .requestMatchers("/api/users/**")
                .hasRole("ADMIN")


                // =================================================
                // CATEGORY
                // GET = ADMIN + STAFF
                // POST/PUT/DELETE = ADMIN
                // =================================================

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/category",
                    "/api/category/**"
                )
                .hasAnyRole(
                    "ADMIN",
                    "STAFF"
                )

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/category",
                    "/api/category/**"
                )
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/category",
                    "/api/category/**"
                )
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/category",
                    "/api/category/**"
                )
                .hasRole("ADMIN")


                // =================================================
                // TABLE
                // ADMIN + STAFF
                // =================================================

                .requestMatchers(
                    "/api/table",
                    "/api/table/**"
                )
                .hasAnyRole(
                    "ADMIN",
                    "STAFF"
                )


                // =================================================
                // MENU
                // GET = ADMIN + STAFF
                // POST/PUT/DELETE = ADMIN
                // =================================================

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/menu",
                    "/api/menu/**"
                )
                .hasAnyRole(
                    "ADMIN",
                    "STAFF"
                )

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/menu",
                    "/api/menu/**"
                )
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/menu",
                    "/api/menu/**"
                )
                .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/menu",
                    "/api/menu/**"
                )
                .hasRole("ADMIN")


                // =================================================
                // REPORT
                // =================================================

                .requestMatchers("/api/report/**")
                .hasRole("ADMIN")


                // =================================================
                // UPLOAD
                // =================================================

                .requestMatchers("/api/upload/**")
                .hasRole("ADMIN")


                // =================================================
                // CUSTOMER
                // ADMIN + STAFF
                // =================================================

                .requestMatchers("/api/customer/**")
                .hasAnyRole(
                    "ADMIN",
                    "STAFF"
                )


                // =================================================
                // CHEF & KITCHEN ORDER APIs
                // CHEF + ADMIN + STAFF
                // =================================================

                .requestMatchers(
                    "/api/order/kitchen/**"
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

                .requestMatchers("/api/order/**")
                .hasAnyRole(
                    "ADMIN",
                    "STAFF",
                    "CHEF"
                )


                // =================================================
                // BILL
                // ADMIN + STAFF
                // =================================================

                .requestMatchers("/api/bill/**")
                .hasAnyRole(
                    "ADMIN",
                    "STAFF"
                )


                // =================================================
                // DASHBOARD
                // ADMIN + STAFF
                // =================================================

                .requestMatchers("/api/dashboard/**")
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
                    "/api/kitchen",
                    "/api/kitchen/**"
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
                    "/api/kitchen/**"
                )
                .hasRole("CHEF")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/kitchen/**"
                )
                .hasRole("CHEF")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/kitchen/**"
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