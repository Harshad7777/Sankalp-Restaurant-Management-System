package com.restaurant.service.mail;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.restaurant.config.JmsConfig;
import com.restaurant.dto.EmailMessageDTO;

@Service
public class JmsMailProducer {

    private static final Logger log = LoggerFactory.getLogger(JmsMailProducer.class);

    private final JmsTemplate jmsTemplate;
    private final JavaMailSender mailSender;
    private final org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    @Value("${spring.mail.username:harshadrakshe28@gmail.com}")
    private String fromEmail;

    @Value("${app.frontend-url:https://restaurantms.co.in}")
    private String frontendUrl;

    public JmsMailProducer(ObjectProvider<JmsTemplate> jmsTemplateProvider,
                           ObjectProvider<JavaMailSender> mailSenderProvider,
                           org.springframework.jdbc.core.JdbcTemplate jdbcTemplate) {
        this.jmsTemplate = jmsTemplateProvider.getIfAvailable();
        this.mailSender = mailSenderProvider.getIfAvailable();
        this.jdbcTemplate = jdbcTemplate;
    }

    public void sendEmail(EmailMessageDTO message) {
        if (message == null || message.getTo() == null || message.getTo().isBlank()) {
            log.warn("Cannot send email: recipient address is empty.");
            return;
        }

        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        }

        if (jmsTemplate != null) {
            log.info("Enqueuing JMS Email message to [{}] -> Subject: '{}'", message.getTo(), message.getSubject());
            jmsTemplate.convertAndSend(JmsConfig.MAIL_QUEUE, message);
            return;
        }

        if (mailSender != null) {
            log.info("Sending direct SMTP Email to [{}] -> Subject: '{}'", message.getTo(), message.getSubject());
            CompletableFuture.runAsync(() -> {
                try {
                    MimeMessage mimeMessage = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    helper.setFrom(fromEmail);
                    helper.setTo(message.getTo());
                    helper.setSubject(message.getSubject());
                    helper.setText(message.getBody(), message.isHtml());
                    mailSender.send(mimeMessage);
                    log.info("✓ [SMTP SUCCESS] Email successfully sent to [{}]", message.getTo());
                } catch (Exception e) {
                    log.error("✗ [SMTP ERROR] Failed to send email to [{}]: {}", message.getTo(), e.getMessage());
                }
            });
            return;
        }

        log.info("ℹ [SIMULATED MAIL] Mail delivery skipped (SMTP/JMS not active). To: {}", message.getTo());
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
                        <p style="margin: 0;"><strong>Portal URL:</strong> <a href="%s/login.jsp">Login to Dashboard</a></p>
                    </div>
                    <p>If you did not request this account, please contact our administrator immediately.</p>
                    <hr style="border: none; border-top: 1px solid #e2e8f0; margin: 20px 0;"/>
                    <p style="font-size: 12px; color: #64748b;">This is an automated notification from Sankalp Restaurant Management System.</p>
                </div>
                """, fullName != null ? fullName : username, role, username, frontendUrl != null ? frontendUrl.replaceAll("/+$", "") : "");

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

    public void sendPasswordResetOtp(String toEmail, String fullName, String username, String otp) {
        String subject = "Your Password Reset OTP - Sankalp Restaurant System";
        String htmlBody = String.format("""
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 25px; border: 1px solid #e2e8f0; border-radius: 12px; background: #ffffff;">
                    <div style="text-align: center; margin-bottom: 20px;">
                        <h2 style="color: #f97316; margin: 0;">Sankalp Restaurant</h2>
                        <p style="color: #64748b; font-size: 14px; margin-top: 4px;">Password Reset Security Service</p>
                    </div>
                    <hr style="border: none; border-top: 1px solid #e2e8f0; margin: 20px 0;"/>
                    <p style="font-size: 15px; color: #334155;">Hello <strong>%s</strong> (Username: <code>%s</code>),</p>
                    <p style="font-size: 14px; color: #475569;">We received a request to reset your password for your Sankalp RMS account. Your 6-Digit Verification OTP code is:</p>
                    <div style="text-align: center; margin: 25px 0;">
                        <span style="font-size: 32px; font-weight: bold; letter-spacing: 6px; color: #ea580c; background: #fff7ed; padding: 12px 24px; border: 2px dashed #fdba74; border-radius: 8px; display: inline-block;">
                            %s
                        </span>
                    </div>
                    <p style="font-size: 13px; color: #dc2626; text-align: center;"><strong>⚠️ Note:</strong> This OTP is valid for 10 minutes. Do NOT share this code with anyone.</p>
                    <hr style="border: none; border-top: 1px solid #e2e8f0; margin: 25px 0;"/>
                    <p style="font-size: 12px; color: #94a3b8; text-align: center;">If you did not request a password reset, please ignore this message or contact your restaurant administrator.</p>
                </div>
                """, fullName != null ? fullName : username, username, otp);

        EmailMessageDTO email = EmailMessageDTO.builder()
                .to(toEmail)
                .subject(subject)
                .body(htmlBody)
                .isHtml(true)
                .emailType("PASSWORD_RESET_OTP")
                .timestamp(LocalDateTime.now())
                .build();

        sendEmail(email);
    }

    private String resolveRestaurantName(Integer restaurantId) {
        if (restaurantId == null || restaurantId <= 0) restaurantId = 1;
        try {
            java.util.List<String> names = jdbcTemplate.query(
                "SELECT restaurant_name FROM restaurants WHERE restaurant_id = ?",
                (rs, rowNum) -> rs.getString("restaurant_name"),
                restaurantId
            );
            if (!names.isEmpty() && names.get(0) != null && !names.get(0).isBlank()) {
                return names.get(0);
            }
            java.util.List<String> settingNames = jdbcTemplate.query(
                "SELECT restaurant_name FROM restaurant_settings WHERE restaurant_id = ?",
                (rs, rowNum) -> rs.getString("restaurant_name"),
                restaurantId
            );
            if (!settingNames.isEmpty() && settingNames.get(0) != null && !settingNames.get(0).isBlank()) {
                return settingNames.get(0);
            }
        } catch (Exception ignored) {}
        return "Restaurant Outlet #" + restaurantId;
    }

    private String resolveAdminEmail(Integer restaurantId) {
        if (restaurantId == null || restaurantId <= 0) restaurantId = 1;
        try {
            java.util.List<String> emails = jdbcTemplate.query(
                "SELECT email FROM restaurant_settings WHERE restaurant_id = ? AND email IS NOT NULL AND email != ''",
                (rs, rowNum) -> rs.getString("email"),
                restaurantId
            );
            if (!emails.isEmpty() && emails.get(0) != null && emails.get(0).contains("@")) {
                return emails.get(0);
            }
            java.util.List<String> adminEmails = jdbcTemplate.query(
                "SELECT email FROM users WHERE role = 'ADMIN' AND restaurant_id = ? AND email IS NOT NULL AND email != '' LIMIT 1",
                (rs, rowNum) -> rs.getString("email"),
                restaurantId
            );
            if (!adminEmails.isEmpty() && adminEmails.get(0) != null && adminEmails.get(0).contains("@")) {
                return adminEmails.get(0);
            }
        } catch (Exception ignored) {}
        return fromEmail;
    }

    // =====================================================
    // TABLE RESERVATION NOTIFICATION SERVICES (EMAIL & SMS)
    // =====================================================

    public void sendReservationSubmittedNotification(com.restaurant.model.Reservation reservation) {
        if (reservation == null) return;

        Integer resId = reservation.getRestaurantId();
        if (resId == null || resId <= 0) {
            resId = 1;
        }

        String restaurantName = resolveRestaurantName(resId);
        String adminEmail = resolveAdminEmail(resId);

        String phone = reservation.getGuestPhone() != null ? reservation.getGuestPhone().trim() : "";
        String guestEmail = reservation.getGuestEmail() != null && !reservation.getGuestEmail().isBlank()
                ? reservation.getGuestEmail().trim()
                : "";

        String guestName = reservation.getGuestName() != null ? reservation.getGuestName() : "Guest";
        String code = reservation.getReservationCode() != null ? reservation.getReservationCode() : "RES-SUBMITTED";
        String date = reservation.getBookingDate() != null ? reservation.getBookingDate().toString() : "-";
        String time = reservation.getBookingTime() != null ? reservation.getBookingTime() : "-";
        int guests = reservation.getGuestCount() != null ? reservation.getGuestCount() : 2;
        String specialReq = reservation.getSpecialRequest() != null ? reservation.getSpecialRequest() : "None";

        // Log SMS Notification Dispatch
        log.info("📲 [SMS SENT] Mobile Confirmation sent to +91 {}: 'Dear {}, your table booking request [{}] for {} guest(s) on {} @ {} at {} has been RECEIVED.'",
                phone, guestName, code, guests, date, time, restaurantName);

        // 1. DISPATCH GUEST HTML CONFIRMATION EMAIL
        if (guestEmail.contains("@")) {
            String subject = "Table Reservation Requested - " + code + " | " + restaurantName;
            String htmlBody = String.format("""
                    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 25px; border: 1px solid #d4af37; border-radius: 12px; background: #ffffff;">
                        <div style="text-align: center; margin-bottom: 20px;">
                            <h2 style="color: #d4af37; margin: 0; font-size: 24px;">%s</h2>
                            <p style="color: #64748b; font-size: 14px; margin-top: 4px;">Table Reservation Confirmation</p>
                        </div>
                        <hr style="border: none; border-top: 1px solid #f1f5f9; margin: 15px 0;"/>
                        <h3 style="color: #1e293b; margin-top: 0;">Reservation Request Received!</h3>
                        <p style="font-size: 15px; color: #475569;">Dear <strong>%s</strong>,</p>
                        <p style="font-size: 14px; color: #475569;">Thank you for reserving with <strong>%s</strong>. Your table request details are below:</p>
                        
                        <div style="background: #fafaf9; padding: 18px; border-radius: 8px; border-left: 4px solid #d4af37; margin: 20px 0;">
                            <p style="margin: 0 0 10px 0;"><strong>Booking Reference:</strong> <span style="color:#d4af37; font-weight:bold; font-size:16px;">%s</span></p>
                            <p style="margin: 0 0 10px 0;"><strong>Mobile Number:</strong> +91 %s</p>
                            <p style="margin: 0 0 10px 0;"><strong>Date & Time Slot:</strong> %s @ %s</p>
                            <p style="margin: 0 0 10px 0;"><strong>Party Size:</strong> %d Guest(s)</p>
                            <p style="margin: 0 0 10px 0;"><strong>Special Request:</strong> %s</p>
                            <p style="margin: 0;"><strong>Status:</strong> <span style="background:#fef3c7; color:#b45309; padding:3px 10px; border-radius:12px; font-size:12px; font-weight:bold;">PENDING CONFIRMATION</span></p>
                        </div>

                        <p style="font-size: 14px; color: #475569;">Our concierge team is reviewing seating availability. You will receive an automated follow-up notification once a table is assigned and confirmed.</p>
                        <hr style="border: none; border-top: 1px solid #f1f5f9; margin: 20px 0;"/>
                        <p style="font-size: 12px; color: #94a3b8; text-align: center;">%s Concierge Team</p>
                    </div>
                    """, restaurantName, guestName, restaurantName, code, phone, date, time, guests, specialReq, restaurantName);

            EmailMessageDTO guestEmailMsg = EmailMessageDTO.builder()
                    .to(guestEmail)
                    .subject(subject)
                    .body(htmlBody)
                    .isHtml(true)
                    .emailType("RESERVATION_SUBMITTED_GUEST")
                    .timestamp(LocalDateTime.now())
                    .build();

            sendEmail(guestEmailMsg);
        }

        // 2. DISPATCH RESTAURANT ADMIN NOTIFICATION EMAIL
        if (adminEmail != null && adminEmail.contains("@")) {
            String adminSubject = "🚨 New Table Reservation Request - " + code + " | " + restaurantName;
            String adminHtmlBody = String.format("""
                    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 25px; border: 1px solid #2563eb; border-radius: 12px; background: #ffffff;">
                        <div style="text-align: center; margin-bottom: 20px;">
                            <h2 style="color: #2563eb; margin: 0; font-size: 24px;">%s</h2>
                            <p style="color: #64748b; font-size: 14px; margin-top: 4px;">New Reservation Alert - Admin Management</p>
                        </div>
                        <hr style="border: none; border-top: 1px solid #f1f5f9; margin: 15px 0;"/>
                        <h3 style="color: #1e293b; margin-top: 0;">A New Table Reservation Requires Review!</h3>
                        <p style="font-size: 14px; color: #475569;">Hello Admin of <strong>%s</strong>,</p>
                        <p style="font-size: 14px; color: #475569;">A new customer has submitted a table reservation request. Please review the guest details below:</p>
                        
                        <div style="background: #eff6ff; padding: 18px; border-radius: 8px; border-left: 4px solid #2563eb; margin: 20px 0;">
                            <p style="margin: 0 0 10px 0;"><strong>Reservation Code:</strong> <span style="color:#2563eb; font-weight:bold; font-size:16px;">%s</span></p>
                            <p style="margin: 0 0 10px 0;"><strong>Guest Name:</strong> %s</p>
                            <p style="margin: 0 0 10px 0;"><strong>Phone Number:</strong> +91 %s</p>
                            <p style="margin: 0 0 10px 0;"><strong>Email Address:</strong> %s</p>
                            <p style="margin: 0 0 10px 0;"><strong>Date & Time Slot:</strong> %s @ %s</p>
                            <p style="margin: 0 0 10px 0;"><strong>Guest Count:</strong> %d Guest(s)</p>
                            <p style="margin: 0 0 10px 0;"><strong>Special Notes:</strong> %s</p>
                            <p style="margin: 0;"><strong>Current Status:</strong> <span style="background:#fef3c7; color:#b45309; padding:3px 10px; border-radius:12px; font-size:12px; font-weight:bold;">PENDING</span></p>
                        </div>

                        <p style="font-size: 14px; color: #475569;">Please log into your restaurant management portal (Table Dashboard) to approve, assign a table, or cancel this reservation.</p>
                        <hr style="border: none; border-top: 1px solid #f1f5f9; margin: 20px 0;"/>
                        <p style="font-size: 12px; color: #94a3b8; text-align: center;">%s Admin Operations Alert</p>
                    </div>
                    """, restaurantName, restaurantName, code, guestName, phone, guestEmail.isEmpty() ? "Not Provided" : guestEmail, date, time, guests, specialReq, restaurantName);

            EmailMessageDTO adminEmailMsg = EmailMessageDTO.builder()
                    .to(adminEmail)
                    .subject(adminSubject)
                    .body(adminHtmlBody)
                    .isHtml(true)
                    .emailType("RESERVATION_SUBMITTED_ADMIN")
                    .timestamp(LocalDateTime.now())
                    .build();

            sendEmail(adminEmailMsg);
        }
    }

    public void sendReservationStatusNotification(com.restaurant.model.Reservation reservation) {
        if (reservation == null) return;

        Integer resId = reservation.getRestaurantId();
        if (resId == null || resId <= 0) {
            resId = 1;
        }

        String restaurantName = resolveRestaurantName(resId);
        String phone = reservation.getGuestPhone() != null ? reservation.getGuestPhone().trim() : "";
        String emailAddr = reservation.getGuestEmail() != null && !reservation.getGuestEmail().isBlank()
                ? reservation.getGuestEmail().trim()
                : fromEmail;

        String guestName = reservation.getGuestName() != null ? reservation.getGuestName() : "Guest";
        String code = reservation.getReservationCode() != null ? reservation.getReservationCode() : "RES-UPDATE";
        String date = reservation.getBookingDate() != null ? reservation.getBookingDate().toString() : "-";
        String time = reservation.getBookingTime() != null ? reservation.getBookingTime() : "-";
        int guests = reservation.getGuestCount() != null ? reservation.getGuestCount() : 2;
        String status = reservation.getStatus() != null ? reservation.getStatus().toUpperCase() : "UPDATED";
        String tableNum = reservation.getTableNumber() != null && !reservation.getTableNumber().isBlank()
                ? (" (Table " + reservation.getTableNumber() + ")")
                : "";

        // Log SMS Notification Dispatch
        log.info("📲 [SMS SENT] Mobile Notification sent to +91 {}: 'Dear {}, your table reservation [{}] status at {} is now: {}{}.'",
                phone, guestName, code, restaurantName, status, tableNum);

        // Dispatch Rich HTML Email
        if (emailAddr != null && emailAddr.contains("@")) {
            String statusColor = "CONFIRMED".equalsIgnoreCase(status) ? "#16a34a" : ("REJECTED".equalsIgnoreCase(status) ? "#dc2626" : "#2563eb");
            String subject = "Table Reservation " + status + " - " + code + " | " + restaurantName;
            String htmlBody = String.format("""
                    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 25px; border: 1px solid #d4af37; border-radius: 12px; background: #ffffff;">
                        <div style="text-align: center; margin-bottom: 20px;">
                            <h2 style="color: #d4af37; margin: 0; font-size: 24px;">%s</h2>
                            <p style="color: #64748b; font-size: 14px; margin-top: 4px;">Table Reservation Update</p>
                        </div>
                        <hr style="border: none; border-top: 1px solid #f1f5f9; margin: 15px 0;"/>
                        <h3 style="color: %s; margin-top: 0;">Reservation Status Updated: %s</h3>
                        <p style="font-size: 15px; color: #475569;">Dear <strong>%s</strong>,</p>
                        <p style="font-size: 14px; color: #475569;">Your dining reservation status at <strong>%s</strong> has been updated by our concierge team.</p>
                        
                        <div style="background: #fafaf9; padding: 18px; border-radius: 8px; border-left: 4px solid %s; margin: 20px 0;">
                            <p style="margin: 0 0 10px 0;"><strong>Booking Reference:</strong> <span style="color:#d4af37; font-weight:bold; font-size:16px;">%s</span></p>
                            <p style="margin: 0 0 10px 0;"><strong>Mobile Contact:</strong> +91 %s</p>
                            <p style="margin: 0 0 10px 0;"><strong>Date & Time Slot:</strong> %s @ %s</p>
                            <p style="margin: 0 0 10px 0;"><strong>Party Size:</strong> %d Guest(s)</p>
                            <p style="margin: 0;"><strong>Reservation Status:</strong> <span style="color:%s; font-weight:bold; font-size:15px;">%s %s</span></p>
                        </div>

                        <p style="font-size: 14px; color: #475569;">%s</p>
                        <hr style="border: none; border-top: 1px solid #f1f5f9; margin: 20px 0;"/>
                        <p style="font-size: 12px; color: #94a3b8; text-align: center;">%s Concierge Team</p>
                    </div>
                    """, restaurantName, statusColor, status, guestName, restaurantName, statusColor, code, phone, date, time, guests, statusColor, status, tableNum,
                    "CONFIRMED".equalsIgnoreCase(status) ? "Your table is ready! Please arrive 5 minutes prior to your time slot." : ("Thank you for choosing " + restaurantName + "."), restaurantName);

            EmailMessageDTO emailMsg = EmailMessageDTO.builder()
                    .to(emailAddr)
                    .subject(subject)
                    .body(htmlBody)
                    .isHtml(true)
                    .emailType("RESERVATION_STATUS_UPDATE")
                    .timestamp(LocalDateTime.now())
                    .build();

            sendEmail(emailMsg);
        }
    }
}
