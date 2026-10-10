package com.restaurant.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a User in the Restaurant Management System.
 * Uses Lombok annotations to generate boilerplate code (getters, setters, constructors, builder).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    private Integer userId;

    private Integer restaurantId;

    @NotBlank(message = "Full Name is required")
    @Size(min = 2, max = 100, message = "Full Name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    /*
     * Password can be received from POST/PUT requests,
     * but is never returned in JSON responses.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 6, message = "Password must contain at least 6 characters")
    private String password;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(SUPER_ADMIN|ADMIN|STAFF|CHEF|MANAGER)$", message = "Role must be SUPER_ADMIN, ADMIN, STAFF, CHEF or MANAGER")
    private String role;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be a valid 10-digit number")
    private String mobile;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be ACTIVE or INACTIVE")
    private String status;

    private String restaurantName;

    // Convenience constructor for legacy calls omitting restaurantId
    public Users(Integer userId, String fullName, String username, String password, String role, String mobile, String email, String status) {
        this(userId, 1, fullName, username, password, role, mobile, email, status, null);
    }
}