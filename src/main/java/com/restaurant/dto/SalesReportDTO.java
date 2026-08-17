package com.restaurant.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesReportDTO {

    private LocalDate reportDate;
    private int totalOrders;
    private double totalSales;
}