package com.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for authentication response payload.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String token;
    private Integer userId;
    private String username;
    private String role;
    private Integer restaurantId;
    private String restaurantName;

    // Convenience constructor for legacy authentication calls
    public LoginResponse(String token, Integer userId, String username, String role) {
        this(token, userId, username, role, 1, "Sankalp Restaurant");
    }
}
