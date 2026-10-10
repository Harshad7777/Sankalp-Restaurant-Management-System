package com.restaurant.repository.query;

/**
 * SQL Queries for Menu Management.
 */
public final class MenuQueries {

    private MenuQueries() {
        // Prevent instantiation
    }

    public static final String INSERT_MENU = """
            INSERT INTO menu
            (restaurant_id, category_id, item_name, description, price, image, status)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String UPDATE_MENU = """
            UPDATE menu
            SET
                category_id = ?,
                item_name = ?,
                description = ?,
                price = ?,
                image = ?,
                status = ?
            WHERE item_id = ? AND restaurant_id = ?
            """;

    public static final String GET_OLD_IMAGE = """
            SELECT image
            FROM menu
            WHERE item_id = ? AND restaurant_id = ?
            """;

    public static final String DELETE_BY_ID = """
            DELETE FROM menu
            WHERE item_id = ? AND restaurant_id = ?
            """;

    public static final String FIND_BY_ID = """
            SELECT
                m.item_id AS itemId,
                m.restaurant_id AS restaurantId,
                m.category_id AS categoryId,
                c.category_name AS categoryName,
                m.item_name AS itemName,
                m.description,
                m.price,
                m.image,
                m.status
            FROM menu m
            INNER JOIN category c
                ON m.category_id = c.category_id
            WHERE m.item_id = ? AND m.restaurant_id = ?
            """;

    public static final String FIND_ALL = """
            SELECT
                m.item_id AS itemId,
                m.restaurant_id AS restaurantId,
                m.category_id AS categoryId,
                c.category_name AS categoryName,
                m.item_name AS itemName,
                m.description,
                m.price,
                m.image,
                m.status
            FROM menu m
            INNER JOIN category c
                ON m.category_id = c.category_id
            WHERE m.restaurant_id = ?
            ORDER BY m.item_id DESC
            """;

    public static final String FIND_BY_CATEGORY = """
            SELECT
                m.item_id AS itemId,
                m.restaurant_id AS restaurantId,
                m.category_id AS categoryId,
                c.category_name AS categoryName,
                m.item_name AS itemName,
                m.description,
                m.price,
                m.image,
                m.status
            FROM menu m
            INNER JOIN category c
                ON m.category_id = c.category_id
            WHERE m.category_id = ? AND m.restaurant_id = ?
            """;

    public static final String EXISTS_BY_NAME = """
            SELECT COUNT(*)
            FROM menu
            WHERE item_name = ? AND restaurant_id = ?
            """;
}
