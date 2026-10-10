package com.restaurant.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for inventory stock adjustment requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockAdjustmentDTO {

    @NotNull(message = "Adjustment quantity is required")
    @DecimalMin(value = "0.01", message = "Quantity must be greater than zero")
    private Double quantity;

    @NotBlank(message = "Action type is required")
    @Pattern(regexp = "RESTOCK|CONSUME|WASTAGE|ADJUSTMENT", message = "Action type must be RESTOCK, CONSUME, WASTAGE, or ADJUSTMENT")
    private String actionType;

    private String reason;

    private String performedBy;
}
