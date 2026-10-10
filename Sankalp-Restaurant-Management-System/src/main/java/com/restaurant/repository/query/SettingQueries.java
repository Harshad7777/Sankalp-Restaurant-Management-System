package com.restaurant.repository.query;

/**
 * SQL Queries for Restaurant Settings Management.
 */
public final class SettingQueries {

    private SettingQueries() {
    }

    public static final String CREATE_TABLE_IF_NOT_EXISTS = """
            CREATE TABLE IF NOT EXISTS restaurant_settings (
                id INT AUTO_INCREMENT PRIMARY KEY,
                restaurant_id INT NOT NULL UNIQUE DEFAULT 1,
                restaurant_name VARCHAR(150) NOT NULL DEFAULT 'Sankalp Restaurant',
                tagline VARCHAR(255) DEFAULT 'Authentic Flavors & Fine Dining Experience',
                phone VARCHAR(50) DEFAULT '+91 98765 43210',
                email VARCHAR(100) DEFAULT 'contact@sankalprestaurant.com',
                website VARCHAR(150) DEFAULT 'https://www.sankalprestaurant.com',
                address TEXT,
                currency_symbol VARCHAR(10) DEFAULT '₹',
                opening_time VARCHAR(20) DEFAULT '10:00',
                closing_time VARCHAR(20) DEFAULT '23:00',
                gst_rate DECIMAL(5,2) DEFAULT 5.00,
                service_charge_rate DECIMAL(5,2) DEFAULT 0.00,
                gstin_number VARCHAR(50) DEFAULT '24ABCDE1234F1Z5',
                fssai_number VARCHAR(50) DEFAULT '10019021004567',
                invoice_prefix VARCHAR(20) DEFAULT 'INV-',
                default_discount DECIMAL(5,2) DEFAULT 20.00,
                bill_footer_note TEXT,
                enable_gst BOOLEAN DEFAULT TRUE,
                prep_time INT DEFAULT 20,
                stock_threshold INT DEFAULT 5,
                kitchen_sound BOOLEAN DEFAULT TRUE,
                auto_release_table BOOLEAN DEFAULT TRUE,
                auto_accept_orders BOOLEAN DEFAULT FALSE,
                theme_color VARCHAR(30) DEFAULT 'gold',
                table_indicators BOOLEAN DEFAULT TRUE,
                daily_sales_email BOOLEAN DEFAULT TRUE,
                low_stock_email BOOLEAN DEFAULT TRUE,
                logo_path VARCHAR(255) DEFAULT '/images/logo.png',
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
            )
            """;

    public static final String FIND_SETTINGS_BY_RESTAURANT = """
            SELECT
                id,
                restaurant_id,
                restaurant_name,
                tagline,
                phone,
                email,
                website,
                address,
                currency_symbol,
                opening_time,
                closing_time,
                gst_rate,
                service_charge_rate,
                gstin_number,
                fssai_number,
                invoice_prefix,
                default_discount,
                bill_footer_note,
                enable_gst,
                prep_time,
                stock_threshold,
                kitchen_sound,
                auto_release_table,
                auto_accept_orders,
                theme_color,
                table_indicators,
                daily_sales_email,
                low_stock_email,
                logo_path
            FROM restaurant_settings
            WHERE restaurant_id = ?
            """;

    public static final String INSERT_DEFAULT_FOR_RESTAURANT = """
            INSERT INTO restaurant_settings (restaurant_id, restaurant_name, tagline, phone, email, website, address, currency_symbol, opening_time, closing_time, gst_rate, service_charge_rate, gstin_number, fssai_number, invoice_prefix, default_discount, bill_footer_note, enable_gst, prep_time, stock_threshold, kitchen_sound, auto_release_table, auto_accept_orders, theme_color, table_indicators, daily_sales_email, low_stock_email, logo_path)
            VALUES (?, 'Sankalp Restaurant', 'Authentic Flavors & Fine Dining Experience', '+91 98765 43210', 'contact@sankalprestaurant.com', 'https://www.sankalprestaurant.com', 'Opp. City Mall, Main Ring Road, Ahmedabad, Gujarat 380015', 'INR', '10:00', '23:00', 5.00, 0.00, '24ABCDE1234F1Z5', '10019021004567', 'INV-', 20.00, 'Thank you for dining with Sankalp! Have a wonderful day.', TRUE, 20, 5, TRUE, TRUE, FALSE, 'gold', TRUE, TRUE, TRUE, '/images/logo.png')
            ON DUPLICATE KEY UPDATE restaurant_name = restaurant_name
            """;

    public static final String INSERT_SETTINGS_FOR_NEW_RESTAURANT = """
            INSERT INTO restaurant_settings (restaurant_id, restaurant_name, tagline, phone, email, website, address, currency_symbol, opening_time, closing_time, gst_rate, service_charge_rate, gstin_number, fssai_number, invoice_prefix, default_discount, bill_footer_note, enable_gst, prep_time, stock_threshold, kitchen_sound, auto_release_table, auto_accept_orders, theme_color, table_indicators, daily_sales_email, low_stock_email, logo_path)
            VALUES (?, ?, 'Authentic Flavors & Fine Dining Experience', '+91 98765 43210', 'contact@restaurant.com', 'https://www.restaurant.com', 'Main Ring Road', 'INR', '10:00', '23:00', 5.00, 0.00, '24ABCDE1234F1Z5', '10019021004567', 'INV-', 10.00, 'Thank you for dining with us!', TRUE, 20, 5, TRUE, TRUE, FALSE, 'gold', TRUE, TRUE, TRUE, '/images/logo.png')
            ON DUPLICATE KEY UPDATE restaurant_name = restaurant_name
            """;

    public static final String UPDATE_SETTINGS = """
            UPDATE restaurant_settings
            SET
                restaurant_name = ?,
                tagline = ?,
                phone = ?,
                email = ?,
                website = ?,
                address = ?,
                currency_symbol = ?,
                opening_time = ?,
                closing_time = ?,
                gst_rate = ?,
                service_charge_rate = ?,
                gstin_number = ?,
                fssai_number = ?,
                invoice_prefix = ?,
                default_discount = ?,
                bill_footer_note = ?,
                enable_gst = ?,
                prep_time = ?,
                stock_threshold = ?,
                kitchen_sound = ?,
                auto_release_table = ?,
                auto_accept_orders = ?,
                theme_color = ?,
                table_indicators = ?,
                daily_sales_email = ?,
                low_stock_email = ?,
                logo_path = ?
            WHERE restaurant_id = ?
            """;
}
