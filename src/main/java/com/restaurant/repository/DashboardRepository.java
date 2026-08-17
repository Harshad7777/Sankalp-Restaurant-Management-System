package com.restaurant.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.dto.RecentOrderDTO;
import com.restaurant.dto.TopSellingItemDTO;

@Repository
public class DashboardRepository {

    private final JdbcTemplate jdbcTemplate;


    public DashboardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // =====================================================
    // TOTAL USERS
    // =====================================================

    public int getTotalUsers() {

        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users",
                Integer.class
        );
    }


    // =====================================================
    // TOTAL CUSTOMERS
    // =====================================================

    public int getTotalCustomers() {

        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM customer",
                Integer.class
        );
    }


    // =====================================================
    // TOTAL MENU ITEMS
    // =====================================================

    public int getTotalMenuItems() {

        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM menu",
                Integer.class
        );
    }


    // =====================================================
    // TOTAL ORDERS
    // =====================================================

    public int getTotalOrders() {

        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM orders",
                Integer.class
        );
    }


    // =====================================================
    // TODAY ORDERS
    // =====================================================

    public int getTodayOrders() {

        String sql = """
                SELECT COUNT(*)
                FROM orders
                WHERE DATE(order_date) = CURDATE()
                """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class
        );
    }


    // =====================================================
    // TODAY SALES
    // =====================================================

    public double getTodaySales() {

        String sql = """
                SELECT IFNULL(SUM(grand_total), 0)
                FROM bill
                WHERE DATE(bill_date) = CURDATE()
                """;

        Double result =
                jdbcTemplate.queryForObject(
                        sql,
                        Double.class
                );

        return result != null ? result : 0.0;
    }


    // =====================================================
    // TOTAL REVENUE
    // =====================================================

    public double getTotalRevenue() {

        String sql = """
                SELECT IFNULL(SUM(grand_total), 0)
                FROM bill
                WHERE payment_status = 'PAID'
                """;

        Double result =
                jdbcTemplate.queryForObject(
                        sql,
                        Double.class
                );

        return result != null ? result : 0.0;
    }


    // =====================================================
    // TOTAL CATEGORIES
    // =====================================================

    public int getTotalCategories() {

        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM category",
                Integer.class
        );
    }


    // =====================================================
    // RECENT ORDERS
    // =====================================================

    public List<RecentOrderDTO> getRecentOrders() {

        String sql = """
                SELECT
                    o.order_id,
                    c.customer_name,
                    o.total_amount,
                    o.order_status,
                    o.order_date
                FROM orders o
                INNER JOIN customer c
                    ON o.customer_id = c.customer_id
                ORDER BY o.order_date DESC
                LIMIT 5
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {

                    RecentOrderDTO dto =
                            new RecentOrderDTO();

                    dto.setOrderId(
                            rs.getInt("order_id")
                    );

                    dto.setCustomerName(
                            rs.getString("customer_name")
                    );

                    dto.setTotalAmount(
                            rs.getDouble("total_amount")
                    );

                    dto.setStatus(
                            rs.getString("order_status")
                    );

                    if (rs.getTimestamp("order_date") != null) {

                        dto.setOrderDate(
                                rs.getTimestamp(
                                        "order_date"
                                ).toString()
                        );

                    }

                    return dto;
                }
        );
    }


    // =====================================================
    // TOP SELLING ITEMS
    // =====================================================

    public List<TopSellingItemDTO> getTopSellingItems() {

        String sql = """
                SELECT
                    m.item_id,
                    m.item_name,
                    m.image,

                    COUNT(DISTINCT od.order_id) AS total_orders,

                    COALESCE(SUM(od.quantity), 0) AS sold_quantity

                FROM order_details od

                INNER JOIN menu m
                    ON od.item_id = m.item_id

                INNER JOIN orders o
                    ON od.order_id = o.order_id

                WHERE o.order_status <> 'CANCELLED'

                GROUP BY
                    m.item_id,
                    m.item_name,
                    m.image

                ORDER BY
                    total_orders DESC,
                    sold_quantity DESC

                LIMIT 5
                """;


        List<TopSellingItemDTO> items =
                jdbcTemplate.query(
                        sql,
                        (rs, rowNum) -> {

                            TopSellingItemDTO dto =
                                    new TopSellingItemDTO();

                            dto.setItemId(
                                    rs.getInt("item_id")
                            );

                            dto.setItemName(
                                    rs.getString("item_name")
                            );

                            dto.setImage(
                                    rs.getString("image")
                            );

                            dto.setOrders(
                                    rs.getInt("total_orders")
                            );

                            dto.setSoldQuantity(
                                    rs.getInt("sold_quantity")
                            );

                            return dto;
                        }
                );


        // =================================================
        // CALCULATE PERCENTAGE
        // =================================================

        int maxOrders = 0;

        for (TopSellingItemDTO item : items) {

            if (item.getOrders() > maxOrders) {

                maxOrders =
                        item.getOrders();
            }
        }


        if (maxOrders > 0) {

            for (TopSellingItemDTO item : items) {

                double percentage =
                        (
                            (double) item.getOrders()
                            /
                            maxOrders
                        ) * 100.0;


                item.setPercentage(
                        Math.round(percentage)
                );
            }
        }


        return items;
    }
}