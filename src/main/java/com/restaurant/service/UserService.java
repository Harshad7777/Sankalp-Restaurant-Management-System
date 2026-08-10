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

    public UserService(
            UserRepository repository,
            PasswordEncoder passwordEncoder) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // =====================================================
    // ADD USER
    // =====================================================

    public String addUser(Users user) {

        if (user.getUsername() == null ||
            user.getUsername().trim().isEmpty()) {

            return "Username is required";
        }

        if (user.getPassword() == null ||
            user.getPassword().trim().isEmpty()) {

            return "Password is required";
        }

        Users existing =
                repository.findByUsername(
                        user.getUsername());

        if (existing != null) {

            return "Username Already Exists";
        }

        if (user.getRole() == null ||
            user.getRole().isEmpty()) {

            user.setRole("STAFF");
        }

        if (user.getStatus() == null ||
            user.getStatus().isEmpty()) {

            user.setStatus("ACTIVE");
        }

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()));

        repository.save(user);

        return "User Added Successfully";
    }

    // =====================================================
    // LOGIN
    // =====================================================

    public Users login(
            String username,
            String password) {

        Users user =
                repository.findByUsername(username);

        if (user == null) {
            return null;
        }

        if (!"ACTIVE".equalsIgnoreCase(
                user.getStatus())) {

            return null;
        }

        if (passwordEncoder.matches(
                password,
                user.getPassword())) {

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
    // GET USER BY ID
    // =====================================================

    public Users getUserById(Integer id) {

        return repository.findById(id);
    }

    // =====================================================
    // UPDATE USER
    // =====================================================

    public String updateUser(
            Integer id,
            Users user) {

        Users old =
                repository.findById(id);

        if (old == null) {

            return "User Not Found";
        }

        // ---------------------------------------------
        // Check username change
        // ---------------------------------------------

        Users usernameUser =
                repository.findByUsername(
                        user.getUsername());

        if (usernameUser != null &&
            !usernameUser.getUserId().equals(id)) {

            return "Username Already Exists";
        }

        old.setFullName(
                user.getFullName());

        old.setUsername(
                user.getUsername());

        old.setRole(
                user.getRole());

        old.setMobile(
                user.getMobile());

        old.setEmail(
                user.getEmail());

        old.setStatus(
                user.getStatus());

        // ---------------------------------------------
        // Password only if entered
        // ---------------------------------------------

        if (user.getPassword() != null &&
            !user.getPassword().trim().isEmpty()) {

            old.setPassword(
                    passwordEncoder.encode(
                            user.getPassword()));

            repository.update(old);

        } else {

            repository.updateWithoutPassword(old);
        }

        return "User Updated Successfully";
    }

    // =====================================================
    // DELETE USER
    // =====================================================

    public String deleteUser(Integer id) {

        Users user =
                repository.findById(id);

        if (user == null) {

            return "User Not Found";
        }

        repository.delete(id);

        return "User Deleted Successfully";
    }

    // =====================================================
    // PROFILE
    // =====================================================

    public Users getProfile(String username) {

        return repository.findByUsername(username);
    }

    // =====================================================
    // UPDATE PROFILE
    // =====================================================

    public String updateProfile(
            String username,
            Users profile) {

        Users user =
                repository.findByUsername(username);

        if (user == null) {

            return "User Not Found";
        }

        user.setFullName(
                profile.getFullName());

        user.setEmail(
                profile.getEmail());

        user.setMobile(
                profile.getMobile());

        repository.updateWithoutPassword(user);

        return "Profile Updated Successfully";
    }

    // =====================================================
    // CHANGE PASSWORD
    // =====================================================

    public String changePassword(
            String username,
            ChangePasswordDTO dto) {

        Users user =
                repository.findByUsername(username);

        if (user == null) {

            return "User Not Found";
        }

        if (!passwordEncoder.matches(
                dto.getOldPassword(),
                user.getPassword())) {

            return "Old Password Incorrect";
        }

        if (!dto.getNewPassword()
                .equals(dto.getConfirmPassword())) {

            return "New Password and Confirm Password do not match";
        }

        repository.updatePassword(
                user.getUserId(),
                passwordEncoder.encode(
                        dto.getNewPassword()));

        return "Password Changed Successfully";
    }
}