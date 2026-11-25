package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.model.Users;
import com.restaurant.repository.UserRepository;
import com.restaurant.service.mail.JmsMailProducer;

@ExtendWith(MockitoExtension.class)
class ForgotPasswordControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JmsMailProducer jmsMailProducer;

    @InjectMocks
    private ForgotPasswordController forgotPasswordController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(forgotPasswordController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Should request OTP successfully")
    void testRequestOtp_Success() throws Exception {
        Users user = Users.builder()
                .userId(1)
                .username("john_staff")
                .fullName("John")
                .email("john@example.com")
                .build();

        when(userRepository.findByUsernameOrEmail("john_staff")).thenReturn(user);

        ForgotPasswordController.ForgotPasswordRequest req = new ForgotPasswordController.ForgotPasswordRequest();
        req.setIdentity("john_staff");

        mockMvc.perform(post("/auth/forgot-password/request-otp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("Should reject request OTP when identity is missing")
    void testRequestOtp_MissingIdentity() throws Exception {
        ForgotPasswordController.ForgotPasswordRequest req = new ForgotPasswordController.ForgotPasswordRequest();

        mockMvc.perform(post("/auth/forgot-password/request-otp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }
}
