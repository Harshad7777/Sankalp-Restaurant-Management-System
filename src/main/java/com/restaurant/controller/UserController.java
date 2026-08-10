package com.restaurant.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.restaurant.dto.ChangePasswordDTO;
import com.restaurant.model.Users;
import com.restaurant.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // =====================================================
    // REGISTER USER
    // POST /api/users/register
    // =====================================================

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody Users user) {

        try {

            String result =
                    service.addUser(user);

            if ("User Added Successfully"
                    .equals(result)) {

                return ResponseEntity.ok(
                        Map.of(
                                "success", true,
                                "message", result
                        )
                );
            }

            return ResponseEntity.badRequest()
                    .body(
                            Map.of(
                                    "success", false,
                                    "message", result
                            )
                    );

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body(
                            Map.of(
                                    "success", false,
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }

    // =====================================================
    // GET ALL USERS
    // GET /api/users
    // =====================================================

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {

        return ResponseEntity.ok(
                service.getAllUsers()
        );
    }

    // =====================================================
    // GET USER
    // GET /api/users/{id}
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(
            @PathVariable Integer id) {

        Users user =
                service.getUserById(id);

        if (user == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(user);
    }

    // =====================================================
    // UPDATE USER
    // PUT /api/users/{id}
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Integer id,
            @RequestBody Users user) {

        String result =
                service.updateUser(id, user);

        if ("User Not Found".equals(result) ||
            "Username Already Exists".equals(result)) {

            return ResponseEntity.badRequest()
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }

    // =====================================================
    // DELETE USER
    // DELETE /api/users/{id}
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Integer id) {

        String result =
                service.deleteUser(id);

        if ("User Not Found".equals(result)) {

            return ResponseEntity.badRequest()
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }

    // =====================================================
    // PROFILE
    // GET /api/users/profile
    // =====================================================

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(
            Authentication authentication) {

        Users user =
                service.getProfile(
                        authentication.getName());

        if (user == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(user);
    }

    // =====================================================
    // UPDATE PROFILE
    // PUT /api/users/profile
    // =====================================================

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            Authentication authentication,
            @RequestBody Users profile) {

        String result =
                service.updateProfile(
                        authentication.getName(),
                        profile);

        if ("User Not Found".equals(result)) {

            return ResponseEntity.badRequest()
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }

    // =====================================================
    // CHANGE PASSWORD
    // PUT /api/users/profile/change-password
    // =====================================================

    @PutMapping("/profile/change-password")
    public ResponseEntity<?> changePassword(
            Authentication authentication,
            @RequestBody ChangePasswordDTO dto) {

        String result =
                service.changePassword(
                        authentication.getName(),
                        dto);

        switch (result) {

            case "User Not Found":
            case "Old Password Incorrect":
            case "New Password and Confirm Password do not match":

                return ResponseEntity.badRequest()
                        .body(result);

            default:

                return ResponseEntity.ok(result);
        }
    }
}