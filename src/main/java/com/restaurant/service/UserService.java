package com.restaurant.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurant.dto.ChangePasswordDTO;
import com.restaurant.model.Users;
import com.restaurant.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final com.restaurant.service.mail.JmsMailProducer mailProducer;

    public UserService(
            UserRepository repository,
            PasswordEncoder passwordEncoder,
            com.restaurant.service.mail.JmsMailProducer mailProducer) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.mailProducer = mailProducer;
    }


    // =====================================================
    // ADD USER
    // =====================================================

    public String addUser(
            Users user) {

        if (user == null) {

            return "User request cannot be null";
        }


        if (user.getFullName() == null ||
            user.getFullName().trim().isEmpty()) {

            return "Full Name is required";
        }


        if (user.getUsername() == null ||
            user.getUsername().trim().isEmpty()) {

            return "Username is required";
        }


        if (user.getPassword() == null ||
            user.getPassword().trim().isEmpty()) {

            return "Password is required";
        }


        if (user.getPassword().length() < 6) {

            return "Password must contain at least 6 characters";
        }


        String username =
                user.getUsername().trim();


        Users existing =
                repository.findByUsername(
                        username
                );


        if (existing != null) {

            return "Username Already Exists";
        }


        // =================================================
        // ROLE
        // =================================================

        String role =
                user.getRole();


        if (role == null ||
            role.trim().isEmpty()) {

            role = "STAFF";
        }


        role =
                role.trim().toUpperCase();


        if (!isValidRole(role)) {

            return
                    "Invalid role. Use ADMIN, STAFF or CHEF";
        }


        user.setRole(role);


        // =================================================
        // STATUS
        // =================================================

        String status =
                user.getStatus();


        if (status == null ||
            status.trim().isEmpty()) {

            status = "ACTIVE";
        }


        status =
                status.trim().toUpperCase();


        if (!isValidStatus(status)) {

            return
                    "Invalid status. Use ACTIVE or INACTIVE";
        }


        user.setStatus(status);


        user.setFullName(
                user.getFullName().trim()
        );


        user.setUsername(
                username
        );


        // =================================================
        // ENCODE PASSWORD
        // =================================================

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );


        int result =
                repository.save(user);


        if (result <= 0) {
            return "User Creation Failed";
        }

        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            try {
                mailProducer.sendWelcomeEmail(user.getEmail(), user.getFullName(), user.getUsername(), user.getRole());
            } catch (Exception e) {
                // Log and ignore to prevent transaction failure if queue has issues
            }
        }

        return "User Added Successfully";
    }


    // =====================================================
    // LOGIN
    // =====================================================

    public Users login(
            String username,
            String password) {

        Users user =
                repository.findByUsername(
                        username
                );


        if (user == null) {

            return null;
        }


        if (!"ACTIVE".equalsIgnoreCase(
                user.getStatus())) {

            return null;
        }


        if (
                passwordEncoder.matches(
                        password,
                        user.getPassword()
                )
        ) {

            return user;
        }


        return null;
    }


    // =====================================================
    // GET ALL USERS
    // =====================================================

    public List<Users> getAllUsers() {

        return repository.findAll();
    }


    // =====================================================
    // GET TEAM USERS
    // STAFF + CHEF
    // =====================================================

    public List<Users> getTeamUsers() {

        return repository.findTeamUsers();
    }


    // =====================================================
    // GET BY ID
    // =====================================================

    public Users getUserById(
            Integer id) {

        if (
                id == null ||
                id <= 0
        ) {

            return null;
        }


        return repository.findById(id);
    }


    // =====================================================
    // UPDATE USER
    // =====================================================

    public String updateUser(
            Integer id,
            Users user) {

        if (
                id == null ||
                id <= 0
        ) {

            return "Invalid User ID";
        }


        if (user == null) {

            return "User request cannot be null";
        }


        Users old =
                repository.findById(id);


        if (old == null) {

            return "User Not Found";
        }


        if (
                user.getFullName() == null ||
                user.getFullName()
                        .trim()
                        .isEmpty()
        ) {

            return "Full Name is required";
        }


        if (
                user.getUsername() == null ||
                user.getUsername()
                        .trim()
                        .isEmpty()
        ) {

            return "Username is required";
        }


        String username =
                user.getUsername().trim();


        Users usernameUser =
                repository.findByUsername(
                        username
                );


        if (
                usernameUser != null &&
                !usernameUser
                        .getUserId()
                        .equals(id)
        ) {

            return "Username Already Exists";
        }


        // =================================================
        // ROLE
        // =================================================

        String role =
                user.getRole();


        if (
                role == null ||
                role.trim().isEmpty()
        ) {

            role =
                    old.getRole() != null
                            ? old.getRole()
                            : "STAFF";
        }


        role =
                role.trim().toUpperCase();


        if (!isValidRole(role)) {

            return
                    "Invalid role. Use ADMIN, STAFF or CHEF";
        }


        // =================================================
        // STATUS
        // =================================================

        String status =
                user.getStatus();


        if (
                status == null ||
                status.trim().isEmpty()
        ) {

            status =
                    old.getStatus() != null
                            ? old.getStatus()
                            : "ACTIVE";
        }


        status =
                status.trim().toUpperCase();


        if (!isValidStatus(status)) {

            return
                    "Invalid status. Use ACTIVE or INACTIVE";
        }


        // =================================================
        // UPDATE
        // =================================================

        old.setFullName(
                user.getFullName().trim()
        );


        old.setUsername(
                username
        );


        old.setRole(
                role
        );


        old.setMobile(
                user.getMobile()
        );


        old.setEmail(
                user.getEmail()
        );


        old.setStatus(
                status
        );


        // =================================================
        // PASSWORD
        // =================================================

        if (
                user.getPassword() != null &&
                !user.getPassword()
                        .trim()
                        .isEmpty()
        ) {

            if (
                    user.getPassword().length() < 6
            ) {

                return
                        "Password must contain at least 6 characters";
            }


            old.setPassword(
                    passwordEncoder.encode(
                            user.getPassword()
                    )
            );


            int result =
                    repository.update(old);


            if (result <= 0) {

                return "User Update Failed";
            }

        } else {

            int result =
                    repository.updateWithoutPassword(
                            old
                    );


            if (result <= 0) {

                return "User Update Failed";
            }
        }


        return "User Updated Successfully";
    }


    // =====================================================
    // ADMIN CHANGE PASSWORD
    // =====================================================

    public String updateUserPassword(
            Integer id,
            String newPassword) {

        if (
                id == null ||
                id <= 0
        ) {

            return "Invalid User ID";
        }


        if (
                newPassword == null ||
                newPassword.trim().isEmpty()
        ) {

            return "Password is required";
        }


        if (newPassword.length() < 6) {

            return
                    "Password must contain at least 6 characters";
        }


        Users user =
                repository.findById(id);


        if (user == null) {

            return "User Not Found";
        }


        String encodedPassword =
                passwordEncoder.encode(
                        newPassword
                );


        int result =
                repository.updatePassword(
                        id,
                        encodedPassword
                );


        if (result <= 0) {

            return "Password Update Failed";
        }


        return "Password Updated Successfully";
    }


    // =====================================================
    // DELETE
    // =====================================================

    public String deleteUser(
            Integer id) {

        if (
                id == null ||
                id <= 0
        ) {

            return "Invalid User ID";
        }


        Users user =
                repository.findById(id);


        if (user == null) {

            return "User Not Found";
        }


        int result =
                repository.delete(id);


        if (result <= 0) {

            return "User Delete Failed";
        }


        return "User Deleted Successfully";
    }


    // =====================================================
    // PROFILE
    // =====================================================

    public Users getProfile(
            String username) {

        return repository.findByUsername(
                username
        );
    }


    // =====================================================
    // UPDATE PROFILE
    // =====================================================

    public String updateProfile(
            String username,
            Users profile) {

        Users user =
                repository.findByUsername(
                        username
                );


        if (user == null) {

            return "User Not Found";
        }


        user.setFullName(
                profile.getFullName()
        );


        user.setEmail(
                profile.getEmail()
        );


        user.setMobile(
                profile.getMobile()
        );


        int result =
                repository.updateWithoutPassword(
                        user
                );


        if (result <= 0) {

            return "Profile Update Failed";
        }


        return "Profile Updated Successfully";
    }


    // =====================================================
    // CHANGE OWN PASSWORD
    // =====================================================

    public String changePassword(
            String username,
            ChangePasswordDTO dto) {

        if (dto == null) {

            return
                    "Password request cannot be null";
        }


        Users user =
                repository.findByUsername(
                        username
                );


        if (user == null) {

            return "User Not Found";
        }


        if (
                dto.getOldPassword() == null
                ||
                !passwordEncoder.matches(
                        dto.getOldPassword(),
                        user.getPassword()
                )
        ) {

            return "Old Password Incorrect";
        }


        if (
                dto.getNewPassword() == null
                ||
                dto.getConfirmPassword() == null
        ) {

            return
                    "New Password and Confirm Password do not match";
        }


        if (
                !dto.getNewPassword()
                        .equals(
                                dto.getConfirmPassword()
                        )
        ) {

            return
                    "New Password and Confirm Password do not match";
        }


        if (
                dto.getNewPassword().length() < 6
        ) {

            return
                    "New Password must contain at least 6 characters";
        }


        int result =
                repository.updatePassword(
                        user.getUserId(),
                        passwordEncoder.encode(
                                dto.getNewPassword()
                        )
                );


        if (result <= 0) {
            return "Password Update Failed";
        }

        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            try {
                mailProducer.sendPasswordResetAlert(user.getEmail(), user.getFullName(), user.getUsername());
            } catch (Exception e) {
                // Log and continue
            }
        }

        return "Password Changed Successfully";
    }


    // =====================================================
    // VALID ROLE
    // =====================================================

    private boolean isValidRole(
            String role) {

        return
                "ADMIN".equals(role)
                ||
                "STAFF".equals(role)
                ||
                "CHEF".equals(role);
    }


    // =====================================================
    // VALID STATUS
    // =====================================================

    private boolean isValidStatus(
            String status) {

        return
                "ACTIVE".equals(status)
                ||
                "INACTIVE".equals(status);
    }

    public com.restaurant.dto.PageResponse<Users> getUsersFilteredPage(com.restaurant.dto.PageRequestDTO req) {
        List<Users> content = repository.getUsersFilteredPage(req);
        long total = repository.countUsersFiltered(req);
        return new com.restaurant.dto.PageResponse<>(content, req.getPage(), req.getSize(), total);
    }
}