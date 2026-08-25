package com.restaurant.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.Claims;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String validSecret = "test-secret-key-that-is-at-least-32-bytes-long-for-hs256!";
    private final long expirationMs = 3600000; // 1 hour

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", validSecret);
        ReflectionTestUtils.setField(jwtUtil, "expirationMs", expirationMs);
        ReflectionTestUtils.invokeMethod(jwtUtil, "validateSecret");
    }

    @Test
    void testGenerateAndExtractUsername() {
        String username = "admin";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        String extractedUser = jwtUtil.extractUsername(token);
        assertEquals(username, extractedUser);
    }

    @Test
    void testExtractClaims() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        Claims claims = jwtUtil.extractClaims(token);
        assertNotNull(claims);
        assertEquals(username, claims.getSubject());
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void testValidateToken_ValidToken() {
        String token = jwtUtil.generateToken("staffUser");
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void testValidateToken_InvalidToken() {
        String invalidToken = "invalid.jwt.token";
        assertFalse(jwtUtil.validateToken(invalidToken));
    }

    @Test
    void testValidateToken_ExpiredToken() {
        JwtUtil expiredJwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(expiredJwtUtil, "secret", validSecret);
        ReflectionTestUtils.setField(expiredJwtUtil, "expirationMs", -10000L); // expired 10s ago
        ReflectionTestUtils.invokeMethod(expiredJwtUtil, "validateSecret");

        String expiredToken = expiredJwtUtil.generateToken("chefUser");
        assertFalse(expiredJwtUtil.validateToken(expiredToken));
    }

    @Test
    void testValidateSecret_ShortSecretThrowsException() {
        JwtUtil weakSecretJwt = new JwtUtil();
        ReflectionTestUtils.setField(weakSecretJwt, "secret", "too-short");
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> ReflectionTestUtils.invokeMethod(weakSecretJwt, "validateSecret")
        );
        assertTrue(exception.getMessage().contains("at least 32 bytes"));
    }
}
