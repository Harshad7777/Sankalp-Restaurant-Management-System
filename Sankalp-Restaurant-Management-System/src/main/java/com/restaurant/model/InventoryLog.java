package com.restaurant.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an Inventory Log entry for stock adjustments.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryLog {

    private int logId;
    private int inventoryId;
    private String itemName;
    private String actionType; // RESTOCK, CONSUME, WASTAGE, ADJUSTMENT
    private double quantity;
    private double previousQuantity;
    private double newQuantity;
    private String reason;
    private String performedBy;
    private LocalDateTime createdAt;
}
