package com.restaurant.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.dto.RecentOrderDTO;
import com.restaurant.dto.TopSellingItemDTO;
import com.restaurant.repository.query.DashboardQueries;

@Repository
public class DashboardRepository {

    private final JdbcTemplate jdbcTemplate;

    public DashboardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getTotalUsers() {
        return jdbcTemplate.queryForObject(
                DashboardQueries.COUNT_USERS,
                Integer.class);
    }

    public int getTotalCustomers() {
        return jdbcTemplate.queryForObject(
                DashboardQueries.COUNT_CUSTOMERS,
                Integer.class);
    }

    public int getTotalMenuItems() {
        return jdbcTemplate.queryForObject(
                DashboardQueries.COUNT_MENU_ITEMS,
                Integer.class);
    }

    public int getTotalOrders() {
        return jdbcTemplate.queryForObject(
                DashboardQueries.COUNT_ORDERS,
                Integer.class);
    }

    public int getTodayOrders() {
        return jdbcTemplate.queryForObject(
                DashboardQueries.COUNT_TODAY_ORDERS,
                Integer.class);
    }

    public double getTodaySales() {
        Double result = jdbcTemplate.queryForObject(
                DashboardQueries.SUM_TODAY_SALES,
                Double.class);
        return result != null ? result : 0.0;
    }

    public double getTotalRevenue() {
        Double result = jdbcTemplate.queryForObject(
                DashboardQueries.SUM_TOTAL_REVENUE,
                Double.class);
        return result != null ? result : 0.0;
    }

    public int getTotalCategories() {
        return jdbcTemplate.queryForObject(
                DashboardQueries.COUNT_CATEGORIES,
                Integer.class);
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
                });
    }

    public List<TopSellingItemDTO> getTopSellingItems() {
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
                });

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
        }

        return items;
    }
}