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
            WHERE order_id = ?
            """;

    public static final String CHECK_BILL_EXISTS_FOR_ORDER = """
            SELECT COUNT(*)
            FROM bill
            WHERE order_id = ?
            """;

    public static final String CALCULATE_ORDER_SUBTOTAL = """
            SELECT COALESCE(SUM(subtotal), 0)
            FROM order_details
            WHERE order_id = ?
            """;

    public static final String INSERT_BILL = """
            INSERT INTO bill
            (
                bill_no,
                order_id,
                subtotal,
                gst,
                discount,
                grand_total,
                payment_method,
                payment_status
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String UPDATE_ORDER_AFTER_BILLING = """
            UPDATE orders
            SET
                total_amount = ?,
                order_status = 'BILLED'
            WHERE order_id = ?
            """;

    public static final String FIND_ALL_BILLS = """
            SELECT
                bill_id,
                bill_no,
                order_id,
                bill_date,
                subtotal,
                gst,
                discount,
                grand_total,
                payment_method,
                payment_status
            FROM bill
            ORDER BY bill_id DESC
            """;

    public static final String FIND_BILLS_PAGE = """
            SELECT
                bill_id,
                bill_no,
                order_id,
                bill_date,
                subtotal,
                gst,
                discount,
                grand_total,
                payment_method,
                payment_status
            FROM bill
            ORDER BY bill_id DESC
            LIMIT ? OFFSET ?
            """;

    public static final String COUNT_BILLS = "SELECT COUNT(*) FROM bill";

    public static final String FIND_BILL_BY_ID = """
            SELECT
                bill_id,
                bill_no,
                order_id,
                bill_date,
                subtotal,
                gst,
                discount,
                grand_total,
                payment_method,
                payment_status
            FROM bill
            WHERE bill_id = ?
            """;

    public static final String FIND_BILL_BY_ORDER_ID = """
            SELECT
                bill_id,
                bill_no,
                order_id,
                bill_date,
                subtotal,
                gst,
                discount,
                grand_total,
                payment_method,
                payment_status
            FROM bill
            WHERE order_id = ?
            """;

    public static final String UPDATE_PAYMENT = """
            UPDATE bill
            SET
                payment_method = ?,
                payment_status = ?
            WHERE bill_id = ?
            """;

    public static final String FIND_TODAY_BILLS = """
            SELECT
                bill_id,
                bill_no,
                order_id,
                bill_date,
                subtotal,
                gst,
                discount,
                grand_total,
                payment_method,
                payment_status
            FROM bill
            WHERE DATE(bill_date) = CURDATE()
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
