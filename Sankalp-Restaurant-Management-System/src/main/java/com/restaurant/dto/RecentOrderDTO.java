package com.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for recent orders summary on dashboard.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentOrderDTO {

    private int orderId;
    private String customerName;
    private double totalAmount;
    private String status;
    private String orderDate;
}