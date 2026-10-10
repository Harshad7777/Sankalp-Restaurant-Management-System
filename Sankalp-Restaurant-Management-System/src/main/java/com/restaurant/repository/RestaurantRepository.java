package com.restaurant.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Restaurant;

@Repository
public class RestaurantRepository {

    private final JdbcTemplate jdbcTemplate;

    public RestaurantRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Restaurant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(rs.getInt("restaurant_id"));
        restaurant.setRestaurantName(rs.getString("restaurant_name"));
        restaurant.setTagline(rs.getString("tagline"));
        restaurant.setPhone(rs.getString("phone"));
        restaurant.setEmail(rs.getString("email"));
        restaurant.setWebsite(rs.getString("website"));
        restaurant.setAddress(rs.getString("address"));
        restaurant.setCurrencySymbol(rs.getString("currency_symbol"));
        restaurant.setOpeningTime(rs.getString("opening_time"));
        restaurant.setClosingTime(rs.getString("closing_time"));
        restaurant.setGstRate(rs.getBigDecimal("gst_rate"));
        restaurant.setServiceChargeRate(rs.getBigDecimal("service_charge_rate"));
        restaurant.setGstinNumber(rs.getString("gstin_number"));
        restaurant.setFssaiNumber(rs.getString("fssai_number"));
        restaurant.setInvoicePrefix(rs.getString("invoice_prefix"));
        restaurant.setDefaultDiscount(rs.getBigDecimal("default_discount"));
        restaurant.setBillFooterNote(rs.getString("bill_footer_note"));
        restaurant.setEnableGst(rs.getBoolean("enable_gst"));
        restaurant.setPrepTime(rs.getInt("prep_time"));
        restaurant.setStockThreshold(rs.getInt("stock_threshold"));
        restaurant.setKitchenSound(rs.getBoolean("kitchen_sound"));
        restaurant.setAutoReleaseTable(rs.getBoolean("auto_release_table"));
        restaurant.setAutoAcceptOrders(rs.getBoolean("auto_accept_orders"));
        restaurant.setThemeColor(rs.getString("theme_color"));
        restaurant.setTableIndicators(rs.getBoolean("table_indicators"));
        restaurant.setDailySalesEmail(rs.getBoolean("daily_sales_email"));
        restaurant.setLowStockEmail(rs.getBoolean("low_stock_email"));
        restaurant.setLogoPath(rs.getString("logo_path"));
        restaurant.setStatus(rs.getString("status"));
        restaurant.setCreatedAt(rs.getTimestamp("created_at"));
        restaurant.setUpdatedAt(rs.getTimestamp("updated_at"));
        return restaurant;
    }

    public List<Restaurant> findAll() {
        String sql = "SELECT * FROM restaurants ORDER BY restaurant_id ASC";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    public Restaurant findById(Integer restaurantId) {
        String sql = "SELECT * FROM restaurants WHERE restaurant_id = ?";
        List<Restaurant> list = jdbcTemplate.query(sql, this::mapRow, restaurantId);
        return list.isEmpty() ? null : list.get(0);
    }

    public Restaurant findByName(String name) {
        if (name == null || name.isBlank()) return null;
        String sql = "SELECT * FROM restaurants WHERE LOWER(restaurant_name) = LOWER(?)";
        List<Restaurant> list = jdbcTemplate.query(sql, this::mapRow, name.trim());
        return list.isEmpty() ? null : list.get(0);
    }

    public int createRestaurant(String restaurantName) {
        String sql = "INSERT INTO restaurants (restaurant_name, status) VALUES (?, 'ACTIVE')";
        org.springframework.jdbc.support.KeyHolder keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            java.sql.PreparedStatement ps = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, restaurantName.trim());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        return key != null ? key.intValue() : 0;
    }
}
