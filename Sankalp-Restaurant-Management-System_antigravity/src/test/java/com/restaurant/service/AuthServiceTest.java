package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.restaurant.dto.LoginResponse;
import com.restaurant.model.Users;
import com.restaurant.repository.UserRepository;
import com.restaurant.security.JwtUtil;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void testLogin_Success() {
        String username = "adminUser";
        String password = "adminPassword";

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(username);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);

        Users mockUser = new Users();
        mockUser.setUserId(1);
        mockUser.setUsername(username);
        mockUser.setRole("ROLE_ADMIN");

        when(userRepository.findByUsername(username)).thenReturn(mockUser);
        when(jwtUtil.generateToken(username)).thenReturn("mocked.jwt.token");

        LoginResponse response = authService.login(username, password);

        assertNotNull(response);
        assertEquals("mocked.jwt.token", response.getToken());
        assertEquals(Integer.valueOf(1), response.getUserId());
        assertEquals(username, response.getUsername());
        assertEquals("ADMIN", response.getRole());
    }

    @Test
    void testLogin_BadCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(BadCredentialsException.class, () -> {
            authService.login("invalidUser", "wrongPass");
        });
    }

    @Test
    void testLogin_UserNotFoundInDb() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("unknownUser");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(userRepository.findByUsername("unknownUser")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login("unknownUser", "pass");
        });
        assertTrue(exception.getMessage().contains("User not found"));
    }
}
