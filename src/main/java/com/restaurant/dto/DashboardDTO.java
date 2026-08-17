package com.restaurant.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDTO {

    private int totalCategories;
    private int totalCustomers;
    private int totalMenuItems;
    private int totalOrders;

    private int todayOrders;

    private double todaySales;
    private double totalRevenue;

    private List<RecentOrderDTO> recentOrders;
    private List<TopSellingItemDTO> topSellingItems;
}