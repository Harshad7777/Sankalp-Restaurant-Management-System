package com.restaurant.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int totalCategories;
    private int totalCustomers;
    private int totalMenuItems;
    private int totalOrders;

    private int todayOrders;

    private double todaySales;
    private double totalRevenue;

    private List<RecentOrderDTO> recentOrders;
    private List<TopSellingItemDTO> topSellingItems;
    private List<RevenueOverviewDTO> revenueOverview;
}