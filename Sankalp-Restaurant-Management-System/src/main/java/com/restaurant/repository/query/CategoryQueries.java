package com.restaurant.repository.query;

/**
 * SQL Queries for Category Management.
 */
public final class CategoryQueries {

    private CategoryQueries() {
        // Prevent instantiation
    }

    public static final String INSERT_CATEGORY = """
            INSERT INTO category
            (restaurant_id, category_name, description, status)
            VALUES (?, ?, ?, ?)
            """;

    public static final String UPDATE_CATEGORY = """
            UPDATE category
            SET
                category_name = ?,
                description = ?,
                status = ?
            WHERE category_id = ? AND restaurant_id = ?
            """;

    public static final String DELETE_BY_ID = """
            DELETE FROM category
            WHERE category_id = ? AND restaurant_id = ?
            """;

    public static final String IS_CATEGORY_USED = """
            SELECT COUNT(*)
            FROM menu
            WHERE category_id = ? AND restaurant_id = ?
            """;

    public static final String FIND_ALL = """
            SELECT
                category_id AS categoryId,
                restaurant_id AS restaurantId,
                category_name AS categoryName,
                description,
                status
            FROM category
            WHERE restaurant_id = ?
            ORDER BY category_id DESC
            """;

    public static final String FIND_BY_ID = """
            SELECT
                category_id AS categoryId,
                restaurant_id AS restaurantId,
                category_name AS categoryName,
                description,
                status
            FROM category
            WHERE category_id = ? AND restaurant_id = ?
            """;

    public static final String EXISTS_BY_NAME = """
            SELECT COUNT(*)
            FROM category
            WHERE category_name = ? AND restaurant_id = ?
            """;

    public static final String EXISTS_BY_NAME_EXCEPT_ID = """
            SELECT COUNT(*)
            FROM category
            WHERE category_name = ?
            AND category_id <> ?
            AND restaurant_id = ?
            """;

    public static final String SEARCH_CATEGORY = """
            SELECT
                category_id AS categoryId,
                restaurant_id AS restaurantId,
                category_name AS categoryName,
                description,
                status
            FROM category
            WHERE restaurant_id = ?
                AND (category_name LIKE ? OR description LIKE ?)
            ORDER BY category_name
            """;
}
