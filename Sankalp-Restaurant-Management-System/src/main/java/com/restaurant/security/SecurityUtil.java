package com.restaurant.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.restaurant.exception.UnauthorizedException;

public final class SecurityUtil {

    private SecurityUtil() {
        // Prevent instantiation
    }

    public static Integer getOptionalCurrentRestaurantId() {
        Integer tenantId = TenantContext.getCurrentRestaurantId();
        if (tenantId != null) {
            return tenantId;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            if (userDetails.getRestaurantId() != null) {
                return userDetails.getRestaurantId();
            }
        }
        return null;
    }

    public static Integer getCurrentRestaurantId() {
        Integer id = getOptionalCurrentRestaurantId();
        if (id != null) {
            return id;
        }
        return 1; // Default fallback restaurant ID for public/unauthenticated requests
    }

    public static CustomUserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails;
        }
        throw new UnauthorizedException("User is not authenticated");
    }

    public static Integer getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    public static String getCurrentUserRole() {
        return getCurrentUser().getRole();
    }
}
