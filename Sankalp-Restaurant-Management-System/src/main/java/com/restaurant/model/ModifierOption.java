package com.restaurant.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Modifier Option for a menu item modifier.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifierOption {

    private Integer optionId;
    private Integer modifierId;
    private String name;
    private BigDecimal price;
    private Boolean isAvailable;
}
