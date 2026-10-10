package com.restaurant.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.dto.RecentOrderDTO;
import com.restaurant.dto.TopSellingItemDTO;
import com.restaurant.repository.query.DashboardQueries;
import com.restaurant.security.SecurityUtil;

@Repository
public class DashboardRepository {

    private final JdbcTemplate jdbcTemplate;

    public DashboardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getTotalUsers() {
        return jdbcTemplate.queryForObject(
                DashboardQueries.COUNT_USERS,
                Integer.class,
                SecurityUtil.getCurrentRestaurantId());
    }

    public int getTotalCustomers() {
        return jdbcTemplate.queryForObject(
                DashboardQueries.COUNT_CUSTOMERS,
                Integer.class,
                SecurityUtil.getCurrentRestaurantId());
    }

    public int getTotalMenuItems() {
        return jdbcTemplate.queryForObject(
                DashboardQueries.COUNT_MENU_ITEMS,
                Integer.class,
                SecurityUtil.getCurrentRestaurantId());
    }

    public int getTotalOrders() {
        return jdbcTemplate.queryForObject(
                DashboardQueries.COUNT_ORDERS,
                Integer.class,
                SecurityUtil.getCurrentRestaurantId());
    }

    public int getTodayOrders() {
        return jdbcTemplate.queryForObject(
                DashboardQueries.COUNT_TODAY_ORDERS,
                Integer.class,
                SecurityUtil.getCurrentRestaurantId());
    }

    public double getTodaySales() {
        Double result = jdbcTemplate.queryForObject(
                DashboardQueries.SUM_TODAY_SALES,
                Double.class,
                SecurityUtil.getCurrentRestaurantId());
        return result != null ? result : 0.0;
    }

    public double getTotalRevenue() {
        Double result = jdbcTemplate.queryForObject(
                DashboardQueries.SUM_TOTAL_REVENUE,
                Double.class,
                SecurityUtil.getCurrentRestaurantId());
        return result != null ? result : 0.0;
    }

    public int getTotalCategories() {
        return jdbcTemplate.queryForObject(
                DashboardQueries.COUNT_CATEGORIES,
                Integer.class,
                SecurityUtil.getCurrentRestaurantId());
    }

    public List<RecentOrderDTO> getRecentOrders() {
        return jdbcTemplate.query(
                DashboardQueries.FIND_RECENT_ORDERS,
                (rs, rowNum) -> {
                    RecentOrderDTO dto = new RecentOrderDTO();
                    dto.setOrderId(rs.getInt("order_id"));
                    dto.setCustomerName(rs.getString("customer_name"));
                    dto.setTotalAmount(rs.getDouble("total_amount"));
                    dto.setStatus(rs.getString("order_status"));
                    if (rs.getTimestamp("order_date") != null) {
                        dto.setOrderDate(rs.getTimestamp("order_date").toString());
                    }
                    return dto;
                },
                SecurityUtil.getCurrentRestaurantId());
    }

    public List<TopSellingItemDTO> getTopSellingItems() {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        List<TopSellingItemDTO> items = jdbcTemplate.query(
                DashboardQueries.FIND_TOP_SELLING_ITEMS,
                (rs, rowNum) -> {
                    TopSellingItemDTO dto = new TopSellingItemDTO();
                    dto.setItemId(rs.getInt("item_id"));
                    dto.setItemName(rs.getString("item_name"));
                    dto.setImage(rs.getString("image"));
                    dto.setOrders(rs.getInt("total_orders"));
                    dto.setSoldQuantity(rs.getInt("sold_quantity"));
                    return dto;
                },
                resId);

        if (items == null || items.isEmpty()) {
            // Fallback to top menu items belonging to THIS specific logged-in restaurant
            String fallbackSql = "SELECT item_id, item_name, image FROM menu WHERE restaurant_id = ? ORDER BY item_id ASC LIMIT 5";
            items = jdbcTemplate.query(
                fallbackSql,
                (rs, rowNum) -> {
                    TopSellingItemDTO dto = new TopSellingItemDTO();
                    dto.setItemId(rs.getInt("item_id"));
                    dto.setItemName(rs.getString("item_name"));
                    dto.setImage(rs.getString("image"));
                    dto.setOrders(0);
                    dto.setSoldQuantity(0);
                    dto.setPercentage(100);
                    return dto;
                },
                resId
            );
        }

        int maxOrders = 0;
        for (TopSellingItemDTO item : items) {
            if (item.getOrders() > maxOrders) {
                maxOrders = item.getOrders();
            }
        }

        if (maxOrders > 0) {
            for (TopSellingItemDTO item : items) {
                double percentage = ((double) item.getOrders() / maxOrders) * 100.0;
                item.setPercentage(Math.round(percentage));
            }
        } else {
            int fallbackPercent = 100;
            for (TopSellingItemDTO item : items) {
                item.setPercentage(fallbackPercent);
                if (fallbackPercent > 40) fallbackPercent -= 15;
            }
        }

        return items;
    }

    public List<com.restaurant.dto.RevenueOverviewDTO> getRevenueOverview() {
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter dayFormatter = java.time.format.DateTimeFormatter.ofPattern("EEE", java.util.Locale.ENGLISH);
        java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");

        java.util.Map<String, Double> revenueMap = new java.util.HashMap<>();
        try {
            jdbcTemplate.query(
                    DashboardQueries.GET_WEEKLY_REVENUE_OVERVIEW,
                    rs -> {
                        String dateStr = rs.getString("bill_date");
                        double amount = rs.getDouble("daily_revenue");
                        revenueMap.put(dateStr, amount);
                    },
                    SecurityUtil.getCurrentRestaurantId());
        } catch (Exception e) {
            // Ignore if table empty
        }

        List<com.restaurant.dto.RevenueOverviewDTO> overviewList = new java.util.ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            java.time.LocalDate date = today.minusDays(i);
            String dateStr = date.format(dateFormatter);
            String dayName = date.format(dayFormatter);
            double amount = revenueMap.getOrDefault(dateStr, 0.0);

            overviewList.add(com.restaurant.dto.RevenueOverviewDTO.builder()
                    .day(dayName)
                    .date(dateStr)
                    .amount(amount)
                    .build());
        }

        return overviewList;
    }
}