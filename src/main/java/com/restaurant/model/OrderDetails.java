package com.restaurant.model;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetails {

    private Integer orderDetailId;
    private Integer orderId;

    @NotNull(message = "Menu item id required")
    @Min(
        value = 1,
        message = "Menu item id must be greater than 0"
    )
    private Integer itemId;

    private String itemName;

    @NotNull(message = "Quantity required")
    @Min(
        value = 1,
        message = "Quantity must be at least 1"
    )
    private Integer quantity;

    private Double price;
    private Double subtotal;

    private String kitchenNote;
    private String kitchenStatus;

    private Integer preparedBy;
    private String preparedByName;

    private LocalDateTime servedAt;
}