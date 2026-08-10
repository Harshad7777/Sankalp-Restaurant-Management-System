package com.restaurant.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.restaurant.dto.LoginResponse;
import com.restaurant.model.Users;
import com.restaurant.repository.UserRepository;
import com.restaurant.security.JwtUtil;

@Service
public class AuthService {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserRepository userRepository) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    // =====================================================
    // LOGIN
    // =====================================================

    public LoginResponse login(
            String username,
            String password) {

        // =================================================
        // AUTHENTICATE USER
        // =================================================

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                username,
                                password
                        )
                );

        // =================================================
        // GET USER FROM DATABASE
        // =================================================

        Users user =
                userRepository.findByUsername(
                        authentication.getName()
                );

        if (user == null) {

            throw new RuntimeException(
                    "User not found"
            );
        }

        // =================================================
        // GET ROLE
        // =================================================

        String role =
                user.getRole();

        if (role == null ||
            role.trim().isEmpty()) {

            throw new RuntimeException(
                    "User role not found"
            );
        }

        role =
                role.trim().toUpperCase();

        // Remove ROLE_ if database contains it
        if (role.startsWith("ROLE_")) {

            role =
                    role.substring(5);
        }

        // =================================================
        // GENERATE JWT
        // =================================================

        String token =
                jwtUtil.generateToken(
                        user.getUsername()
                );

        logger.info(
                "Login success: userId={}, username={}, role={}",
                user.getUserId(), user.getUsername(), role);

        // =================================================
        // RESPONSE
        // =================================================

        return new LoginResponse(
                token,
                user.getUserId(),
                user.getUsername(),
                role
        );
    }
}
