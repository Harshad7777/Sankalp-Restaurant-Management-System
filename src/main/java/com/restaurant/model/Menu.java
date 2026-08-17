package com.restaurant.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {

    private Integer itemId;

    @NotNull(message = "Category ID is required")
    private Integer categoryId;

    private String categoryName;

    @NotBlank(message = "Item Name is required")
    @Size(min = 2, max = 100, message = "Item Name must be between 2 and 100 characters")
    private String itemName;

    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private Double price;

    private String image;

    @Pattern(regexp = "^(AVAILABLE|UNAVAILABLE)$", message = "Status must be AVAILABLE or UNAVAILABLE")
    private String status;
}