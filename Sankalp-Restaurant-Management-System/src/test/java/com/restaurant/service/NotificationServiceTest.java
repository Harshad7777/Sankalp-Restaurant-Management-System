package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class NotificationServiceTest {

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationService();
        ReflectionTestUtils.setField(notificationService, "twilioSid", "MOCK_SID_123");
        ReflectionTestUtils.setField(notificationService, "msg91AuthKey", "MOCK_MSG91_KEY");
        ReflectionTestUtils.setField(notificationService, "twilioWhatsAppNumber", "+14155238886");
    }

    @Test
    @DisplayName("Should send mock sandbox WhatsApp notification")
    void testSendWhatsAppNotification() {
        Map<String, Object> result = notificationService.sendWhatsAppNotification("9876543210", "Hello Test");

        assertNotNull(result);
        assertEquals("SUCCESS_MOCK", result.get("status"));
        assertEquals("WHATSAPP", result.get("channel"));
        assertEquals("9876543210", result.get("recipient"));
    }

    @Test
    @DisplayName("Should send mock sandbox SMS notification")
    void testSendSMSNotification() {
        Map<String, Object> result = notificationService.sendSMSNotification("9876543210", "OTP code: 9999");

        assertNotNull(result);
        assertEquals("SUCCESS_MOCK", result.get("status"));
        assertEquals("SMS", result.get("channel"));
    }
}
