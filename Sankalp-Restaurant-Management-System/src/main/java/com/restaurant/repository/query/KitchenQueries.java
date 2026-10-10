package com.restaurant.repository.query;

/**
 * SQL Queries for Kitchen Display System (KDS) with strict multi-tenant isolation.
 */
public final class KitchenQueries {

    private KitchenQueries() {
        // Prevent instantiation
    }

    public static final String GET_KITCHEN_ORDERS = """
            SELECT
                o.order_id AS orderId,
                o.customer_id AS customerId,
                COALESCE(c.customer_name, 'Walk-in Customer') AS customerName,
                o.table_id AS tableId,
                o.order_date AS orderDate,
                COALESCE(o.order_status, 'NEW') AS orderStatus,
                COALESCE(o.remarks, '') AS remarks,
                COALESCE(o.total_amount, 0) AS totalAmount,
                od.order_detail_id AS detailId,
                od.item_id AS itemId,
                COALESCE(m.item_name, 'Unknown Item') AS itemName,
                COALESCE(od.quantity, 1) AS quantity,
                COALESCE(od.price, 0) AS price,
                COALESCE(od.subtotal, 0) AS subtotal,
                COALESCE(od.kitchen_status, 'NEW') AS kitchenStatus,
                od.is_extra AS isExtra,
                od.kitchen_note AS kitchenNote,
                od.prepared_by AS preparedBy,
                od.served_at AS servedAt,
                od.created_at AS createdAt
            FROM orders o
            LEFT JOIN customer c
                ON c.customer_id = o.customer_id
            INNER JOIN order_details od
                ON od.order_id = o.order_id
            LEFT JOIN menu m
                ON m.item_id = od.item_id
            WHERE
                o.restaurant_id = ?
                AND COALESCE(od.kitchen_status, 'NEW') IN (
                    'NEW',
                    'ACCEPTED',
                    'PREPARING',
                    'READY',
                    'SERVED'
                )
                AND COALESCE(o.order_status, 'NEW') NOT IN (
                    'CANCELLED',
                    'PAID',
                    'CLOSED',
                    'BILLED'
                )
            ORDER BY
                o.order_date DESC,
                od.order_detail_id ASC
            """;

    public static final String GET_KITCHEN_ORDER_BY_ID = """
            SELECT
                o.order_id AS orderId,
                o.customer_id AS customerId,
                COALESCE(c.customer_name, 'Walk-in Customer') AS customerName,
                o.table_id AS tableId,
                o.order_date AS orderDate,
                COALESCE(o.order_status, 'NEW') AS orderStatus,
                COALESCE(o.remarks, '') AS remarks,
                COALESCE(o.total_amount, 0) AS totalAmount,
                od.order_detail_id AS detailId,
                od.item_id AS itemId,
                COALESCE(m.item_name, 'Unknown Item') AS itemName,
                COALESCE(od.quantity, 1) AS quantity,
                COALESCE(od.price, 0) AS price,
                COALESCE(od.subtotal, 0) AS subtotal,
                COALESCE(od.kitchen_status, 'NEW') AS kitchenStatus,
                od.is_extra AS isExtra,
                od.kitchen_note AS kitchenNote,
                od.prepared_by AS preparedBy,
                od.served_at AS servedAt,
                od.created_at AS createdAt
            FROM orders o
            LEFT JOIN customer c
                ON c.customer_id = o.customer_id
            INNER JOIN order_details od
                ON od.order_id = o.order_id
            LEFT JOIN menu m
                ON m.item_id = od.item_id
            WHERE o.order_id = ? AND o.restaurant_id = ?
            ORDER BY od.order_detail_id ASC
            """;

    public static final String CHECK_ORDER_EXISTS = """
            SELECT COUNT(*)
            FROM orders
            WHERE order_id = ? AND restaurant_id = ?
            """;

    public static final String CHECK_DETAIL_EXISTS = """
            SELECT COUNT(*)
            FROM order_details od
            JOIN orders o ON od.order_id = o.order_id
            WHERE od.order_detail_id = ? AND o.restaurant_id = ?
            """;

    public static final String GET_ORDER_ID_FROM_DETAIL = """
            SELECT od.order_id
            FROM order_details od
            JOIN orders o ON od.order_id = o.order_id
            WHERE od.order_detail_id = ? AND o.restaurant_id = ?
            """;

    public static final String GET_KITCHEN_STATUS = """
            SELECT COALESCE(od.kitchen_status, 'NEW') AS kitchen_status
            FROM order_details od
            JOIN orders o ON od.order_id = o.order_id
            WHERE od.order_detail_id = ? AND o.restaurant_id = ?
            """;

    public static final String UPDATE_KITCHEN_STATUS = """
            UPDATE order_details od
            JOIN orders o ON od.order_id = o.order_id
            SET od.kitchen_status = ?
            WHERE od.order_detail_id = ? AND o.restaurant_id = ?
            """;

    public static final String UPDATE_PREPARED_BY = """
            UPDATE order_details od
            JOIN orders o ON od.order_id = o.order_id
            SET od.prepared_by = ?
            WHERE od.order_detail_id = ? AND o.restaurant_id = ?
            """;

    public static final String UPDATE_SERVED_AT = """
            UPDATE order_details od
            JOIN orders o ON od.order_id = o.order_id
            SET od.served_at = CURRENT_TIMESTAMP
            WHERE od.order_detail_id = ? AND o.restaurant_id = ?
            """;

    public static final String CLEAR_SERVED_AT = """
            UPDATE order_details od
            JOIN orders o ON od.order_id = o.order_id
            SET od.served_at = NULL
            WHERE od.order_detail_id = ? AND o.restaurant_id = ?
            """;

    public static final String UPDATE_ORDER_STATUS = """
            UPDATE orders
            SET order_status = ?
            WHERE order_id = ? AND restaurant_id = ?
            """;

    public static final String GET_ORDER_STATUS = """
            SELECT COALESCE(order_status, 'NEW') AS order_status
            FROM orders
            WHERE order_id = ? AND restaurant_id = ?
            """;

    public static final String GET_ORDER_KITCHEN_SUMMARY = """
            SELECT
                COUNT(*) AS totalItems,
                SUM(CASE WHEN COALESCE(od.kitchen_status, 'NEW') = 'NEW' THEN 1 ELSE 0 END) AS newItems,
                SUM(CASE WHEN od.kitchen_status = 'ACCEPTED' THEN 1 ELSE 0 END) AS acceptedItems,
                SUM(CASE WHEN od.kitchen_status = 'PREPARING' THEN 1 ELSE 0 END) AS preparingItems,
                SUM(CASE WHEN od.kitchen_status = 'READY' THEN 1 ELSE 0 END) AS readyItems,
                SUM(CASE WHEN od.kitchen_status = 'SERVED' THEN 1 ELSE 0 END) AS servedItems
            FROM order_details od
            JOIN orders o ON od.order_id = o.order_id
            WHERE od.order_id = ? AND o.restaurant_id = ?
            """;

    public static final String GET_KITCHEN_STATUSES = """
            SELECT COALESCE(od.kitchen_status, 'NEW')
            FROM order_details od
            JOIN orders o ON od.order_id = o.order_id
            WHERE od.order_id = ? AND o.restaurant_id = ?
            ORDER BY od.order_detail_id ASC
            """;

    public static final String GET_DETAIL_IDS = """
            SELECT od.order_detail_id
            FROM order_details od
            JOIN orders o ON od.order_id = o.order_id
            WHERE od.order_id = ? AND o.restaurant_id = ?
            ORDER BY od.order_detail_id ASC
            """;

    public static final String HAS_ORDER_DETAILS = """
            SELECT COUNT(*)
            FROM order_details od
            JOIN orders o ON od.order_id = o.order_id
            WHERE od.order_id = ? AND o.restaurant_id = ?
            """;

    public static final String GET_ITEM_COUNT_BY_STATUS = """
            SELECT COUNT(*)
            FROM order_details od
            JOIN orders o ON od.order_id = o.order_id
            WHERE od.order_id = ? AND o.restaurant_id = ?
            AND COALESCE(od.kitchen_status, 'NEW') = ?
            """;

    public static final String RESET_KITCHEN_STATUS = """
            UPDATE order_details od
            JOIN orders o ON od.order_id = o.order_id
            SET
                od.kitchen_status = 'NEW',
                od.prepared_by = NULL,
                od.served_at = NULL
            WHERE od.order_detail_id = ? AND o.restaurant_id = ?
            """;
}
