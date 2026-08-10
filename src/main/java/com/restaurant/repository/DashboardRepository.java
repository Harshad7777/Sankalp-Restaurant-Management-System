package com.restaurant.repository;


import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.dto.RecentOrderDTO;


@Repository
public class DashboardRepository {


    private final JdbcTemplate jdbcTemplate;



    public DashboardRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }





    // ================================
    // TOTAL USERS
    // ================================

    public int getTotalUsers(){

        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users",
                Integer.class);
    }





    // ================================
    // TOTAL CUSTOMERS
    // ================================

    public int getTotalCustomers(){

        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM customer",
                Integer.class);
    }





    // ================================
    // TOTAL MENU ITEMS
    // ================================

    public int getTotalMenuItems(){

        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM menu",
                Integer.class);
    }





    // ================================
    // TOTAL ORDERS
    // ================================

    public int getTotalOrders(){

        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM orders",
                Integer.class);
    }





    // ================================
    // TODAY ORDERS
    // ================================

    public int getTodayOrders(){

        String sql = """
                SELECT COUNT(*)
                FROM orders
                WHERE DATE(order_date)=CURDATE()
                """;


        return jdbcTemplate.queryForObject(
                sql,
                Integer.class);
    }





    // ================================
    // TODAY SALES
    // ================================

    public double getTodaySales(){

        String sql = """
            SELECT IFNULL(SUM(grand_total),0)
            FROM bill
            WHERE DATE(bill_date)=CURDATE()
            """;

        return jdbcTemplate.queryForObject(sql, Double.class);
    }





    // ================================
    // TOTAL REVENUE
    // ================================
    public double getTotalRevenue(){

        String sql = """
            SELECT IFNULL(SUM(grand_total),0)
            FROM bill
            WHERE payment_status='PAID'
            """;	

        return jdbcTemplate.queryForObject(sql, Double.class);
    }
    
    //Add Total Categories
    public int getTotalCategories() {

        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM category",
                Integer.class);

    }
    
   // Add Recent Orders
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

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            RecentOrderDTO dto = new RecentOrderDTO();

            dto.setOrderId(rs.getInt("order_id"));
            dto.setCustomerName(rs.getString("customer_name"));
            dto.setTotalAmount(rs.getDouble("total_amount"));
            dto.setStatus(rs.getString("order_status"));
            dto.setOrderDate(rs.getTimestamp("order_date").toString());

            return dto;
        });
    }

}