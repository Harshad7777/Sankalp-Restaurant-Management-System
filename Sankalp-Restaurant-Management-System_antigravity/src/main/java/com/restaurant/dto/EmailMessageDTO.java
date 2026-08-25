package com.restaurant.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EmailMessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String to;
    private String subject;
    private String body;
    private boolean isHtml;
    private String emailType;
    private LocalDateTime timestamp;

    public EmailMessageDTO() {
    }

    public EmailMessageDTO(String to, String subject, String body, boolean isHtml, String emailType, LocalDateTime timestamp) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.isHtml = isHtml;
        this.emailType = emailType;
        this.timestamp = timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String to;
        private String subject;
        private String body;
        private boolean isHtml;
        private String emailType;
        private LocalDateTime timestamp;

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder isHtml(boolean isHtml) {
            this.isHtml = isHtml;
            return this;
        }

        public Builder emailType(String emailType) {
            this.emailType = emailType;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public EmailMessageDTO build() {
            return new EmailMessageDTO(to, subject, body, isHtml, emailType, timestamp);
        }
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean isHtml) {
        this.isHtml = isHtml;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
