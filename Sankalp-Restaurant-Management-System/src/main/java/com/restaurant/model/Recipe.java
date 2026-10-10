package com.restaurant.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Recipe mapping menu items to inventory ingredients.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {

    private Integer recipeId;
    private Integer restaurantId;
    private Integer itemId;
    private String itemName;
    private Integer inventoryId;
    private String inventoryItemName;
    private BigDecimal quantityRequired;
    private String unit;
    private LocalDateTime createdAt;
}
