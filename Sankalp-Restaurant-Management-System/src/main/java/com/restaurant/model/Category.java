package com.restaurant.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Menu Category in the Restaurant Management System.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    private Integer categoryId;
    private Integer restaurantId;

    @NotBlank(message = "Category Name is required")
    @Size(min = 2, max = 100, message = "Category Name must be between 2 and 100 characters")
    private String categoryName;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be ACTIVE or INACTIVE")
    private String status;
}