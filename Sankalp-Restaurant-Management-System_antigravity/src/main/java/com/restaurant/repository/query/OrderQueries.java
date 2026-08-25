package com.restaurant.repository.query;

/**
 * SQL Queries for Order Management.
 */
public final class OrderQueries {

    private OrderQueries() {
        // Prevent instantiation
    }

    public static final String INSERT_ORDER = """
            INSERT INTO orders
            (
                order_no,
                customer_id,
                table_id,
                staff_id,
                order_date,
                total_amount,
                order_status
            )
            VALUES (?, ?, ?, ?, NOW(), ?, ?)
            """;

    public static final String INSERT_ORDER_DETAIL = """
            INSERT INTO order_details
            (
                order_id,
                item_id,
                quantity,
                price,
                subtotal,
                kitchen_note,
                kitchen_status
            )
            VALUES (?, ?, ?, ?, ?, ?, 'NEW')
            """;

    public static final String UPDATE_ORDER_TOTAL = """
            UPDATE orders
            SET total_amount = ?
            WHERE order_id = ?
            """;

    public static final String GET_MENU_PRICE = """
            SELECT price
            FROM menu
            WHERE item_id = ?
            """;

    public static final String FIND_ALL_ORDERS = """
            SELECT
                o.order_id AS orderId,
                o.customer_id AS customerId,
                c.customer_name AS customerName,
                o.table_id AS tableId,
                rt.table_number AS tableNumber,
                o.staff_id AS staffId,
                o.order_date AS orderDate,
                o.total_amount AS totalAmount,
                o.order_status AS orderStatus,
                o.order_type AS orderType,
                o.remarks AS remarks
            FROM orders o
            INNER JOIN customer c
                ON o.customer_id = c.customer_id
            INNER JOIN restaurant_tables rt
                ON o.table_id = rt.table_id
            ORDER BY o.order_id DESC
            """;

    public static final String FIND_ORDERS_PAGE = """
            SELECT
                o.order_id AS orderId,
                o.customer_id AS customerId,
                c.customer_name AS customerName,
                o.table_id AS tableId,
                rt.table_number AS tableNumber,
                o.staff_id AS staffId,
                o.order_date AS orderDate,
                o.total_amount AS totalAmount,
                o.order_status AS orderStatus,
                o.order_type AS orderType,
                o.remarks AS remarks
            FROM orders o
            INNER JOIN customer c
                ON o.customer_id = c.customer_id
            INNER JOIN restaurant_tables rt
                ON o.table_id = rt.table_id
            ORDER BY o.order_id DESC
            LIMIT ? OFFSET ?
            """;

    public static final String COUNT_ORDERS = "SELECT COUNT(*) FROM orders";

    public static final String FIND_ORDER_BY_ID = """
            SELECT
                o.order_id AS orderId,
                o.customer_id AS customerId,
                c.customer_name AS customerName,
                o.table_id AS tableId,
                rt.table_number AS tableNumber,
                o.staff_id AS staffId,
                o.order_date AS orderDate,
                o.total_amount AS totalAmount,
                o.order_status AS orderStatus,
                o.order_type AS orderType,
                o.remarks AS remarks
            FROM orders o
            INNER JOIN customer c
                ON o.customer_id = c.customer_id
            INNER JOIN restaurant_tables rt
                ON o.table_id = rt.table_id
            WHERE o.order_id = ?
            """;

    public static final String FIND_ORDER_DETAILS = """
            SELECT
                od.order_detail_id AS orderDetailId,
                od.order_id AS orderId,
                od.item_id AS itemId,
                m.item_name AS itemName,
                od.quantity AS quantity,
                od.price AS price,
                od.subtotal AS subtotal,
                od.kitchen_note AS kitchenNote,
                od.kitchen_status AS kitchenStatus,
                od.prepared_by AS preparedBy,
                u.full_name AS preparedByName,
                od.served_at AS servedAt
            FROM order_details od
            INNER JOIN menu m
                ON od.item_id = m.item_id
            LEFT JOIN users u
                ON od.prepared_by = u.user_id
            WHERE od.order_id = ?
            ORDER BY od.order_detail_id
            """;

    public static final String UPDATE_STATUS = """
            UPDATE orders
            SET order_status = ?
            WHERE order_id = ?
            """;

    public static final String CANCEL_ORDER = """
            UPDATE orders
            SET order_status = 'CANCELLED'
            WHERE order_id = ?
            """;

    public static final String FIND_CUSTOMER_ORDERS = """
            SELECT
                o.order_id AS orderId,
                o.customer_id AS customerId,
                c.customer_name AS customerName,
                o.table_id AS tableId,
                rt.table_number AS tableNumber,
                o.staff_id AS staffId,
                o.order_date AS orderDate,
                o.total_amount AS totalAmount,
                o.order_status AS orderStatus,
                o.order_type AS orderType,
                o.remarks AS remarks
            FROM orders o
            INNER JOIN customer c
                ON o.customer_id = c.customer_id
            INNER JOIN restaurant_tables rt
                ON o.table_id = rt.table_id
            WHERE o.customer_id = ?
            ORDER BY o.order_date DESC
            """;

    public static final String FIND_TODAY_ORDERS = """
            SELECT
                o.order_id AS orderId,
                o.customer_id AS customerId,
                c.customer_name AS customerName,
                o.table_id AS tableId,
                rt.table_number AS tableNumber,
                o.staff_id AS staffId,
                o.order_date AS orderDate,
                o.total_amount AS totalAmount,
                o.order_status AS orderStatus,
                o.order_type AS orderType,
                o.remarks AS remarks
            FROM orders o
            INNER JOIN customer c
                ON o.customer_id = c.customer_id
            INNER JOIN restaurant_tables rt
                ON o.table_id = rt.table_id
            WHERE DATE(o.order_date) = CURDATE()
            ORDER BY o.order_date DESC
            """;

    public static final String FIND_TODAY_KITCHEN_ORDERS = """
            SELECT
                od.order_detail_id AS orderDetailId,
                od.order_id AS orderId,
                o.order_no AS orderNo,
                c.customer_name AS customerName,
                rt.table_number AS tableNumber,
                od.item_id AS itemId,
                m.item_name AS itemName,
                od.quantity AS quantity,
                od.price AS price,
                od.subtotal AS subtotal,
                od.kitchen_note AS kitchenNote,
                od.kitchen_status AS kitchenStatus,
                od.prepared_by AS preparedBy,
                u.full_name AS preparedByName,
                o.order_date AS orderDate,
                od.served_at AS servedAt
            FROM order_details od
            INNER JOIN orders o
                ON od.order_id = o.order_id
            LEFT JOIN customer c
                ON o.customer_id = c.customer_id
            LEFT JOIN restaurant_tables rt
                ON o.table_id = rt.table_id
            INNER JOIN menu m
                ON od.item_id = m.item_id
            LEFT JOIN users u
                ON od.prepared_by = u.user_id
            WHERE DATE(o.order_date) = CURDATE()
            AND o.order_status <> 'CANCELLED'
            ORDER BY
                CASE od.kitchen_status
                    WHEN 'NEW' THEN 1
                    WHEN 'ACCEPTED' THEN 2
                    WHEN 'PREPARING' THEN 3
                    WHEN 'READY' THEN 4
                    WHEN 'SERVED' THEN 5
                    ELSE 6
                END,
                o.order_date ASC
            """;

    public static final String GET_KITCHEN_STATS = """
            SELECT
                COALESCE(SUM(CASE WHEN od.kitchen_status = 'NEW' THEN 1 ELSE 0 END), 0) AS newCount,
                COALESCE(SUM(CASE WHEN od.kitchen_status = 'ACCEPTED' THEN 1 ELSE 0 END), 0) AS acceptedCount,
                COALESCE(SUM(CASE WHEN od.kitchen_status = 'PREPARING' THEN 1 ELSE 0 END), 0) AS preparingCount,
                COALESCE(SUM(CASE WHEN od.kitchen_status = 'READY' THEN 1 ELSE 0 END), 0) AS readyCount
            FROM order_details od
            INNER JOIN orders o
                ON od.order_id = o.order_id
            WHERE DATE(o.order_date) = CURDATE()
            AND o.order_status <> 'CANCELLED'
            """;

    public static final String GET_ORDER_ID_BY_DETAIL_ID = """
            SELECT order_id
            FROM order_details
            WHERE order_detail_id = ?
            """;

    public static final String UPDATE_DETAIL_NEW = """
            UPDATE order_details
            SET
                kitchen_status = 'NEW',
                prepared_by = NULL,
                served_at = NULL
            WHERE order_detail_id = ?
            """;

    public static final String UPDATE_DETAIL_STATUS_WITH_CHEF = """
            UPDATE order_details
            SET
                kitchen_status = ?,
                prepared_by = ?,
                served_at = NULL
            WHERE order_detail_id = ?
            """;

    public static final String UPDATE_DETAIL_SERVED = """
            UPDATE order_details
            SET
                kitchen_status = 'SERVED',
                prepared_by = ?,
                served_at = NOW()
            WHERE order_detail_id = ?
            """;

    public static final String UPDATE_DETAIL_CANCELLED = """
            UPDATE order_details
            SET
                kitchen_status = 'CANCELLED',
                prepared_by = ?,
                served_at = NULL
            WHERE order_detail_id = ?
            """;

    public static final String COUNT_DETAILS_BY_ORDER = """
            SELECT COUNT(*)
            FROM order_details
            WHERE order_id = ?
            """;

    public static final String COUNT_DETAILS_BY_STATUS = """
            SELECT COUNT(*)
            FROM order_details
            WHERE order_id = ?
            AND kitchen_status = ?
            """;

    public static final String FIND_KITCHEN_ORDER_DETAIL = """
            SELECT
                od.order_detail_id AS orderDetailId,
                od.order_id AS orderId,
                o.order_no AS orderNo,
                c.customer_name AS customerName,
                rt.table_number AS tableNumber,
                od.item_id AS itemId,
                m.item_name AS itemName,
                od.quantity AS quantity,
                od.price AS price,
                od.subtotal AS subtotal,
                od.kitchen_note AS kitchenNote,
                od.kitchen_status AS kitchenStatus,
                od.prepared_by AS preparedBy,
                u.full_name AS preparedByName,
                o.order_date AS orderDate,
                od.served_at AS servedAt
            FROM order_details od
            INNER JOIN orders o
                ON od.order_id = o.order_id
            LEFT JOIN customer c
                ON o.customer_id = c.customer_id
            LEFT JOIN restaurant_tables rt
                ON o.table_id = rt.table_id
            INNER JOIN menu m
                ON od.item_id = m.item_id
            LEFT JOIN users u
                ON od.prepared_by = u.user_id
            WHERE od.order_detail_id = ?
            """;
}
