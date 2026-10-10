package com.restaurant.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.restaurant.model.OrderDetails;
import com.restaurant.model.Recipe;

@Service
public class RecipeService {

    private final JdbcTemplate jdbcTemplate;

    public RecipeService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveRecipe(Recipe recipe) {
        String sql = "INSERT INTO recipes (restaurant_id, item_id, inventory_id, quantity_required, unit) VALUES (1, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, recipe.getItemId(), recipe.getInventoryId(), recipe.getQuantityRequired(), recipe.getUnit());
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    public List<Recipe> getRecipesByItemId(int itemId) {
        String sql = "SELECT r.*, m.item_name, i.item_name as inventory_item_name " +
                     "FROM recipes r " +
                     "JOIN menu m ON r.item_id = m.item_id " +
                     "JOIN inventory i ON r.inventory_id = i.inventory_id " +
                     "WHERE r.item_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Recipe r = new Recipe();
            r.setRecipeId(rs.getInt("recipe_id"));
            r.setRestaurantId(rs.getInt("restaurant_id"));
            r.setItemId(rs.getInt("item_id"));
            r.setItemName(rs.getString("item_name"));
            r.setInventoryId(rs.getInt("inventory_id"));
            r.setInventoryItemName(rs.getString("inventory_item_name"));
            r.setQuantityRequired(rs.getBigDecimal("quantity_required"));
            r.setUnit(rs.getString("unit"));
            return r;
        }, itemId);
    }

    public boolean deleteRecipe(int recipeId) {
        return jdbcTemplate.update("DELETE FROM recipes WHERE recipe_id = ?", recipeId) > 0;
    }

    public void deductStockForOrderItems(List<OrderDetails> items, String performedBy) {
        if (items == null || items.isEmpty()) return;

        for (OrderDetails item : items) {
            if (item.getItemId() == null) continue;
            int orderQty = item.getQuantity() != null ? item.getQuantity() : 1;

            List<Recipe> ingredients = getRecipesByItemId(item.getItemId());
            for (Recipe ing : ingredients) {
                BigDecimal totalDeduct = ing.getQuantityRequired().multiply(new BigDecimal(orderQty));

                // Fetch current inventory item details
                String fetchSql = "SELECT quantity, min_threshold FROM inventory WHERE inventory_id = ?";
                jdbcTemplate.query(fetchSql, rs -> {
                    BigDecimal prevQty = rs.getBigDecimal("quantity");
                    BigDecimal minThreshold = rs.getBigDecimal("min_threshold");
                    BigDecimal newQty = prevQty.subtract(totalDeduct);
                    if (newQty.compareTo(BigDecimal.ZERO) < 0) {
                        newQty = BigDecimal.ZERO;
                    }

                    String status = newQty.compareTo(minThreshold) <= 0 ? "LOW_STOCK" : "IN_STOCK";
                    if (newQty.compareTo(BigDecimal.ZERO) == 0) status = "OUT_OF_STOCK";

                    // Update Inventory
                    jdbcTemplate.update("UPDATE inventory SET quantity = ?, status = ?, updated_at = NOW() WHERE inventory_id = ?",
                            newQty, status, ing.getInventoryId());

                    // Audit Log Entry
                    jdbcTemplate.update("INSERT INTO inventory_logs (inventory_id, action_type, quantity, previous_quantity, new_quantity, reason, performed_by) VALUES (?, 'AUTO_RECIPE_DEDUCTION', ?, ?, ?, ?, ?)",
                            ing.getInventoryId(), totalDeduct, prevQty, newQty, "Automatic Recipe Deduction for Order", performedBy != null ? performedBy : "SYSTEM");
                }, ing.getInventoryId());
            }
        }
    }
}
