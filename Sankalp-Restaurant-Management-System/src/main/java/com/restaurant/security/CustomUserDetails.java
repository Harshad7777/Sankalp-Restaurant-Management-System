package com.restaurant.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private final Integer userId;
    private final String username;
    private final String password;
    private final String role;
    private final Integer restaurantId;
    private final String restaurantName;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(
            Integer userId,
            String username,
            String password,
            String role,
            Integer restaurantId,
            String restaurantName,
            Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.restaurantId = restaurantId != null ? restaurantId : 1;
        this.restaurantName = restaurantName;
        this.authorities = authorities;
    }

    public CustomUserDetails(
            Integer userId,
            String username,
            String password,
            String role,
            Integer restaurantId,
            String restaurantName) {
        this(
                userId,
                username,
                password,
                role,
                restaurantId,
                restaurantName,
                Collections.singletonList(new SimpleGrantedAuthority(role.startsWith("ROLE_") ? role : "ROLE_" + role))
        );
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
