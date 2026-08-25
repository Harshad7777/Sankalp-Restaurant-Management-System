package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private MessageService messageService;

    @Test
    @DisplayName("Should resolve message from message bundle")
    void testGetMessage() {
        when(messageSource.getMessage(eq("user.success.created"), isNull(), eq("user.success.created"), any(Locale.class)))
                .thenReturn("User registered successfully.");

        String message = messageService.getMessage("user.success.created");

        assertEquals("User registered successfully.", message);
    }

    @Test
    @DisplayName("Should return code if message not found")
    void testGetMessage_DefaultFallback() {
        when(messageSource.getMessage(eq("unknown.key"), isNull(), eq("unknown.key"), any(Locale.class)))
                .thenReturn("unknown.key");

        String message = messageService.getMessage("unknown.key");

        assertEquals("unknown.key", message);
    }
}
