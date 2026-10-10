package com.restaurant.repository.query;

/**
 * SQL Queries for Billing & Invoicing.
 */
public final class BillQueries {

    private BillQueries() {
        // Prevent instantiation
    }

    public static final String CHECK_ORDER_EXISTS = """
            SELECT COUNT(*)
            FROM orders
            WHERE order_id = ? AND restaurant_id = ?
            """;

    public static final String CHECK_BILL_EXISTS_FOR_ORDER = """
            SELECT COUNT(*)
            FROM bill
            WHERE order_id = ? AND restaurant_id = ?
            """;

    public static final String CALCULATE_ORDER_SUBTOTAL = """
            SELECT COALESCE(SUM(subtotal), 0)
            FROM order_details
            WHERE order_id = ?
            """;

    public static final String INSERT_BILL = """
            INSERT INTO bill
            (
                restaurant_id,
                bill_no,
                order_id,
                subtotal,
                tax_amount,
                discount_amount,
                grand_total,
                payment_mode,
                payment_status
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String UPDATE_ORDER_AFTER_BILLING = """
            UPDATE orders
            SET
                total_amount = ?,
                order_status = 'BILLED'
            WHERE order_id = ? AND restaurant_id = ?
            """;

    public static final String FIND_ALL_BILLS = """
            SELECT
                bill_id AS billId,
                restaurant_id AS restaurantId,
                bill_no AS billNo,
                order_id AS orderId,
                bill_date AS billDate,
                subtotal,
                tax_amount AS gst,
                discount_amount AS discount,
                grand_total AS grandTotal,
                payment_mode AS paymentMethod,
                payment_status AS paymentStatus
            FROM bill
            WHERE restaurant_id = ?
            ORDER BY bill_id DESC
            """;

    public static final String FIND_BILLS_PAGE = """
            SELECT
                bill_id AS billId,
                restaurant_id AS restaurantId,
                bill_no AS billNo,
                order_id AS orderId,
                bill_date AS billDate,
                subtotal,
                tax_amount AS gst,
                discount_amount AS discount,
                grand_total AS grandTotal,
                payment_mode AS paymentMethod,
                payment_status AS paymentStatus
            FROM bill
            WHERE restaurant_id = ?
            ORDER BY bill_id DESC
            LIMIT ? OFFSET ?
            """;

    public static final String COUNT_BILLS = "SELECT COUNT(*) FROM bill WHERE restaurant_id = ?";

    public static final String FIND_BILL_BY_ID = """
            SELECT
                bill_id AS billId,
                restaurant_id AS restaurantId,
                bill_no AS billNo,
                order_id AS orderId,
                bill_date AS billDate,
                subtotal,
                tax_amount AS gst,
                discount_amount AS discount,
                grand_total AS grandTotal,
                payment_mode AS paymentMethod,
                payment_status AS paymentStatus
            FROM bill
            WHERE bill_id = ? AND restaurant_id = ?
            """;

    public static final String FIND_BILL_BY_ORDER_ID = """
            SELECT
                bill_id AS billId,
                restaurant_id AS restaurantId,
                bill_no AS billNo,
                order_id AS orderId,
                bill_date AS billDate,
                subtotal,
                tax_amount AS gst,
                discount_amount AS discount,
                grand_total AS grandTotal,
                payment_mode AS paymentMethod,
                payment_status AS paymentStatus
            FROM bill
            WHERE order_id = ? AND restaurant_id = ?
            """;

    public static final String UPDATE_PAYMENT = """
            UPDATE bill
            SET
                payment_mode = ?,
                payment_status = ?
            WHERE bill_id = ? AND restaurant_id = ?
            """;

    public static final String FIND_TODAY_BILLS = """
            SELECT
                bill_id AS billId,
                restaurant_id AS restaurantId,
                bill_no AS billNo,
                order_id AS orderId,
                bill_date AS billDate,
                subtotal,
                tax_amount AS gst,
                discount_amount AS discount,
                grand_total AS grandTotal,
                payment_mode AS paymentMethod,
                payment_status AS paymentStatus
            FROM bill
            WHERE DATE(bill_date) = CURDATE() AND restaurant_id = ?
            ORDER BY bill_id DESC
            """;

    public static final String GET_MAX_BILL_NUMBER = """
            SELECT COALESCE(
                MAX(
                    CAST(
                        SUBSTRING_INDEX(bill_no, '-', -1) AS UNSIGNED
                    )
                ), 0
            )
            FROM bill
            WHERE bill_no LIKE 'BILL-%'
            """;
}
