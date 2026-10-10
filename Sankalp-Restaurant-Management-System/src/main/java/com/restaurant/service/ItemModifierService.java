package com.restaurant.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.restaurant.model.ItemModifier;
import com.restaurant.model.ModifierOption;

@Service
public class ItemModifierService {

    private final JdbcTemplate jdbcTemplate;

    public ItemModifierService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ItemModifier> getModifiersByItemId(int itemId) {
        String sql = "SELECT * FROM item_modifiers WHERE item_id = ?";
        List<ItemModifier> modifiers = jdbcTemplate.query(sql, (rs, rowNum) -> {
            ItemModifier m = new ItemModifier();
            m.setModifierId(rs.getInt("modifier_id"));
            m.setRestaurantId(rs.getInt("restaurant_id"));
            m.setItemId(rs.getInt("item_id"));
            m.setName(rs.getString("name"));
            m.setIsRequired(rs.getBoolean("is_required"));
            m.setMinSelection(rs.getInt("min_selection"));
            m.setMaxSelection(rs.getInt("max_selection"));
            return m;
        }, itemId);

        for (ItemModifier m : modifiers) {
            String optSql = "SELECT * FROM modifier_options WHERE modifier_id = ?";
            List<ModifierOption> options = jdbcTemplate.query(optSql, (rs, rowNum) -> {
                ModifierOption o = new ModifierOption();
                o.setOptionId(rs.getInt("option_id"));
                o.setModifierId(rs.getInt("modifier_id"));
                o.setName(rs.getString("name"));
                o.setPrice(rs.getBigDecimal("price"));
                o.setIsAvailable(rs.getBoolean("is_available"));
                return o;
            }, m.getModifierId());
            m.setOptions(options);
        }

        return modifiers;
    }

    public int saveModifier(ItemModifier modifier) {
        String sql = "INSERT INTO item_modifiers (restaurant_id, item_id, name, is_required, min_selection, max_selection) VALUES (1, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, modifier.getItemId(), modifier.getName(), 
                modifier.getIsRequired() != null && modifier.getIsRequired(),
                modifier.getMinSelection() != null ? modifier.getMinSelection() : 0,
                modifier.getMaxSelection() != null ? modifier.getMaxSelection() : 1);
        int modifierId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        if (modifier.getOptions() != null) {
            for (ModifierOption o : modifier.getOptions()) {
                saveOption(modifierId, o);
            }
        }
        return modifierId;
    }

    public int saveOption(int modifierId, ModifierOption option) {
        String sql = "INSERT INTO modifier_options (modifier_id, name, price, is_available) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, modifierId, option.getName(), 
                option.getPrice() != null ? option.getPrice() : BigDecimal.ZERO,
                option.getIsAvailable() == null || option.getIsAvailable());
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    public boolean deleteModifier(int modifierId) {
        jdbcTemplate.update("DELETE FROM modifier_options WHERE modifier_id = ?", modifierId);
        return jdbcTemplate.update("DELETE FROM item_modifiers WHERE modifier_id = ?", modifierId) > 0;
    }
}
