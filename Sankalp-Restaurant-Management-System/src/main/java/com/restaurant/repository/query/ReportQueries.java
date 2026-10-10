package com.restaurant.repository.query;

/**
 * SQL Queries for Reporting and Sales Analytics.
 */
public final class ReportQueries {

    private ReportQueries() {
        // Prevent instantiation
    }

    public static final String GET_TOTAL_SALES = """
            SELECT IFNULL(SUM(grand_total), 0)
            FROM bill
            WHERE payment_status = 'PAID' AND restaurant_id = ?
            """;

    public static final String GET_DATE_WISE_SALES = """
            SELECT
                DATE(bill_date) AS reportDate,
                COUNT(order_id) AS totalOrders,
                IFNULL(SUM(grand_total), 0) AS totalSales
            FROM bill
            WHERE payment_status = 'PAID' AND restaurant_id = ?
            GROUP BY DATE(bill_date)
            ORDER BY reportDate DESC
            """;

    public static final String GET_SALES_BY_DATE_RANGE = """
            SELECT
                DATE(bill_date) AS reportDate,
                COUNT(order_id) AS totalOrders,
                IFNULL(SUM(grand_total), 0) AS totalSales
            FROM bill
            WHERE payment_status = 'PAID' AND restaurant_id = ?
            AND DATE(bill_date) BETWEEN ? AND ?
            GROUP BY DATE(bill_date)
            ORDER BY reportDate DESC
            """;

    public static final String GET_TOTAL_ORDERS = """
            SELECT COUNT(*)
            FROM bill
            WHERE payment_status = 'PAID' AND restaurant_id = ?
            """;

    public static final String GET_TOTAL_CUSTOMERS = """
            SELECT COUNT(*)
            FROM customer
            WHERE status = 'ACTIVE' AND restaurant_id = ?
            """;

    public static final String GET_TOP_MENU = """
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
            WHERE b.payment_status = 'PAID' AND b.restaurant_id = ?
            GROUP BY
                m.item_id,
                m.item_name
            ORDER BY totalQuantity DESC
            LIMIT 5
            """;
}
