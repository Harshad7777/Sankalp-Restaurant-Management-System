package com.restaurant.security;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restaurant.model.Users;
import com.restaurant.repository.UserRepository;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private static final Logger logger =
            LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserRepository repository;

    public CustomUserDetailsService(
            UserRepository repository) {

        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {

        // =====================================================
        // FIND USER
        // =====================================================

        Users user =
                repository.findByUsername(username);

        if (user == null) {

            logger.debug("User not found: {}", username);

            throw new UsernameNotFoundException(
                "User Not Found: " + username
            );
        }

        // =====================================================
        // GET ROLE
        // =====================================================

        String role =
                user.getRole();

        if (role == null ||
            role.trim().isEmpty()) {

            logger.debug("User role missing: {}", username);

            throw new UsernameNotFoundException(
                "User role not found"
            );
        }

        // =====================================================
        // NORMALIZE ROLE
        // =====================================================

        role =
                role.trim()
                    .toUpperCase();

        // =====================================================
        // REMOVE ROLE_ IF DATABASE HAS IT
        // =====================================================

        if (role.startsWith("ROLE_")) {

            role =
                role.substring(5);
        }

        // =====================================================
        // VALIDATE ROLE
        // =====================================================

        if (!role.equals("ADMIN") &&
            !role.equals("STAFF") &&
            !role.equals("CHEF")) {

            logger.warn("Invalid role for user {}: {}", username, role);

            throw new UsernameNotFoundException(
                "Invalid user role: " + role
            );
        }

        String authority =
                "ROLE_" + role;

        logger.debug(
                "Loaded user authority: username={}, authority={}",
                user.getUsername(), authority);

        // =====================================================
        // RETURN USER DETAILS
        // =====================================================

        return new User(
            user.getUsername(),
            user.getPassword(),
            Collections.singletonList(
                new SimpleGrantedAuthority(
                    authority
                )
            )
        );
    }
}
