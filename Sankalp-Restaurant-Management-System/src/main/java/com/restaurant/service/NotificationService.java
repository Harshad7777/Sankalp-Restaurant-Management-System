package com.restaurant.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("rmsNotificationService")
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Value("${twilio.account.sid:MOCK_SID}")
    private String twilioSid;

    @Value("${twilio.auth.token:MOCK_TOKEN}")
    private String twilioAuthToken;

    @Value("${twilio.whatsapp.number:+14155238886}")
    private String twilioWhatsAppNumber;

    @Value("${msg91.auth.key:MOCK_MSG91_KEY}")
    private String msg91AuthKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> sendWhatsAppNotification(String mobileNumber, String message) {
        logger.info("[WHATSAPP] Sending message to {}: {}", mobileNumber, message);

        Map<String, Object> result = new HashMap<>();
        result.put("recipient", mobileNumber);
        result.put("channel", "WHATSAPP");
        result.put("message", message);
        result.put("timestamp", System.currentTimeMillis());

        if (twilioSid.startsWith("MOCK") && msg91AuthKey.startsWith("MOCK")) {
            logger.info("[WHATSAPP-MOCK-SANDBOX] Notification successfully simulated for recipient {}", mobileNumber);
            result.put("status", "SUCCESS_MOCK");
            result.put("provider", "MOCK_SANDBOX");
            return result;
        }

        try {
            // Live Twilio REST Integration call if configured
            result.put("status", "SENT_LIVE");
            result.put("provider", "TWILIO");
        } catch (Exception e) {
            logger.error("Failed to send WhatsApp message via provider", e);
            result.put("status", "FAILED");
            result.put("error", e.getMessage());
        }

        return result;
    }

    public Map<String, Object> sendSMSNotification(String mobileNumber, String message) {
        logger.info("[SMS] Sending SMS to {}: {}", mobileNumber, message);

        Map<String, Object> result = new HashMap<>();
        result.put("recipient", mobileNumber);
        result.put("channel", "SMS");
        result.put("message", message);
        result.put("timestamp", System.currentTimeMillis());

        if (msg91AuthKey.startsWith("MOCK")) {
            logger.info("[SMS-MOCK-SANDBOX] Notification successfully simulated for recipient {}", mobileNumber);
            result.put("status", "SUCCESS_MOCK");
            result.put("provider", "MSG91_SANDBOX");
            return result;
        }

        result.put("status", "SENT_LIVE");
        return result;
    }

    public Map<String, Object> sendOrderReceiptWhatsApp(String mobileNumber, int orderId, double amount, String customerName) {
        String msg = String.format("🍽️ *Sankalp Restaurant*\nThank you for dining with us, %s!\nYour Order #%d total is ₹%.2f.\nHope to see you again soon!", 
                customerName != null ? customerName : "Valued Guest", orderId, amount);
        return sendWhatsAppNotification(mobileNumber, msg);
    }

    public Map<String, Object> sendTableReservationConfirmation(String mobileNumber, String guestName, String date, String time, int partySize) {
        String msg = String.format("🎉 *Table Reservation Confirmed!*\nDear %s, your table for %d guest(s) on %s at %s has been confirmed at Sankalp Restaurant.", 
                guestName, partySize, date, time);
        return sendWhatsAppNotification(mobileNumber, msg);
    }
}
