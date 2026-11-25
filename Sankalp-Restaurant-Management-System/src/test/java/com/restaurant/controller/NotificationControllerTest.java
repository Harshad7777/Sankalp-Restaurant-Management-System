package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.service.NotificationService;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Should send WhatsApp notification")
    void testSendWhatsApp() throws Exception {
        Map<String, String> req = Map.of("phone", "9876543210", "message", "Your table is ready!");
        when(notificationService.sendWhatsAppNotification(anyString(), anyString()))
                .thenReturn(Map.of("status", "SUCCESS_MOCK", "recipient", "9876543210"));

        mockMvc.perform(post("/notifications/whatsapp/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS_MOCK"));
    }

    @Test
    @DisplayName("Should send SMS notification")
    void testSendSMS() throws Exception {
        Map<String, String> req = Map.of("phone", "9876543210", "message", "OTP is 1234");
        when(notificationService.sendSMSNotification(anyString(), anyString()))
                .thenReturn(Map.of("status", "SUCCESS_MOCK", "channel", "SMS"));

        mockMvc.perform(post("/notifications/sms/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.channel").value("SMS"));
    }
}
