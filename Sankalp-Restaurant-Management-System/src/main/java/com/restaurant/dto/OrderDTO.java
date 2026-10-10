package com.restaurant.dto;

import java.util.List;
import com.restaurant.model.OrderDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for creating/updating Orders with item details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    @NotNull(message = "Customer id is required")
    @Min(value = 1, message = "Customer id must be greater than 0")
    private Integer customerId;

    @NotNull(message = "Table id is required")
    @Min(value = 1, message = "Table id must be greater than 0")
    private Integer tableId;

    @NotNull(message = "Staff id is required")
    @Min(value = 1, message = "Staff id must be greater than 0")
    private Integer staffId;

    @NotEmpty(message = "Order items cannot be empty")
    @Valid
    private List<OrderDetails> items;
}