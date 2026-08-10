package com.restaurant.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.dto.SalesReportDTO;
import com.restaurant.dto.TopMenuDTO;

@Repository
public class ReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // =====================================================
    // TOTAL SALES
    // GET /api/report/sales
    // =====================================================

    public double getTotalSales() {

        String sql = """
                SELECT IFNULL(SUM(grand_total), 0)
                FROM bill
                WHERE payment_status = 'PAID'
                """;

        return jdbcTemplate.queryForObject(
                sql,
                Double.class
        );
    }


    // =====================================================
    // DATE WISE SALES
    // GET /api/report/sales/date
    // =====================================================

    public List<SalesReportDTO> getDateWiseSales() {

        String sql = """
                SELECT
                    DATE(bill_date) AS reportDate,
                    COUNT(order_id) AS totalOrders,
                    IFNULL(SUM(grand_total), 0) AS totalSales
                FROM bill
                WHERE payment_status = 'PAID'
                GROUP BY DATE(bill_date)
                ORDER BY reportDate DESC
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(
                        SalesReportDTO.class
                )
        );
    }


 // =====================================================
 // TOTAL PAID ORDERS
 // GET /api/report/orders
 // =====================================================

 // =====================================================
 // DATE-RANGE SALES
 // GET /api/report/sales/range?startDate=&endDate=
 // =====================================================

 public List<SalesReportDTO> getSalesByDateRange(
         LocalDate startDate,
         LocalDate endDate) {

     String sql = """
             SELECT
                 DATE(bill_date) AS reportDate,
                 COUNT(order_id) AS totalOrders,
                 IFNULL(SUM(grand_total), 0) AS totalSales
             FROM bill
             WHERE payment_status = 'PAID'
             AND DATE(bill_date) BETWEEN ? AND ?
             GROUP BY DATE(bill_date)
             ORDER BY reportDate DESC
             """;

     return jdbcTemplate.query(
             sql,
             new BeanPropertyRowMapper<>(
                     SalesReportDTO.class
             ),
             startDate,
             endDate
     );
 }


 public int getTotalOrders() {

     String sql = """
             SELECT COUNT(*)
             FROM bill
             WHERE payment_status = 'PAID'
             """;

     return jdbcTemplate.queryForObject(
             sql,
             Integer.class
     );
 }


    // =====================================================
    // TOTAL CUSTOMERS
    // GET /api/report/customer
    // =====================================================

    public int getTotalCustomers() {

        String sql = """
                SELECT COUNT(*)
                FROM customer
                WHERE status = 'ACTIVE'
                """;

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class
        );
    }


    // =====================================================
    // TOP SELLING MENU
    // GET /api/report/top-menu
    // =====================================================

    public List<TopMenuDTO> getTopMenu() {

        String sql = """
                SELECT
                    m.item_name AS itemName,
                    SUM(od.quantity) AS totalQuantity,
                    IFNULL(SUM(od.subtotal), 0) AS totalSales
                FROM order_details od
                JOIN menu m
                    ON od.item_id = m.item_id
                JOIN orders o
                    ON od.order_id = o.order_id
                JOIN bill b
                    ON o.order_id = b.order_id
                WHERE b.payment_status = 'PAID'
                GROUP BY
                    m.item_id,
                    m.item_name
                ORDER BY totalQuantity DESC
                LIMIT 5
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(
                        TopMenuDTO.class
                )
        );
    }
}