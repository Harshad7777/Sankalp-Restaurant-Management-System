package com.restaurant.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Dining Table entity in the Restaurant Management System.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Table {

    private Integer tableId;
    private Integer restaurantId;

    @NotBlank(message = "Table number is required")
    @Size(min = 1, max = 50, message = "Table number must be between 1 and 50 characters")
    private String tableNumber;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1 person")
    private Integer capacity;

    @Pattern(regexp = "^(AVAILABLE|OCCUPIED|RESERVED|CLEANING|BILLED|MAINTENANCE|INACTIVE)$", message = "Table status must be AVAILABLE, OCCUPIED, RESERVED, CLEANING, BILLED, MAINTENANCE or INACTIVE")
    private String tableStatus;

    @Builder.Default
    private Integer xPos = 0;

    @Builder.Default
    private Integer yPos = 0;

    @Builder.Default
    private String shape = "RECTANGLE";

    @Builder.Default
    private Integer width = 100;

    @Builder.Default
    private Integer height = 100;

    // Convenience constructor
    public Table(Integer tableId, Integer restaurantId, String tableNumber, Integer capacity, String tableStatus) {
        this(tableId, restaurantId, tableNumber, capacity, tableStatus, 0, 0, "RECTANGLE", 100, 100);
    }

    // Explicit getters/setters for xPos and yPos to support both getxPos() and getXPos() call styles
    public Integer getxPos() {
        return xPos;
    }

    public void setxPos(Integer xPos) {
        this.xPos = xPos;
    }

    public Integer getyPos() {
        return yPos;
    }

    public void setyPos(Integer yPos) {
        this.yPos = yPos;
    }
}