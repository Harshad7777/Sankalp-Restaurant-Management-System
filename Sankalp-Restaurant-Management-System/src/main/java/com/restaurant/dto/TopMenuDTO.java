package com.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for top menu items summary.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopMenuDTO {

    private String itemName;
    private int totalQuantity;
    private double totalSales;
}