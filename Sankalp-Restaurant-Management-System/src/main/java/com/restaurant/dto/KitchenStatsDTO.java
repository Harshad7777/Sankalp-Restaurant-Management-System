package com.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for kitchen status counts (New, Accepted, Preparing, Ready).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitchenStatsDTO {

    private int newCount;
    private int acceptedCount;
    private int preparingCount;
    private int readyCount;
}