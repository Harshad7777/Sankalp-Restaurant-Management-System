
package com.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restaurant.dto.LoginDTO;
import com.restaurant.dto.LoginResponse;
import com.restaurant.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginDTO dto) {

        LoginResponse response =
                authService.login(
                        dto.getUsername(),
                        dto.getPassword()
                );

        return ResponseEntity.ok(response);
    }
}

