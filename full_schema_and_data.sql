-- =====================================================
-- SANKALP RESTAURANT MANAGEMENT SYSTEM (RMS)
-- PARENT-CHILD ORDERED DATABASE DUMP (MULTI-TENANT SCHEMA + SEED DATA)
-- =====================================================

SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS smprowor_RMS;
USE smprowor_RMS;

-- -----------------------------------------------------
-- PARENT TABLE: RESTAURANTS (TENANTS)
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS restaurants (
    restaurant_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_name VARCHAR(150) NOT NULL,
    tagline VARCHAR(255) DEFAULT 'Authentic Flavors & Fine Dining Experience',
    phone VARCHAR(50) DEFAULT '+91 98765 43210',
    email VARCHAR(100) DEFAULT 'contact@restaurant.com',
    website VARCHAR(150) DEFAULT 'https://www.restaurant.com',
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
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- -----------------------------------------------------
-- PARENT TABLES (No Foreign Keys except restaurant_id)
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL DEFAULT 1,
    full_name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    mobile VARCHAR(20),
    email VARCHAR(100),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL DEFAULT 1,
    category_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_category_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS restaurant_tables (
    table_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL DEFAULT 1,
    table_number VARCHAR(50) NOT NULL,
    capacity INT NOT NULL DEFAULT 4,
    table_status VARCHAR(20) DEFAULT 'AVAILABLE',
    x_pos INT DEFAULT 50,
    y_pos INT DEFAULT 50,
    shape VARCHAR(20) DEFAULT 'RECTANGLE',
    width INT DEFAULT 100,
    height INT DEFAULT 100,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tables_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL DEFAULT 1,
    customer_name VARCHAR(100) NOT NULL,
    mobile VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    address VARCHAR(255),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_customer_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS inventory (
    inventory_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL DEFAULT 1,
    item_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    quantity DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    unit VARCHAR(20) NOT NULL,
    min_threshold DECIMAL(10,2) NOT NULL DEFAULT 5.00,
    cost_per_unit DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    supplier_name VARCHAR(100),
    supplier_contact VARCHAR(50),
    last_restocked DATETIME,
    status VARCHAR(20) DEFAULT 'IN_STOCK',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_inventory_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE
);

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
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_settings_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS menu (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL DEFAULT 1,
    category_id INT NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    image VARCHAR(255),
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    external_sku VARCHAR(100) NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_menu_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE,
    CONSTRAINT fk_menu_category FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS inventory_logs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    inventory_id INT NOT NULL,
    action_type VARCHAR(20) NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    previous_quantity DECIMAL(10,2) NOT NULL,
    new_quantity DECIMAL(10,2) NOT NULL,
    reason VARCHAR(255),
    performed_by VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_inv_log FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS table_reservation (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL DEFAULT 1,
    reservation_code VARCHAR(50) NOT NULL UNIQUE,
    guest_name VARCHAR(100) NOT NULL,
    guest_phone VARCHAR(20) NOT NULL,
    guest_email VARCHAR(100) NULL,
    booking_date DATE NOT NULL,
    booking_time VARCHAR(20) NOT NULL,
    guest_count INT NOT NULL DEFAULT 2,
    special_request TEXT,
    table_id INT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_reservation_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE,
    CONSTRAINT fk_reservation_table FOREIGN KEY (table_id) REFERENCES restaurant_tables(table_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL DEFAULT 1,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    customer_id INT NOT NULL,
    table_id INT NOT NULL,
    staff_id INT NOT NULL,
    order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    order_status VARCHAR(30) NOT NULL DEFAULT 'NEW',
    order_type VARCHAR(30) DEFAULT 'DINE_IN',
    aggregator_channel VARCHAR(50) DEFAULT 'DIRECT',
    external_order_ref VARCHAR(100) NULL,
    remarks VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE,
    CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    CONSTRAINT fk_orders_table FOREIGN KEY (table_id) REFERENCES restaurant_tables(table_id),
    CONSTRAINT fk_orders_staff FOREIGN KEY (staff_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS order_details (
    order_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    item_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    subtotal DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    kitchen_note VARCHAR(255),
    kitchen_status VARCHAR(30) NOT NULL DEFAULT 'NEW',
    is_extra TINYINT(1) DEFAULT 0,
    prepared_by INT NULL,
    served_at DATETIME NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_details_order FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    CONSTRAINT fk_details_item FOREIGN KEY (item_id) REFERENCES menu(item_id),
    CONSTRAINT fk_details_chef FOREIGN KEY (prepared_by) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS bill (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL DEFAULT 1,
    bill_no VARCHAR(50) NOT NULL UNIQUE,
    order_id INT NOT NULL UNIQUE,
    bill_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    subtotal DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    tax_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    discount_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    grand_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    payment_mode VARCHAR(30) NOT NULL DEFAULT 'CASH',
    payment_status VARCHAR(30) NOT NULL DEFAULT 'UNPAID',
    transaction_id VARCHAR(100) NULL,
    payment_gateway VARCHAR(50) NULL,
    qr_code_url TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bill_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE,
    CONSTRAINT fk_bill_order FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS recipes (
    recipe_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL DEFAULT 1,
    item_id INT NOT NULL,
    inventory_id INT NOT NULL,
    quantity_required DECIMAL(10,3) NOT NULL,
    unit VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_recipe_menu FOREIGN KEY (item_id) REFERENCES menu(item_id) ON DELETE CASCADE,
    CONSTRAINT fk_recipe_inventory FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS item_modifiers (
    modifier_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL DEFAULT 1,
    item_id INT NOT NULL,
    modifier_name VARCHAR(100) NOT NULL,
    is_required BOOLEAN DEFAULT FALSE,
    max_selection INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_modifier_menu FOREIGN KEY (item_id) REFERENCES menu(item_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS modifier_options (
    option_id INT AUTO_INCREMENT PRIMARY KEY,
    modifier_id INT NOT NULL,
    option_name VARCHAR(100) NOT NULL,
    extra_price DECIMAL(10,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_option_modifier FOREIGN KEY (modifier_id) REFERENCES item_modifiers(modifier_id) ON DELETE CASCADE
);

-- Seed Data
INSERT IGNORE INTO restaurants (restaurant_id, restaurant_name, tagline, phone, email, website, address, currency_symbol, logo_path, status)
VALUES
(1, 'Sankalp Restaurant', 'Authentic South Indian & Fine Dining', '+91 98765 43210', 'sankalp@restaurant.com', 'https://www.sankalprestaurant.com', 'MG Road, Bangalore', '₹', '/images/sankalp_logo.jpg', 'ACTIVE'),
(2, 'ABC Restaurant', 'Flavors of North & International Cuisine', '+91 98765 99999', 'abc@restaurant.com', 'https://www.abcrestaurant.com', 'Connaught Place, New Delhi', '₹', '/images/logo.png', 'ACTIVE');

INSERT IGNORE INTO restaurant_settings (id, restaurant_id, restaurant_name, tagline, phone, email, website, address, currency_symbol, logo_path)
VALUES
(1, 1, 'Sankalp Restaurant', 'Authentic South Indian & Fine Dining', '+91 98765 43210', 'sankalp@restaurant.com', 'https://www.sankalprestaurant.com', 'MG Road, Bangalore', '₹', '/images/sankalp_logo.jpg'),
(2, 2, 'ABC Restaurant', 'Flavors of North & International Cuisine', '+91 98765 99999', 'abc@restaurant.com', 'https://www.abcrestaurant.com', 'Connaught Place, New Delhi', '₹', '/images/logo.png');

INSERT IGNORE INTO users (user_id, restaurant_id, full_name, username, password, role, mobile, email, status)
VALUES 
(1, 1, 'Sankalp Admin', 'admin', '$2a$10$Qu2vNCPJSXP7HUZrVkiUweOH5/tW4mMpNmKzHd.Pg/pAAuS3hwv.S', 'ADMIN', '9876543210', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(2, 1, 'Demo Admin Sankalp', 'adminUser', '$2a$10$Qu2vNCPJSXP7HUZrVkiUweOH5/tW4mMpNmKzHd.Pg/pAAuS3hwv.S', 'ADMIN', '9876500006', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(3, 1, 'Head Chef Vikas', 'chef', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9876543211', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(4, 1, 'Head Chef Rahul', 'chef1', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9876543211', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(5, 1, 'Staff Waiter Rohan', 'staff', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9876543212', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(6, 1, 'Staff Waiter Amit', 'staff1', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9876543212', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(7, 2, 'ABC Admin', 'admin2', '$2a$10$Qu2vNCPJSXP7HUZrVkiUweOH5/tW4mMpNmKzHd.Pg/pAAuS3hwv.S', 'ADMIN', '9898989801', 'abc.admin@restaurant.com', 'ACTIVE'),
(8, 2, 'ABC Head Chef Sanjeev', 'chef2', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9898989802', 'abc.chef@restaurant.com', 'ACTIVE'),
(9, 2, 'ABC Staff Waiter Kabir', 'staff2', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9898989803', 'abc.staff@restaurant.com', 'ACTIVE');

-- -----------------------------------------------------
-- INDEXES FOR FAST SEARCHING & QUERY OPTIMIZATION
-- -----------------------------------------------------

-- Users: Fast login lookup & filtering by tenant/role
CREATE INDEX idx_users_tenant_role ON users(restaurant_id, role, status);
CREATE INDEX idx_users_mobile ON users(mobile);

-- Customer: Fast search by phone number or name per tenant
CREATE INDEX idx_customer_tenant_mobile ON customer(restaurant_id, mobile);
CREATE INDEX idx_customer_tenant_name ON customer(restaurant_id, customer_name);

-- Category & Menu: Fast menu catalog retrieval & search by category/item name
CREATE INDEX idx_category_tenant_status ON category(restaurant_id, status);
CREATE INDEX idx_menu_tenant_cat_status ON menu(restaurant_id, category_id, status);
CREATE INDEX idx_menu_item_name ON menu(restaurant_id, item_name);

-- Tables: Fast table availability check per tenant
CREATE INDEX idx_tables_tenant_status ON restaurant_tables(restaurant_id, table_status);

-- Inventory: Fast stock lookup & threshold check per tenant
CREATE INDEX idx_inventory_tenant_status ON inventory(restaurant_id, status);
CREATE INDEX idx_inventory_item_name ON inventory(restaurant_id, item_name);
CREATE INDEX idx_inventory_logs_item ON inventory_logs(inventory_id, created_at);

-- Reservations: Fast lookup by date, code, and status per tenant
CREATE INDEX idx_reservation_tenant_date ON table_reservation(restaurant_id, booking_date, status);
CREATE INDEX idx_reservation_phone ON table_reservation(guest_phone);

-- Orders: Fast filtering by date range, order status, customer, and table
CREATE INDEX idx_orders_tenant_status_date ON orders(restaurant_id, order_status, order_date);
CREATE INDEX idx_orders_customer ON orders(restaurant_id, customer_id);
CREATE INDEX idx_orders_table ON orders(restaurant_id, table_id);
CREATE INDEX idx_orders_staff ON orders(restaurant_id, staff_id);

-- Order Details: Fast kitchen order tracking & analytics item join
CREATE INDEX idx_order_details_order_kitchen ON order_details(order_id, kitchen_status);
CREATE INDEX idx_order_details_item ON order_details(item_id);

-- Bill: Fast billing lookup, date range reporting & payment status filter
CREATE INDEX idx_bill_tenant_payment_date ON bill(restaurant_id, payment_status, bill_date);

SET FOREIGN_KEY_CHECKS = 1;

