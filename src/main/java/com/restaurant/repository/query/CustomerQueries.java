package com.restaurant.repository.query;

/**
 * SQL Queries for Customer Management.
 */
public final class CustomerQueries {

    private CustomerQueries() {
        // Prevent instantiation
    }

    public static final String INSERT_CUSTOMER = """
            INSERT INTO customer
            (customer_name, mobile, email, address, status)
            VALUES (?, ?, ?, ?, ?)
            """;

    public static final String UPDATE_CUSTOMER = """
            UPDATE customer
            SET
                customer_name = ?,
                mobile = ?,
                email = ?,
                address = ?,
                status = ?
            WHERE customer_id = ?
            """;

    public static final String DELETE_CUSTOMER = """
            DELETE FROM customer
            WHERE customer_id = ?
            """;

    public static final String FIND_BY_ID = """
            SELECT
                customer_id AS customerId,
                customer_name AS customerName,
                mobile,
                email,
                address,
                status
            FROM customer
            WHERE customer_id = ?
            """;

    public static final String FIND_ALL = """
            SELECT
                customer_id AS customerId,
                customer_name AS customerName,
                mobile,
                email,
                address,
                status
            FROM customer
            ORDER BY customer_id DESC
            """;

    public static final String EXISTS_BY_MOBILE = """
            SELECT COUNT(*)
            FROM customer
            WHERE mobile = ?
            """;

    public static final String SEARCH_CUSTOMER = """
            SELECT
                customer_id AS customerId,
                customer_name AS customerName,
                mobile,
                email,
                address,
                status
            FROM customer
            WHERE customer_name LIKE ?
               OR mobile LIKE ?
            """;
}
