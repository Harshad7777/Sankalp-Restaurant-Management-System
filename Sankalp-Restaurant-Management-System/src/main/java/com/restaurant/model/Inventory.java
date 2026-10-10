package com.restaurant.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an Inventory item in the Restaurant Management System.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    private int inventoryId;
    private int restaurantId;
    private String itemName;
    private String category;
    private double quantity;
    private String unit;
    private double minThreshold;
    private double costPerUnit;
    private String supplierName;
    private String supplierContact;
    private LocalDateTime lastRestocked;
    private String status; // IN_STOCK, LOW_STOCK, OUT_OF_STOCK
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Convenience constructor for legacy instantiation without restaurantId
    public Inventory(int inventoryId, String itemName, String category, double quantity, String unit, double minThreshold, double costPerUnit, String supplierName, String supplierContact, LocalDateTime lastRestocked, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(inventoryId, 0, itemName, category, quantity, unit, minThreshold, costPerUnit, supplierName, supplierContact, lastRestocked, status, createdAt, updatedAt);
    }
}
