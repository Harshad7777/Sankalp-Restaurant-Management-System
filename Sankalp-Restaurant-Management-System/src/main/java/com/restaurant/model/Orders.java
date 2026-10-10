package com.restaurant.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an Order entity in the Restaurant Management System.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {

    private Integer orderId;
    private Integer restaurantId;
    private String orderNo;
    private Integer customerId;
    private String customerName;

    private Integer tableId;
    private String tableNumber;

    private Integer staffId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String orderStatus;

    private String orderType;
    private String remarks;

    // Convenience constructor for legacy instantiation without restaurantId
    public Orders(Integer orderId, String orderNo, Integer customerId, String customerName, Integer tableId, String tableNumber, Integer staffId, LocalDateTime orderDate, Double totalAmount, String orderStatus, String orderType, String remarks) {
        this(orderId, 0, orderNo, customerId, customerName, tableId, tableNumber, staffId, orderDate, totalAmount, orderStatus, orderType, remarks);
    }
}