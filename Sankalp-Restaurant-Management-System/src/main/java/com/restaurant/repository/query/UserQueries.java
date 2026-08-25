package com.restaurant.repository.query;

/**
 * SQL Queries for User Management.
 */
public final class UserQueries {

    private UserQueries() {
        // Prevent instantiation
    }

    public static final String FIND_BY_USERNAME = """
            SELECT
                user_id AS userId,
                full_name AS fullName,
                username,
                password,
                role,
                mobile,
                email,
                status
            FROM users
            WHERE username = ?
            """;

    public static final String EXISTS_BY_USERNAME = """
            SELECT COUNT(*)
            FROM users
            WHERE username = ?
            """;

    public static final String INSERT_USER = """
            INSERT INTO users
            (
                full_name,
                username,
                password,
                role,
                mobile,
                email,
                status
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String FIND_ALL = """
            SELECT
                user_id AS userId,
                full_name AS fullName,
                username,
                password,
                role,
                mobile,
                email,
                status
            FROM users
            ORDER BY user_id DESC
            """;

    public static final String FIND_TEAM_USERS = """
            SELECT
                user_id AS userId,
                full_name AS fullName,
                username,
                password,
                role,
                mobile,
                email,
                status
            FROM users
            WHERE role IN ('STAFF', 'CHEF')
            ORDER BY user_id DESC
            """;

    public static final String FIND_BY_ID = """
            SELECT
                user_id AS userId,
                full_name AS fullName,
                username,
                password,
                role,
                mobile,
                email,
                status
            FROM users
            WHERE user_id = ?
            """;

    public static final String UPDATE_USER = """
            UPDATE users
            SET
                full_name = ?,
                username = ?,
                password = ?,
                role = ?,
                mobile = ?,
                email = ?,
                status = ?
            WHERE user_id = ?
            """;

    public static final String UPDATE_WITHOUT_PASSWORD = """
            UPDATE users
            SET
                full_name = ?,
                username = ?,
                role = ?,
                mobile = ?,
                email = ?,
                status = ?
            WHERE user_id = ?
            """;

    public static final String UPDATE_PASSWORD = """
            UPDATE users
            SET password = ?
            WHERE user_id = ?
            """;

    public static final String DELETE_USER = """
            DELETE FROM users
            WHERE user_id = ?
            """;
}
