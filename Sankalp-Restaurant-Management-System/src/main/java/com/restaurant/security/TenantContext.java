package com.restaurant.security;

/**
 * Thread-local context to hold the current request's restaurant (tenant) ID.
 */
public final class TenantContext {

    private static final ThreadLocal<Integer> CURRENT_RESTAURANT = new ThreadLocal<>();

    private TenantContext() {
        // Prevent instantiation
    }

    public static void setCurrentRestaurantId(Integer restaurantId) {
        CURRENT_RESTAURANT.set(restaurantId);
    }

    public static Integer getCurrentRestaurantId() {
        return CURRENT_RESTAURANT.get();
    }

    public static void clear() {
        CURRENT_RESTAURANT.remove();
    }
}
