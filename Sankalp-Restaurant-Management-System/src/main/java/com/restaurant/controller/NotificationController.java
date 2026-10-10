package com.restaurant.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.service.NotificationService;

@RestController
@RequestMapping({"/notifications", "/api/notifications"})
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(@org.springframework.beans.factory.annotation.Qualifier("rmsNotificationService") NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/whatsapp/send")
    public ResponseEntity<Map<String, Object>> sendWhatsApp(@RequestBody Map<String, String> req) {
        String phone = req.get("phone");
        String message = req.get("message");
        return ResponseEntity.ok(notificationService.sendWhatsAppNotification(phone, message));
    }

    @PostMapping("/whatsapp/order-receipt")
    public ResponseEntity<Map<String, Object>> sendOrderReceipt(@RequestBody Map<String, Object> req) {
        String phone = (String) req.get("phone");
        int orderId = Integer.parseInt(req.get("orderId").toString());
        double amount = Double.parseDouble(req.get("amount").toString());
        String name = (String) req.get("customerName");
        return ResponseEntity.ok(notificationService.sendOrderReceiptWhatsApp(phone, orderId, amount, name));
    }

    @PostMapping("/sms/send")
    public ResponseEntity<Map<String, Object>> sendSMS(@RequestBody Map<String, String> req) {
        String phone = req.get("phone");
        String message = req.get("message");
        return ResponseEntity.ok(notificationService.sendSMSNotification(phone, message));
    }
}
