package com.restaurant.service.mail;

import java.time.LocalDateTime;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.restaurant.config.JmsConfig;
import com.restaurant.dto.EmailMessageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JmsMailProducer {

    private final JmsTemplate jmsTemplate;

    public void sendEmail(EmailMessageDTO message) {
        if (message == null || message.getTo() == null || message.getTo().isBlank()) {
            log.warn("Cannot send email: recipient address is empty.");
            return;
        }

        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        }

        log.info("Enqueuing JMS Email message to [{}] -> Subject: '{}'", message.getTo(), message.getSubject());
        jmsTemplate.convertAndSend(JmsConfig.MAIL_QUEUE, message);
    }

    public void sendWelcomeEmail(String toEmail, String fullName, String username, String role) {
        String subject = "Welcome to Sankalp Restaurant Management System!";
        String htmlBody = String.format("""
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e2e8f0; border-radius: 8px;">
                    <h2 style="color: #f97316;">Welcome to Sankalp RMS</h2>
                    <p>Dear <strong>%s</strong>,</p>
                    <p>Your account has been registered successfully with role: <span style="background:#fef3c7; color:#b45309; padding:2px 8px; border-radius:4px; font-weight:bold;">%s</span>.</p>
                    <div style="background: #f8fafc; padding: 15px; border-radius: 6px; margin: 15px 0;">
                        <p style="margin: 0 0 8px 0;"><strong>Username:</strong> %s</p>
                        <p style="margin: 0;"><strong>Portal URL:</strong> <a href="http://localhost:8080/RMS_front_end_antigravity/login.jsp">Login to Dashboard</a></p>
                    </div>
                    <p>If you did not request this account, please contact our administrator immediately.</p>
                    <hr style="border: none; border-top: 1px solid #e2e8f0; margin: 20px 0;"/>
                    <p style="font-size: 12px; color: #64748b;">This is an automated notification from Sankalp Restaurant Management System.</p>
                </div>
                """, fullName != null ? fullName : username, role, username);

        EmailMessageDTO email = EmailMessageDTO.builder()
                .to(toEmail)
                .subject(subject)
                .body(htmlBody)
                .isHtml(true)
                .emailType("WELCOME")
                .timestamp(LocalDateTime.now())
                .build();

        sendEmail(email);
    }

    public void sendBillInvoiceEmail(String toEmail, String customerName, String billNo, double subtotal, double gst, double discount, double grandTotal, String paymentMethod) {
        String subject = String.format("Invoice Receipt for Order [%s] - Sankalp Restaurant", billNo);
        String htmlBody = String.format("""
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e2e8f0; border-radius: 8px;">
                    <h2 style="color: #f97316;">Sankalp Restaurant - Invoice Receipt</h2>
                    <p>Thank you for dining with us, <strong>%s</strong>!</p>
                    <table style="width: 100%%; border-collapse: collapse; margin: 15px 0;">
                        <tr style="background: #f8fafc;"><td style="padding: 8px; border: 1px solid #e2e8f0;"><strong>Bill Number:</strong></td><td style="padding: 8px; border: 1px solid #e2e8f0;">%s</td></tr>
                        <tr><td style="padding: 8px; border: 1px solid #e2e8f0;"><strong>Subtotal:</strong></td><td style="padding: 8px; border: 1px solid #e2e8f0;">₹%.2f</td></tr>
                        <tr><td style="padding: 8px; border: 1px solid #e2e8f0;"><strong>GST (5%%):</strong></td><td style="padding: 8px; border: 1px solid #e2e8f0;">₹%.2f</td></tr>
                        <tr><td style="padding: 8px; border: 1px solid #e2e8f0;"><strong>Discount:</strong></td><td style="padding: 8px; border: 1px solid #e2e8f0;">- ₹%.2f</td></tr>
                        <tr style="background: #fff7ed;"><td style="padding: 8px; border: 1px solid #fed7aa;"><strong>Grand Total:</strong></td><td style="padding: 8px; border: 1px solid #fed7aa; color: #ea580c; font-weight: bold; font-size: 16px;">₹%.2f</td></tr>
                        <tr><td style="padding: 8px; border: 1px solid #e2e8f0;"><strong>Payment Method:</strong></td><td style="padding: 8px; border: 1px solid #e2e8f0;">%s</td></tr>
                    </table>
                    <p>We look forward to serving you again soon!</p>
                    <hr style="border: none; border-top: 1px solid #e2e8f0; margin: 20px 0;"/>
                    <p style="font-size: 12px; color: #64748b;">Sankalp Restaurant Management System | Generated at %s</p>
                </div>
                """,
                customerName != null ? customerName : "Valued Customer",
                billNo, subtotal, gst, discount, grandTotal,
                paymentMethod != null ? paymentMethod : "CASH",
                LocalDateTime.now());

        EmailMessageDTO email = EmailMessageDTO.builder()
                .to(toEmail)
                .subject(subject)
                .body(htmlBody)
                .isHtml(true)
                .emailType("BILL_INVOICE")
                .timestamp(LocalDateTime.now())
                .build();

        sendEmail(email);
    }

    public void sendPasswordResetAlert(String toEmail, String fullName, String username) {
        String subject = "Security Alert: Password Updated for your Account";
        String htmlBody = String.format("""
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e2e8f0; border-radius: 8px;">
                    <h2 style="color: #dc2626;">Security Alert: Password Changed</h2>
                    <p>Dear <strong>%s</strong> (Username: <code>%s</code>),</p>
                    <p>The password for your Sankalp RMS account was updated on <strong>%s</strong>.</p>
                    <p>If you made this change, you can safely disregard this email.</p>
                    <p style="color: #dc2626;">If you did NOT authorize this change, please contact your restaurant administrator immediately to secure your account.</p>
                    <hr style="border: none; border-top: 1px solid #e2e8f0; margin: 20px 0;"/>
                    <p style="font-size: 12px; color: #64748b;">Sankalp Restaurant Management System Security Notification.</p>
                </div>
                """, fullName != null ? fullName : username, username, LocalDateTime.now());

        EmailMessageDTO email = EmailMessageDTO.builder()
                .to(toEmail)
                .subject(subject)
                .body(htmlBody)
                .isHtml(true)
                .emailType("PASSWORD_CHANGE_ALERT")
                .timestamp(LocalDateTime.now())
                .build();

        sendEmail(email);
    }
}
