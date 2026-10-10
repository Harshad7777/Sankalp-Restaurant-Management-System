package com.restaurant.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Inventory creation and update requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequestDTO {

    private Integer inventoryId;

    @NotBlank(message = "Item name is required")
    @Size(max = 100, message = "Item name cannot exceed 100 characters")
    private String itemName;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category cannot exceed 50 characters")
    private String category;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Quantity cannot be negative")
    private Double quantity;

    @NotBlank(message = "Unit of measurement is required")
    @Size(max = 20, message = "Unit cannot exceed 20 characters")
    private String unit;

    @NotNull(message = "Minimum threshold is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Minimum threshold cannot be negative")
    private Double minThreshold;

    @NotNull(message = "Cost per unit is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Cost per unit cannot be negative")
    private Double costPerUnit;

    @Size(max = 100, message = "Supplier name cannot exceed 100 characters")
    private String supplierName;

    @Size(max = 50, message = "Supplier contact cannot exceed 50 characters")
    private String supplierContact;

    private String status;
}
