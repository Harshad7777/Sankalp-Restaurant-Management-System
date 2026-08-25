package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.restaurant.dto.ChangePasswordDTO;
import com.restaurant.model.Users;
import com.restaurant.repository.UserRepository;
import com.restaurant.service.mail.JmsMailProducer;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JmsMailProducer mailProducer;

    @InjectMocks
    private UserService userService;

    private Users testUser;

    @BeforeEach
    void setUp() {
        testUser = Users.builder()
                .userId(1)
                .fullName("John Doe")
                .username("johndoe")
                .password("password123")
                .role("STAFF")
                .mobile("9876543210")
                .email("john@example.com")
                .status("ACTIVE")
                .build();
    }

    @Test
    @DisplayName("Should successfully add a new user with BCrypt hashed password")
    void testAddUser_Success() {
        when(repository.findByUsername("johndoe")).thenReturn(null);
        when(passwordEncoder.encode("password123")).thenReturn("$2a$10$hashedPassword");
        when(repository.save(any(Users.class))).thenReturn(1);

        String result = userService.addUser(testUser);

        assertEquals("User Added Successfully", result);
        verify(repository).save(any(Users.class));
        verify(passwordEncoder).encode("password123");
        verify(mailProducer).sendWelcomeEmail(eq("john@example.com"), eq("John Doe"), eq("johndoe"), eq("STAFF"));
    }

    @Test
    @DisplayName("Should reject adding user if username already exists")
    void testAddUser_DuplicateUsername() {
        when(repository.findByUsername("johndoe")).thenReturn(testUser);

        String result = userService.addUser(testUser);

        assertEquals("Username Already Exists", result);
        verify(repository, never()).save(any(Users.class));
    }

    @Test
    @DisplayName("Should reject adding user if user object is null")
    void testAddUser_NullUser() {
        String result = userService.addUser(null);
        assertEquals("User request cannot be null", result);
    }

    @Test
    @DisplayName("Should successfully update user profile")
    void testUpdateProfile_Success() {
        when(repository.findByUsername("johndoe")).thenReturn(testUser);
        when(repository.updateWithoutPassword(any(Users.class))).thenReturn(1);

        Users updateRequest = Users.builder()
                .fullName("John Updated")
                .mobile("9123456789")
                .email("john.new@example.com")
                .build();

        String result = userService.updateProfile("johndoe", updateRequest);

        assertEquals("Profile Updated Successfully", result);
        verify(repository).updateWithoutPassword(any(Users.class));
    }

    @Test
    @DisplayName("Should fail updating profile if user is not found")
    void testUpdateProfile_UserNotFound() {
        when(repository.findByUsername("unknown")).thenReturn(null);

        String result = userService.updateProfile("unknown", testUser);

        assertEquals("User Not Found", result);
    }

    @Test
    @DisplayName("Should successfully change user password")
    void testChangePassword_Success() {
        when(repository.findByUsername("johndoe")).thenReturn(testUser);
        when(passwordEncoder.matches("oldPass123", "password123")).thenReturn(true);
        when(passwordEncoder.encode("newPass123")).thenReturn("$2a$10$newHashedPassword");
        when(repository.updatePassword(eq(1), anyString())).thenReturn(1);

        ChangePasswordDTO dto = ChangePasswordDTO.builder()
                .oldPassword("oldPass123")
                .newPassword("newPass123")
                .confirmPassword("newPass123")
                .build();

        String result = userService.changePassword("johndoe", dto);

        assertEquals("Password Changed Successfully", result);
        verify(repository).updatePassword(eq(1), anyString());
        verify(mailProducer).sendPasswordResetAlert(eq("john@example.com"), eq("John Doe"), eq("johndoe"));
    }

    @Test
    @DisplayName("Should reject change password if new and confirm passwords do not match")
    void testChangePassword_Mismatch() {
        when(repository.findByUsername("johndoe")).thenReturn(testUser);
        when(passwordEncoder.matches("oldPass123", "password123")).thenReturn(true);

        ChangePasswordDTO dto = ChangePasswordDTO.builder()
                .oldPassword("oldPass123")
                .newPassword("newPass123")
                .confirmPassword("differentPass")
                .build();

        String result = userService.changePassword("johndoe", dto);

        assertEquals("New Password and Confirm Password do not match", result);
        verify(repository, never()).updatePassword(anyInt(), anyString());
    }

    @Test
    @DisplayName("Should return team users (STAFF and CHEF)")
    void testGetTeamUsers() {
        when(repository.findTeamUsers()).thenReturn(List.of(testUser));

        List<Users> team = userService.getTeamUsers();

        assertNotNull(team);
        assertEquals(1, team.size());
        assertEquals("STAFF", team.get(0).getRole());
    }
}
