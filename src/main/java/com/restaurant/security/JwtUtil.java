package com.restaurant.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

    // Secret and expiry come from configuration (application.properties /
    // environment variables) instead of being hardcoded in source.
    // See application.properties: jwt.secret, jwt.expiration-ms
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms:3600000}")
    private long expirationMs;

    @PostConstruct
    private void validateSecret() {

        if (secret == null || secret.getBytes().length < 32) {

            // HS256 requires a key of at least 256 bits (32 bytes).
            // Failing fast here prevents accidentally running with a
            // weak/default key in production.
            throw new IllegalStateException(
                    "jwt.secret must be set and at least 32 bytes long. "
                    + "Set the JWT_SECRET environment variable."
            );
        }
    }

    // Generate JWT
    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + expirationMs)
                )
                .signWith(
                        Keys.hmacShaKeyFor(secret.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    // Extract Username
    public String extractUsername(String token) {

        return extractClaims(token).getSubject();
    }

    // Extract Claims
    public Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Validate Token
    public boolean validateToken(String token) {

        try {

            extractClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}
