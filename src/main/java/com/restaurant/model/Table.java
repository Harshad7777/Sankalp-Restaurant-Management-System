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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Table {

    private Integer tableId;

    @NotBlank(message = "Table number is required")
    @Size(min = 1, max = 50, message = "Table number must be between 1 and 50 characters")
    private String tableNumber;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1 person")
    private Integer capacity;

    @Pattern(regexp = "^(AVAILABLE|OCCUPIED|RESERVED)$", message = "Table status must be AVAILABLE, OCCUPIED or RESERVED")
    private String tableStatus;
}