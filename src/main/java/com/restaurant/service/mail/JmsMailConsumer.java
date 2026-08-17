package com.restaurant.service.mail;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.restaurant.config.JmsConfig;
import com.restaurant.dto.EmailMessageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JmsMailConsumer {

    private final ObjectProvider<JavaMailSender> mailSenderProvider;

    @Value("${spring.mail.username:}")
    private String fromEmail;

    @Value("${restaurant.mail.enabled:false}")
    private boolean mailEnabled;

    @JmsListener(destination = JmsConfig.MAIL_QUEUE, containerFactory = "jmsListenerContainerFactory")
    public void receiveAndProcessEmail(EmailMessageDTO email) {
        log.info("--> [JMS Queue] Received Email Task for [{}], Type: [{}], Subject: '{}'",
                email.getTo(), email.getEmailType(), email.getSubject());

        JavaMailSender mailSender = mailSenderProvider.getIfAvailable();

        if (mailEnabled && mailSender != null && fromEmail != null && !fromEmail.isBlank()) {
            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                helper.setFrom(fromEmail);
                helper.setTo(email.getTo());
                helper.setSubject(email.getSubject());
                helper.setText(email.getBody(), email.isHtml());

                mailSender.send(mimeMessage);
                log.info("✓ [SMTP] Email successfully dispatched to [{}] via JavaMailSender", email.getTo());
            } catch (Exception e) {
                log.error("✗ Failed to send email to [{}] via SMTP: {}", email.getTo(), e.getMessage(), e);
            }
        } else {
            log.info("ℹ [MOCK / DEV MAIL] Mail delivery simulated (SMTP disabled or unconfigured).");
            log.info("  To: {}", email.getTo());
            log.info("  Subject: {}", email.getSubject());
            log.info("  Type: {}", email.getEmailType());
            log.info("  Timestamp: {}", email.getTimestamp());
        }
    }
}
