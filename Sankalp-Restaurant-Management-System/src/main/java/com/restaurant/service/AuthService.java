package com.restaurant.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.restaurant.dto.LoginResponse;
import com.restaurant.model.RestaurantSetting;
import com.restaurant.model.Users;
import com.restaurant.repository.SettingRepository;
import com.restaurant.repository.UserRepository;
import com.restaurant.security.JwtUtil;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final SettingRepository settingRepository;
    private final com.restaurant.repository.RestaurantRepository restaurantRepository;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserRepository userRepository,
            SettingRepository settingRepository,
            com.restaurant.repository.RestaurantRepository restaurantRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.settingRepository = settingRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public LoginResponse login(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        Users user = userRepository.findByUsername(authentication.getName());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        String role = user.getRole();

        if (role == null || role.trim().isEmpty()) {
            throw new RuntimeException("User role not found");
        }

        role = role.trim().toUpperCase();

        if (role.startsWith("ROLE_")) {
            role = role.substring(5);
        }

        Integer restaurantId = user.getRestaurantId() != null ? user.getRestaurantId() : 1;

        String restaurantName = "Sankalp Restaurant";
        com.restaurant.model.Restaurant restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant != null && restaurant.getRestaurantName() != null) {
            restaurantName = restaurant.getRestaurantName();
        } else {
            RestaurantSetting settings = settingRepository.getSettingsByRestaurantId(restaurantId);
            if (settings != null && settings.getRestaurantName() != null) {
                restaurantName = settings.getRestaurantName();
            }
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getUserId(), role, restaurantId);

        logger.info(
                "Login success: userId={}, username={}, role={}, restaurantId={}, restaurantName={}",
                user.getUserId(), user.getUsername(), role, restaurantId, restaurantName);

        return new LoginResponse(
                token,
                user.getUserId(),
                user.getUsername(),
                role,
                restaurantId,
                restaurantName
        );
    }
}
