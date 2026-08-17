package com.restaurant.repository.query;

/**
 * SQL Queries for Restaurant Tables Management.
 */
public final class TableQueries {

    private TableQueries() {
        // Prevent instantiation
    }

    public static final String FIND_ALL = """
            SELECT
                table_id,
                table_number,
                capacity,
                table_status
            FROM restaurant_tables
            ORDER BY table_id ASC
            """;

    public static final String FIND_BY_ID = """
            SELECT
                table_id,
                table_number,
                capacity,
                table_status
            FROM restaurant_tables
            WHERE table_id = ?
            """;

    public static final String EXISTS_BY_TABLE_NUMBER = """
            SELECT COUNT(*)
            FROM restaurant_tables
            WHERE table_number = ?
            """;

    public static final String EXISTS_BY_TABLE_NUMBER_AND_NOT_ID = """
            SELECT COUNT(*)
            FROM restaurant_tables
            WHERE table_number = ?
            AND table_id <> ?
            """;

    public static final String INSERT_TABLE = """
            INSERT INTO restaurant_tables
            (
                table_number,
                capacity,
                table_status
            )
            VALUES (?, ?, ?)
            """;

    public static final String UPDATE_TABLE = """
            UPDATE restaurant_tables
            SET
                table_number = ?,
                capacity = ?,
                table_status = ?
            WHERE table_id = ?
            """;

    public static final String DELETE_TABLE = """
            DELETE FROM restaurant_tables
            WHERE table_id = ?
            """;
}
