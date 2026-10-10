package com.restaurant.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Kitchen Display System (KDS) order items.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitchenOrderDTO {

    private Integer orderDetailId;
    private Integer orderId;

    private String orderNo;
    private String customerName;
    private String tableNumber;

    private Integer itemId;
    private String itemName;

    private Integer quantity;

    private Double price;
    private Double subtotal;

    private String kitchenNote;
    private String kitchenStatus;

    private Integer preparedBy;
    private String preparedByName;

    private LocalDateTime orderDate;
    private LocalDateTime servedAt;
}