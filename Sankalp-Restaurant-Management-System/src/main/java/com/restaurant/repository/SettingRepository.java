package com.restaurant.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.RestaurantSetting;
import com.restaurant.repository.query.SettingQueries;
import com.restaurant.security.SecurityUtil;

@Repository
public class SettingRepository {

    private final JdbcTemplate jdbcTemplate;

    public SettingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        try {
            jdbcTemplate.execute(SettingQueries.CREATE_TABLE_IF_NOT_EXISTS);
            jdbcTemplate.update(SettingQueries.INSERT_DEFAULT_FOR_RESTAURANT);

            // Ensure Royal Fine Dining tenant (id 3) exists and migrate royal_admin users in database
            jdbcTemplate.update("INSERT INTO restaurants (restaurant_id, restaurant_name, status) VALUES (3, 'Royal Fine Dining', 'ACTIVE') ON DUPLICATE KEY UPDATE restaurant_name = 'Royal Fine Dining'");
            jdbcTemplate.update("UPDATE users SET restaurant_id = 3 WHERE username LIKE 'royal_admin%'");
            seedStarterTenantData(3, "Royal Fine Dining");
        } catch (Exception e) {
            System.err.println("Database auto-migration info: " + e.getMessage());
        }
    }

    private RestaurantSetting mapRow(ResultSet rs, int rowNum) throws SQLException {
        RestaurantSetting setting = new RestaurantSetting();
        setting.setId(rs.getInt("id"));
        try {
            setting.setRestaurantId(rs.getInt("restaurant_id"));
        } catch (SQLException ignored) {}
        setting.setRestaurantName(rs.getString("restaurant_name"));
        setting.setTagline(rs.getString("tagline"));
        setting.setPhone(rs.getString("phone"));
        setting.setEmail(rs.getString("email"));
        setting.setWebsite(rs.getString("website"));
        setting.setAddress(rs.getString("address"));
        setting.setCurrencySymbol(rs.getString("currency_symbol"));
        setting.setOpeningTime(rs.getString("opening_time"));
        setting.setClosingTime(rs.getString("closing_time"));
        setting.setGstRate(rs.getDouble("gst_rate"));
        setting.setServiceChargeRate(rs.getDouble("service_charge_rate"));
        setting.setGstinNumber(rs.getString("gstin_number"));
        setting.setFssaiNumber(rs.getString("fssai_number"));
        setting.setInvoicePrefix(rs.getString("invoice_prefix"));
        setting.setDefaultDiscount(rs.getDouble("default_discount"));
        setting.setBillFooterNote(rs.getString("bill_footer_note"));
        setting.setEnableGst(rs.getBoolean("enable_gst"));
        setting.setPrepTime(rs.getInt("prep_time"));
        setting.setStockThreshold(rs.getInt("stock_threshold"));
        setting.setKitchenSound(rs.getBoolean("kitchen_sound"));
        setting.setAutoReleaseTable(rs.getBoolean("auto_release_table"));
        setting.setAutoAcceptOrders(rs.getBoolean("auto_accept_orders"));
        setting.setThemeColor(rs.getString("theme_color"));
        setting.setTableIndicators(rs.getBoolean("table_indicators"));
        setting.setDailySalesEmail(rs.getBoolean("daily_sales_email"));
        setting.setLowStockEmail(rs.getBoolean("low_stock_email"));
        setting.setLogoPath(rs.getString("logo_path"));
        return setting;
    }

    public RestaurantSetting getSettings() {
        return getSettingsByRestaurantId(SecurityUtil.getCurrentRestaurantId());
    }

    public RestaurantSetting getSettingsByRestaurantId(Integer restaurantId) {
        if (restaurantId == null) {
            restaurantId = SecurityUtil.getCurrentRestaurantId();
        }
        List<RestaurantSetting> list = jdbcTemplate.query(
                SettingQueries.FIND_SETTINGS_BY_RESTAURANT,
                this::mapRow,
                restaurantId
        );
        if (list.isEmpty()) {
            String sqlName = "SELECT restaurant_name FROM restaurants WHERE restaurant_id = ?";
            List<String> names = jdbcTemplate.query(sqlName, (rs, rowNum) -> rs.getString("restaurant_name"), restaurantId);
            String resName = !names.isEmpty() && names.get(0) != null ? names.get(0) : "Restaurant " + restaurantId;
            initializeSettingsForRestaurant(restaurantId, resName);
            list = jdbcTemplate.query(SettingQueries.FIND_SETTINGS_BY_RESTAURANT, this::mapRow, restaurantId);
        }
        return list.isEmpty() ? new RestaurantSetting() : list.get(0);
    }

    public int updateSettings(RestaurantSetting s) {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        return jdbcTemplate.update(
                SettingQueries.UPDATE_SETTINGS,
                s.getRestaurantName(),
                s.getTagline(),
                s.getPhone(),
                s.getEmail(),
                s.getWebsite(),
                s.getAddress(),
                s.getCurrencySymbol(),
                s.getOpeningTime(),
                s.getClosingTime(),
                s.getGstRate(),
                s.getServiceChargeRate(),
                s.getGstinNumber(),
                s.getFssaiNumber(),
                s.getInvoicePrefix(),
                s.getDefaultDiscount(),
                s.getBillFooterNote(),
                s.getEnableGst(),
                s.getPrepTime(),
                s.getStockThreshold(),
                s.getKitchenSound(),
                s.getAutoReleaseTable(),
                s.getAutoAcceptOrders(),
                s.getThemeColor(),
                s.getTableIndicators(),
                s.getDailySalesEmail(),
                s.getLowStockEmail(),
                s.getLogoPath(),
                resId
        );
    }

    public int resetDefaults() {
        return jdbcTemplate.update(SettingQueries.INSERT_DEFAULT_FOR_RESTAURANT, SecurityUtil.getCurrentRestaurantId());
    }

    public int initializeSettingsForRestaurant(Integer restaurantId, String restaurantName) {
        return jdbcTemplate.update(SettingQueries.INSERT_SETTINGS_FOR_NEW_RESTAURANT, restaurantId, restaurantName);
    }

    public void seedStarterTenantData(Integer restaurantId, String restaurantName) {
        initializeSettingsForRestaurant(restaurantId, restaurantName);

        try {
            // 1. Starter Categories
            String sqlCategories = """
                    INSERT INTO category (restaurant_id, category_name, description, status)
                    VALUES 
                    (?, 'Starters', 'Appetizers and Starters', 'ACTIVE'),
                    (?, 'Main Course', 'Delicious Main Dishes', 'ACTIVE'),
                    (?, 'Beverages', 'Hot & Cold Drinks', 'ACTIVE'),
                    (?, 'Desserts', 'Sweet Treats', 'ACTIVE')
                    """;
            jdbcTemplate.update(sqlCategories, restaurantId, restaurantId, restaurantId, restaurantId);

            // 2. Starter Tables
            String sqlTables = """
                    INSERT INTO restaurant_tables (restaurant_id, table_number, capacity, status)
                    VALUES 
                    (?, 'Table 1', 2, 'AVAILABLE'),
                    (?, 'Table 2', 4, 'AVAILABLE'),
                    (?, 'Table 3', 4, 'AVAILABLE'),
                    (?, 'Table 4', 6, 'AVAILABLE'),
                    (?, 'Table 5', 8, 'AVAILABLE')
                    """;
            jdbcTemplate.update(sqlTables, restaurantId, restaurantId, restaurantId, restaurantId, restaurantId);

            // 3. Starter Menu Items
            String sqlMenu1 = "INSERT INTO menu (restaurant_id, category_id, item_name, description, price, is_available, status) " +
                    "SELECT ?, category_id, 'Paneer Butter Masala', 'Rich cottage cheese gravy', 280.00, TRUE, 'ACTIVE' " +
                    "FROM category WHERE restaurant_id = ? AND category_name = 'Main Course' LIMIT 1";
            jdbcTemplate.update(sqlMenu1, restaurantId, restaurantId);

            String sqlMenu2 = "INSERT INTO menu (restaurant_id, category_id, item_name, description, price, is_available, status) " +
                    "SELECT ?, category_id, 'Garlic Naan', 'Butter roasted garlic naan', 60.00, TRUE, 'ACTIVE' " +
                    "FROM category WHERE restaurant_id = ? AND category_name = 'Main Course' LIMIT 1";
            jdbcTemplate.update(sqlMenu2, restaurantId, restaurantId);

            String sqlMenu3 = "INSERT INTO menu (restaurant_id, category_id, item_name, description, price, is_available, status) " +
                    "SELECT ?, category_id, 'Fresh Lime Soda', 'Refreshing lime drink', 70.00, TRUE, 'ACTIVE' " +
                    "FROM category WHERE restaurant_id = ? AND category_name = 'Beverages' LIMIT 1";
            jdbcTemplate.update(sqlMenu3, restaurantId, restaurantId);

            String sqlMenu4 = "INSERT INTO menu (restaurant_id, category_id, item_name, description, price, is_available, status) " +
                    "SELECT ?, category_id, 'Gulab Jamun', 'Classic sweet dessert', 90.00, TRUE, 'ACTIVE' " +
                    "FROM category WHERE restaurant_id = ? AND category_name = 'Desserts' LIMIT 1";
            jdbcTemplate.update(sqlMenu4, restaurantId, restaurantId);

        } catch (Exception e) {
            System.err.println("Starter data seeding note: " + e.getMessage());
        }
    }
}
