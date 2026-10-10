package com.restaurant.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for revenue overview charts.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueOverviewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String day;
    private String date;
    private double amount;
}
