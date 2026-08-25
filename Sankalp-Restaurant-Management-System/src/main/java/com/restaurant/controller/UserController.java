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
@CrossOrigin(origins = "*")
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
            @jakarta.validation.Valid @RequestBody Users user) {

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

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body(
                            Map.of(
                                    "success", false,
                                    "message",
                                    e.getMessage() != null
                                            ? e.getMessage()
                                            : "User registration failed"
                            )
                    );
        }
    }


    // =====================================================
    // GET TEAM USERS
    // GET /api/users/team
    //
    // STAFF + CHEF ONLY
    // ADMIN EXCLUDED
    // =====================================================

    @GetMapping("/team")
    public ResponseEntity<List<Users>> getTeamUsers() {

        return ResponseEntity.ok(
                service.getTeamUsers()
        );
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
    // PROFILE
    // GET /api/users/profile
    // =====================================================

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(
            Authentication authentication) {

        Users user =
                service.getProfile(
                        authentication.getName()
                );

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
                        profile
                );

        if ("User Not Found".equals(result)) {

            return ResponseEntity.badRequest()
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }


    // =====================================================
    // CHANGE OWN PASSWORD
    // PUT /api/users/profile/change-password
    // =====================================================

    @PutMapping("/profile/change-password")
    public ResponseEntity<?> changePassword(
            Authentication authentication,
            @jakarta.validation.Valid @RequestBody ChangePasswordDTO dto) {

        String result =
                service.changePassword(
                        authentication.getName(),
                        dto
                );

        if (
                "User Not Found".equals(result)
                ||
                "Old Password Incorrect".equals(result)
                ||
                "New Password and Confirm Password do not match"
                        .equals(result)
        ) {

            return ResponseEntity.badRequest()
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }


    // =====================================================
    // ADMIN CHANGE PASSWORD
    // PUT /api/users/{id}/password
    // =====================================================

    @PutMapping("/{id}/password")
    public ResponseEntity<?> changeUserPassword(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body) {

        try {

            if (id == null || id <= 0) {

                return ResponseEntity.badRequest()
                        .body(
                                Map.of(
                                        "success", false,
                                        "message",
                                        "Invalid user ID"
                                )
                        );
            }


            if (body == null) {

                return ResponseEntity.badRequest()
                        .body(
                                Map.of(
                                        "success", false,
                                        "message",
                                        "Password request cannot be null"
                                )
                        );
            }


            String password =
                    body.get("password");


            if (password == null ||
                password.trim().isEmpty()) {

                return ResponseEntity.badRequest()
                        .body(
                                Map.of(
                                        "success", false,
                                        "message",
                                        "Password is required"
                                )
                        );
            }


            String result =
                    service.updateUserPassword(
                            id,
                            password
                    );


            if ("Password Updated Successfully"
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

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body(
                            Map.of(
                                    "success", false,
                                    "message",
                                    e.getMessage() != null
                                            ? e.getMessage()
                                            : "Password update failed"
                            )
                    );
        }
    }


    // =====================================================
    // GET USER BY ID
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
                service.updateUser(
                        id,
                        user
                );


        if (
                "User Not Found".equals(result)
                ||
                "Username Already Exists".equals(result)
        ) {

            return ResponseEntity.badRequest()
                    .body(result);
        }


        if (
                result.startsWith("Invalid")
                ||
                result.endsWith("required")
                ||
                result.contains("Password")
                ||
                result.contains("Failed")
        ) {

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


        if (
                "User Not Found".equals(result)
                ||
                result.startsWith("Invalid")
                ||
                result.endsWith("Failed")
        ) {

            return ResponseEntity.badRequest()
                    .body(result);
        }


        return ResponseEntity.ok(result);
    }

    // =====================================================
    // GET PAGINATED, FILTERED, SORTED USERS
    // GET /api/users/page
    // =====================================================

    @GetMapping("/page")
    public ResponseEntity<com.restaurant.dto.PageResponse<Users>> getUsersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status) {

        com.restaurant.dto.PageRequestDTO req = com.restaurant.dto.PageRequestDTO.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .search(search)
                .role(role)
                .status(status)
                .build();

        return ResponseEntity.ok(service.getUsersFilteredPage(req));
    }
}