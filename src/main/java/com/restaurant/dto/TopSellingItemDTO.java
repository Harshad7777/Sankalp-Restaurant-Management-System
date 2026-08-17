package com.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopSellingItemDTO {

    private int itemId;
    private String itemName;
    private String image;
    private int orders;
    private int soldQuantity;
    private double percentage;
}