package com.restaurant.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.RestaurantSetting;
import com.restaurant.repository.query.SettingQueries;

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
            jdbcTemplate.update(SettingQueries.INSERT_DEFAULT);
        } catch (Exception e) {
            System.err.println("Failed to initialize restaurant_settings table: " + e.getMessage());
        }
    }

    private RestaurantSetting mapRow(ResultSet rs, int rowNum) throws SQLException {
        RestaurantSetting setting = new RestaurantSetting();
        setting.setId(rs.getInt("id"));
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
        List<RestaurantSetting> list = jdbcTemplate.query(
                SettingQueries.FIND_SETTINGS,
                this::mapRow
        );
        if (list.isEmpty()) {
            jdbcTemplate.update(SettingQueries.INSERT_DEFAULT);
            list = jdbcTemplate.query(SettingQueries.FIND_SETTINGS, this::mapRow);
        }
        return list.isEmpty() ? new RestaurantSetting() : list.get(0);
    }

    public int updateSettings(RestaurantSetting s) {
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
                s.getLogoPath()
        );
    }

    public int resetDefaults() {
        return jdbcTemplate.update(SettingQueries.RESET_DEFAULTS);
    }
}
