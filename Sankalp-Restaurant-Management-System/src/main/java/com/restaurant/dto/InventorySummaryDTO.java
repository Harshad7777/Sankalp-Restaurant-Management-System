package com.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for inventory statistics and summary.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventorySummaryDTO {

    private long totalItems;
    private long inStockItems;
    private long lowStockItems;
    private long outOfStockItems;
    private double totalInventoryValue;
}
