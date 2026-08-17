package com.restaurant.repository.query;

/**
 * SQL Queries for Dashboard Analytics and Metrics.
 */
public final class DashboardQueries {

    private DashboardQueries() {
        // Prevent instantiation
    }

    public static final String COUNT_USERS = "SELECT COUNT(*) FROM users";

    public static final String COUNT_CUSTOMERS = "SELECT COUNT(*) FROM customer";

    public static final String COUNT_MENU_ITEMS = "SELECT COUNT(*) FROM menu";

    public static final String COUNT_ORDERS = "SELECT COUNT(*) FROM orders";

    public static final String COUNT_CATEGORIES = "SELECT COUNT(*) FROM category";

    public static final String COUNT_TODAY_ORDERS = """
            SELECT COUNT(*)
            FROM orders
            WHERE DATE(order_date) = CURDATE()
            """;

    public static final String SUM_TODAY_SALES = """
            SELECT IFNULL(SUM(grand_total), 0)
            FROM bill
            WHERE DATE(bill_date) = CURDATE()
            """;

    public static final String SUM_TOTAL_REVENUE = """
            SELECT IFNULL(SUM(grand_total), 0)
            FROM bill
            WHERE payment_status = 'PAID'
            """;

    public static final String FIND_RECENT_ORDERS = """
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

    public static final String FIND_TOP_SELLING_ITEMS = """
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
}
