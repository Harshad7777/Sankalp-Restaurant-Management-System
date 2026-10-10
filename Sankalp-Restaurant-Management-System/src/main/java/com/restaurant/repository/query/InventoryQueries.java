package com.restaurant.repository.query;

public final class InventoryQueries {

    private InventoryQueries() {
        // Prevent instantiation
    }

    public static final String FIND_ALL =
            "SELECT inventory_id, restaurant_id, item_name, category, quantity, unit, min_threshold, " +
            "cost_per_unit, supplier_name, supplier_contact, last_restocked, status, created_at, updated_at " +
            "FROM inventory WHERE restaurant_id = ? ORDER BY item_name ASC";

    public static final String FIND_BY_ID =
            "SELECT inventory_id, restaurant_id, item_name, category, quantity, unit, min_threshold, " +
            "cost_per_unit, supplier_name, supplier_contact, last_restocked, status, created_at, updated_at " +
            "FROM inventory WHERE inventory_id = ? AND restaurant_id = ?";

    public static final String EXISTS_BY_NAME =
            "SELECT COUNT(*) FROM inventory WHERE LOWER(item_name) = LOWER(?) AND restaurant_id = ?";

    public static final String EXISTS_BY_NAME_AND_NOT_ID =
            "SELECT COUNT(*) FROM inventory WHERE LOWER(item_name) = LOWER(?) AND inventory_id != ? AND restaurant_id = ?";

    public static final String INSERT_ITEM =
            "INSERT INTO inventory (restaurant_id, item_name, category, quantity, unit, min_threshold, " +
            "cost_per_unit, supplier_name, supplier_contact, last_restocked, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_ITEM =
            "UPDATE inventory SET item_name = ?, category = ?, quantity = ?, unit = ?, " +
            "min_threshold = ?, cost_per_unit = ?, supplier_name = ?, supplier_contact = ?, status = ? " +
            "WHERE inventory_id = ? AND restaurant_id = ?";

    public static final String UPDATE_STOCK_AND_STATUS =
            "UPDATE inventory SET quantity = ?, status = ?, last_restocked = CASE WHEN ? = 'RESTOCK' THEN NOW() ELSE last_restocked END " +
            "WHERE inventory_id = ? AND restaurant_id = ?";

    public static final String DELETE_ITEM =
            "DELETE FROM inventory WHERE inventory_id = ? AND restaurant_id = ?";

    public static final String GET_DISTINCT_CATEGORIES =
            "SELECT DISTINCT category FROM inventory WHERE restaurant_id = ? AND category IS NOT NULL AND category != '' ORDER BY category ASC";

    public static final String INSERT_LOG =
            "INSERT INTO inventory_logs (inventory_id, action_type, quantity, previous_quantity, new_quantity, reason, performed_by) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String FIND_LOGS_BY_INVENTORY_ID =
            "SELECT l.log_id, l.inventory_id, i.item_name, l.action_type, l.quantity, l.previous_quantity, " +
            "l.new_quantity, l.reason, l.performed_by, l.created_at " +
            "FROM inventory_logs l " +
            "JOIN inventory i ON l.inventory_id = i.inventory_id " +
            "WHERE l.inventory_id = ? AND i.restaurant_id = ? " +
            "ORDER BY l.created_at DESC";

    public static final String COUNT_TOTAL =
            "SELECT COUNT(*) FROM inventory WHERE restaurant_id = ?";

    public static final String COUNT_BY_STATUS =
            "SELECT COUNT(*) FROM inventory WHERE status = ? AND restaurant_id = ?";

    public static final String SUM_TOTAL_VALUE =
            "SELECT COALESCE(SUM(quantity * cost_per_unit), 0.0) FROM inventory WHERE restaurant_id = ?";
}
