-- =====================================================
-- SANKALP RESTAURANT MANAGEMENT SYSTEM (RMS)
-- PHPMYADMIN / HOSTINGER IMPORT SCRIPT (UTF8MB4 FIX)
-- =====================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- =====================================================
-- SECTION 1: CREATE PARENT TABLES (No Foreign Keys)
-- =====================================================

-- 1. PARENT TABLE: USERS
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    mobile VARCHAR(20),
    email VARCHAR(100),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. PARENT TABLE: CATEGORY
CREATE TABLE IF NOT EXISTS category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. PARENT TABLE: RESTAURANT_TABLES
CREATE TABLE IF NOT EXISTS restaurant_tables (
    table_id INT AUTO_INCREMENT PRIMARY KEY,
    table_number VARCHAR(50) NOT NULL UNIQUE,
    capacity INT NOT NULL DEFAULT 4,
    table_status VARCHAR(20) DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. PARENT TABLE: CUSTOMER
CREATE TABLE IF NOT EXISTS customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    mobile VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    address VARCHAR(255),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. PARENT TABLE: INVENTORY
CREATE TABLE IF NOT EXISTS inventory (
    inventory_id INT AUTO_INCREMENT PRIMARY KEY,
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
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 6. PARENT TABLE: RESTAURANT_SETTINGS
CREATE TABLE IF NOT EXISTS restaurant_settings (
    id INT PRIMARY KEY DEFAULT 1,
    restaurant_name VARCHAR(150) NOT NULL DEFAULT 'Sankalp Restaurant',
    tagline VARCHAR(255) DEFAULT 'Authentic Flavors & Fine Dining Experience',
    phone VARCHAR(50) DEFAULT '+91 98765 43210',
    email VARCHAR(100) DEFAULT 'contact@sankalprestaurant.com',
    website VARCHAR(150) DEFAULT 'https://www.sankalprestaurant.com',
    address TEXT,
    currency_symbol VARCHAR(10) CHARACTER SET utf8mb4 DEFAULT '₹',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- =====================================================
-- SECTION 2: CREATE LEVEL 1 CHILD TABLES
-- =====================================================

-- 7. CHILD TABLE: MENU (Depends on category)
CREATE TABLE IF NOT EXISTS menu (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    image VARCHAR(255),
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_menu_category FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 8. CHILD TABLE: INVENTORY_LOGS (Depends on inventory)
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 9. CHILD TABLE: TABLE_RESERVATION (Depends on restaurant_tables)
CREATE TABLE IF NOT EXISTS table_reservation (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
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
    CONSTRAINT fk_reservation_table FOREIGN KEY (table_id) REFERENCES restaurant_tables(table_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 10. CHILD TABLE: ORDERS (Depends on customer, restaurant_tables, users)
CREATE TABLE IF NOT EXISTS orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    customer_id INT NOT NULL,
    table_id INT NOT NULL,
    staff_id INT NOT NULL,
    order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    order_status VARCHAR(30) NOT NULL DEFAULT 'NEW',
    order_type VARCHAR(30) DEFAULT 'DINE_IN',
    remarks VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    CONSTRAINT fk_orders_table FOREIGN KEY (table_id) REFERENCES restaurant_tables(table_id),
    CONSTRAINT fk_orders_staff FOREIGN KEY (staff_id) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- =====================================================
-- SECTION 3: CREATE LEVEL 2 CHILD TABLES
-- =====================================================

-- 11. CHILD TABLE: ORDER_DETAILS (Depends on orders, menu, users)
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 12. CHILD TABLE: BILL (Depends on orders)
CREATE TABLE IF NOT EXISTS bill (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    bill_no VARCHAR(50) NOT NULL UNIQUE,
    order_id INT NOT NULL UNIQUE,
    bill_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    subtotal DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    gst DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    discount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    grand_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    payment_method VARCHAR(30) NOT NULL DEFAULT 'CASH',
    payment_status VARCHAR(30) NOT NULL DEFAULT 'PAID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bill_order FOREIGN KEY (order_id) REFERENCES orders(order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- =====================================================
-- SECTION 4: INSERT PARENT TABLE SEED DATA FIRST
-- =====================================================

-- 1. Insert Users (Parent)
INSERT IGNORE INTO users (user_id, full_name, username, password, role, mobile, email, status)
VALUES 
(1, 'System Administrator', 'admin', '$2a$10$Qu2vNCPJSXP7HUZrVkiUweOH5/tW4mMpNmKzHd.Pg/pAAuS3hwv.S', 'ADMIN', '9876543210', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(2, 'Demo Admin', 'adminUser', '$2a$10$Qu2vNCPJSXP7HUZrVkiUweOH5/tW4mMpNmKzHd.Pg/pAAuS3hwv.S', 'ADMIN', '9876500006', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(3, 'Head Chef Vikas', 'chef', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9876543211', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(4, 'Head Chef Rahul', 'chef1', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9876543211', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(5, 'Demo Chef', 'chefUser', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9876500004', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(6, 'Staff Waiter Rohan', 'staff', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9876543212', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(7, 'Staff Waiter Rohan', 'staff1', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9876543212', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(8, 'Demo Staff', 'staffUser', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9876500005', 'harshadrakshe28@gmail.com', 'ACTIVE');

-- 2. Insert Categories (Parent)
INSERT IGNORE INTO category (category_id, category_name, description, status)
VALUES 
(1, 'Starters', 'Delicious appetizers and snacks', 'ACTIVE'),
(2, 'South Indian', 'Traditional dosas, idlis and vadas', 'ACTIVE'),
(3, 'Main Course', 'Rich curries, paneer, and dal specials', 'ACTIVE'),
(4, 'Breads & Rice', 'Tandoori rotis, naans, and aromatic biryanis', 'ACTIVE'),
(5, 'Desserts', 'Sweet treats and traditional desserts', 'ACTIVE'),
(6, 'Beverages', 'Refreshing mocktails, lassi, and coffee', 'ACTIVE');

-- 3. Insert Restaurant Tables (Parent)
INSERT IGNORE INTO restaurant_tables (table_id, table_number, capacity, table_status)
VALUES 
(1, 'T-01', 2, 'AVAILABLE'),
(2, 'T-02', 4, 'AVAILABLE'),
(3, 'T-03', 4, 'AVAILABLE'),
(4, 'T-04', 6, 'AVAILABLE'),
(5, 'T-05', 8, 'AVAILABLE'),
(6, 'T-06', 4, 'AVAILABLE');

-- 4. Insert Customers (Parent)
INSERT IGNORE INTO customer (customer_id, customer_name, mobile, email, address, status)
VALUES 
(1, 'Walk-in Customer', '9999999999', 'walkin@restaurant.com', 'Dine In', 'ACTIVE'),
(2, 'Amitabh Sharma', '9820012345', 'amitabh@gmail.com', 'MG Road, Bangalore', 'ACTIVE'),
(3, 'Neha Verma', '9830067890', 'neha.v@yahoo.com', 'Indiranagar, Bangalore', 'ACTIVE');

-- 5. Insert Inventory Stock (Parent)
INSERT IGNORE INTO inventory (inventory_id, item_name, category, quantity, unit, min_threshold, cost_per_unit, supplier_name, supplier_contact, status)
VALUES 
(1, 'Fresh Paneer (Malai)', 'Dairy', 35.00, 'kg', 10.00, 320.00, 'Amul Dairy Supplies', '9876500001', 'IN_STOCK'),
(2, 'Basmati Rice (Aged 2 Yr)', 'Grains', 50.00, 'kg', 15.00, 110.00, 'Kohinoor Grain Traders', '9876500002', 'IN_STOCK'),
(3, 'Amul Butter (Salted)', 'Dairy', 18.00, 'kg', 5.00, 480.00, 'Amul Dairy Supplies', '9876500001', 'IN_STOCK'),
(4, 'Black Urad Lentils', 'Grains', 25.00, 'kg', 8.00, 140.00, 'Bharat Agro Foods', '9876500003', 'IN_STOCK'),
(5, 'Pure Desi Ghee', 'Dairy', 12.00, 'liters', 4.00, 650.00, 'Govind Dairy', '9876500004', 'IN_STOCK'),
(6, 'Refined Sunflower Oil', 'Cooking Oil', 40.00, 'liters', 15.00, 135.00, 'Fortune Distributors', '9876500005', 'IN_STOCK'),
(7, 'Fresh Tomatoes', 'Vegetables', 4.50, 'kg', 10.00, 35.00, 'Green Valley Organic Farms', '9876500006', 'LOW_STOCK'),
(8, 'Kashmiri Saffron (Grade A)', 'Spices', 0.25, 'kg', 0.10, 180000.00, 'Pampore Royal Spices', '9876500007', 'IN_STOCK'),
(9, 'South Indian Coffee Beans', 'Beverages', 2.00, 'kg', 5.00, 550.00, 'Coorg Plantation Direct', '9876500008', 'LOW_STOCK'),
(10, 'Full Cream Milk', 'Dairy', 0.00, 'liters', 20.00, 68.00, 'Local Dairy Federation', '9876500009', 'OUT_OF_STOCK');

-- 6. Insert Restaurant Settings (Parent)
INSERT INTO restaurant_settings (id, restaurant_name, tagline, phone, email, website, address, currency_symbol, opening_time, closing_time, gst_rate, service_charge_rate, gstin_number, fssai_number, invoice_prefix, default_discount, bill_footer_note, enable_gst, prep_time, stock_threshold, kitchen_sound, auto_release_table, auto_accept_orders, theme_color, table_indicators, daily_sales_email, low_stock_email, logo_path)
VALUES (1, 'Sankalp Restaurant', 'Authentic Flavors & Fine Dining Experience', '+91 98765 43210', 'harshadrakshe28@gmail.com', 'https://www.sankalprestaurant.com', 'Opp. City Mall, Main Ring Road, Ahmedabad, Gujarat 380015', '₹', '10:00', '23:00', 5.00, 0.00, '24ABCDE1234F1Z5', '10019021004567', 'INV-', 20.00, 'Thank you for dining with Sankalp! Have a wonderful day.', TRUE, 20, 5, TRUE, TRUE, FALSE, 'gold', TRUE, TRUE, TRUE, '/images/logo.png')
ON DUPLICATE KEY UPDATE id = 1;


-- =====================================================
-- SECTION 5: INSERT CHILD TABLE SEED DATA NEXT
-- =====================================================

-- 7. Insert Menu Items (Child of category)
INSERT IGNORE INTO menu (item_id, category_id, item_name, description, price, image, status)
VALUES
(1, 1, 'Paneer Tikka', 'Marinated cottage cheese cubes grilled with spices and peppers', 280.00, 'paneer_tikka.jpg', 'AVAILABLE'),
(2, 1, 'Veg Spring Rolls', 'Crispy rolls filled with seasoned shredded vegetables', 190.00, 'spring_rolls.jpg', 'AVAILABLE'),
(3, 2, 'Special Masala Dosa', 'Crispy golden crepe filled with spiced potato masala, served with sambar and chutneys', 160.00, 'masala_dosa.jpg', 'AVAILABLE'),
(4, 2, 'Mysore Masala Dosa', 'Signature crispy dosa layered with spicy red chutney and potato filling', 190.00, 'mysore_dosa.jpg', 'AVAILABLE'),
(5, 3, 'Paneer Butter Masala', 'Succulent paneer cubes cooked in a rich, creamy tomato and butter gravy', 320.00, 'paneer_butter_masala.jpg', 'AVAILABLE'),
(6, 3, 'Dal Makhani', 'Slow-cooked black lentils with butter and cream', 260.00, 'dal_makhani.jpg', 'AVAILABLE'),
(7, 4, 'Butter Naan', 'Soft and fluffy leavened tandoor bread brushed with melted butter', 55.00, 'butter_naan.jpg', 'AVAILABLE'),
(8, 4, 'Jeera Rice', 'Fragrant basmati rice tempered with roasted cumin seeds and ghee', 150.00, 'jeera_rice.jpg', 'AVAILABLE'),
(9, 5, 'Gulab Jamun (2 Pcs)', 'Soft milk dumplings soaked in aromatic rose-cardamom sugar syrup', 90.00, 'gulab_jamun.jpg', 'AVAILABLE'),
(10, 6, 'Special Filter Coffee', 'Authentic South Indian hot brew filter coffee with frothy milk', 70.00, 'filter_coffee.jpg', 'AVAILABLE');

-- 8. Insert Table Reservations (Child of restaurant_tables)
INSERT IGNORE INTO table_reservation (reservation_id, reservation_code, guest_name, guest_phone, guest_email, booking_date, booking_time, guest_count, special_request, table_id, status)
VALUES
(1, 'RES-20260825-1001', 'Rajesh Patel', '9898012345', 'harshadrakshe28@gmail.com', '2026-08-25', '07:30 PM', 4, 'Window side table requested', 2, 'CONFIRMED'),
(2, 'RES-20260825-1002', 'Priya Sharma', '9879054321', 'harshadrakshe28@gmail.com', '2026-08-25', '08:30 PM', 2, 'Birthday celebration decor', NULL, 'PENDING');

SET FOREIGN_KEY_CHECKS = 1;
