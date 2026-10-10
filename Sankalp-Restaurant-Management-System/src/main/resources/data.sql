-- =====================================================
-- SANKALP RESTAURANT MANAGEMENT SYSTEM (RMS)
-- MULTI-TENANT SEED DATA
-- Default passwords:
-- admin / adminUser / admin2: admin123 ($2a$10$Qu2vNCPJSXP7HUZrVkiUweOH5/tW4mMpNmKzHd.Pg/pAAuS3hwv.S)
-- chef / chef1 / chef2: chef123 ($2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2)
-- staff / staff1 / staff2: staff123 ($2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86)
-- =====================================================

-- 1. Default Restaurants (Tenants)
INSERT IGNORE INTO restaurants (restaurant_id, restaurant_name, tagline, phone, email, website, address, currency_symbol, logo_path, status)
VALUES
(1, 'Sankalp Restaurant', 'Authentic South Indian & Fine Dining', '+91 98765 43210', 'sankalp@restaurant.com', 'https://www.sankalprestaurant.com', 'MG Road, Bangalore', '₹', '/images/sankalp_logo.jpg', 'ACTIVE'),
(2, 'ABC Restaurant', 'Flavors of North & International Cuisine', '+91 98765 99999', 'abc@restaurant.com', 'https://www.abcrestaurant.com', 'Connaught Place, New Delhi', '₹', '/images/logo.png', 'ACTIVE');

-- 2. Restaurant Settings for each tenant
INSERT IGNORE INTO restaurant_settings (id, restaurant_id, restaurant_name, tagline, phone, email, website, address, currency_symbol, logo_path)
VALUES
(1, 1, 'Sankalp Restaurant', 'Authentic South Indian & Fine Dining', '+91 98765 43210', 'sankalp@restaurant.com', 'https://www.sankalprestaurant.com', 'MG Road, Bangalore', '₹', '/images/sankalp_logo.jpg'),
(2, 2, 'ABC Restaurant', 'Flavors of North & International Cuisine', '+91 98765 99999', 'abc@restaurant.com', 'https://www.abcrestaurant.com', 'Connaught Place, New Delhi', '₹', '/images/logo.png');

-- 3. Users for each restaurant
-- Admin 1, Chef 1, Staff 1 belong to Restaurant 1 (Sankalp)
-- Admin 2, Chef 2, Staff 2 belong to Restaurant 2 (ABC Restaurant)
INSERT IGNORE INTO users (user_id, restaurant_id, full_name, username, password, role, mobile, email, status)
VALUES 
-- Restaurant 1 Users (Sankalp)
(1, 1, 'Sankalp Admin', 'admin', '$2a$10$Qu2vNCPJSXP7HUZrVkiUweOH5/tW4mMpNmKzHd.Pg/pAAuS3hwv.S', 'ADMIN', '9876543210', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(2, 1, 'Demo Admin Sankalp', 'adminUser', '$2a$10$Qu2vNCPJSXP7HUZrVkiUweOH5/tW4mMpNmKzHd.Pg/pAAuS3hwv.S', 'ADMIN', '9876500006', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(3, 1, 'Head Chef Vikas', 'chef', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9876543211', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(4, 1, 'Head Chef Rahul', 'chef1', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9876543211', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(5, 1, 'Staff Waiter Rohan', 'staff', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9876543212', 'harshadrakshe28@gmail.com', 'ACTIVE'),
(6, 1, 'Staff Waiter Amit', 'staff1', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9876543212', 'harshadrakshe28@gmail.com', 'ACTIVE'),

-- Restaurant 2 Users (ABC Restaurant)
(7, 2, 'ABC Admin', 'admin2', '$2a$10$Qu2vNCPJSXP7HUZrVkiUweOH5/tW4mMpNmKzHd.Pg/pAAuS3hwv.S', 'ADMIN', '9898989801', 'abc.admin@restaurant.com', 'ACTIVE'),
(8, 2, 'ABC Head Chef Sanjeev', 'chef2', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9898989802', 'abc.chef@restaurant.com', 'ACTIVE'),
(9, 2, 'ABC Staff Waiter Kabir', 'staff2', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9898989803', 'abc.staff@restaurant.com', 'ACTIVE');

-- 4. Categories per restaurant
INSERT IGNORE INTO category (category_id, restaurant_id, category_name, description, status)
VALUES 
-- Restaurant 1 Categories
(1, 1, 'Starters', 'Delicious appetizers and snacks', 'ACTIVE'),
(2, 1, 'South Indian', 'Traditional dosas, idlis and vadas', 'ACTIVE'),
(3, 1, 'Main Course', 'Rich curries, paneer, and dal specials', 'ACTIVE'),
(4, 1, 'Beverages', 'Refreshing mocktails, lassi, and coffee', 'ACTIVE'),

-- Restaurant 2 Categories
(5, 2, 'ABC Appetizers', 'Exclusive starters for ABC Restaurant', 'ACTIVE'),
(6, 2, 'North Indian Delights', 'Royal Mughlai & North Indian Curries', 'ACTIVE'),
(7, 2, 'ABC Desserts', 'Exotic international sweets', 'ACTIVE');

-- 5. Menu Items per restaurant
INSERT IGNORE INTO menu (item_id, restaurant_id, category_id, item_name, description, price, image, status)
VALUES
-- Restaurant 1 Menu
(1, 1, 1, 'Paneer Tikka', 'Marinated cottage cheese cubes grilled with spices and peppers', 280.00, 'paneer_tikka.jpg', 'AVAILABLE'),
(2, 1, 2, 'Special Masala Dosa', 'Crispy golden crepe filled with spiced potato masala', 160.00, 'masala_dosa.jpg', 'AVAILABLE'),
(3, 1, 3, 'Paneer Butter Masala', 'Succulent paneer cubes in rich tomato butter gravy', 320.00, 'paneer_butter_masala.jpg', 'AVAILABLE'),
(4, 1, 4, 'Special Filter Coffee', 'Authentic South Indian hot brew filter coffee', 70.00, 'filter_coffee.jpg', 'AVAILABLE'),

-- Restaurant 2 Menu
(5, 2, 5, 'ABC Crisp Nachos', 'Loaded nachos with jalapenos and cheese dip', 240.00, 'spring_rolls.jpg', 'AVAILABLE'),
(6, 2, 6, 'ABC Kadhai Paneer', 'Spicy cottage cheese cooked with bell peppers in kadhai gravy', 340.00, 'paneer_butter_masala.jpg', 'AVAILABLE'),
(7, 2, 6, 'ABC Butter Chicken Spec', 'Smokey chicken in rich creamy tomato curry', 420.00, 'dal_makhani.jpg', 'AVAILABLE'),
(8, 2, 7, 'ABC Chocolate Lava Cake', 'Warm chocolate cake with molten chocolate core', 180.00, 'gulab_jamun.jpg', 'AVAILABLE');

-- 6. Tables per restaurant
INSERT IGNORE INTO restaurant_tables (table_id, restaurant_id, table_number, capacity, table_status)
VALUES 
-- Restaurant 1 Tables
(1, 1, 'T-01', 2, 'AVAILABLE'),
(2, 1, 'T-02', 4, 'AVAILABLE'),
(3, 1, 'T-03', 4, 'AVAILABLE'),
(4, 1, 'T-04', 6, 'AVAILABLE'),

-- Restaurant 2 Tables
(5, 2, 'ABC-T1', 2, 'AVAILABLE'),
(6, 2, 'ABC-T2', 4, 'AVAILABLE'),
(7, 2, 'ABC-T3', 6, 'AVAILABLE');

-- 7. Customers per restaurant
INSERT IGNORE INTO customer (customer_id, restaurant_id, customer_name, mobile, email, address, status)
VALUES 
-- Restaurant 1 Customers
(1, 1, 'Walk-in Customer', '9999999999', 'walkin@sankalp.com', 'Dine In', 'ACTIVE'),
(2, 1, 'Amitabh Sharma (Sankalp Guest)', '9820012345', 'amitabh@gmail.com', 'MG Road, Bangalore', 'ACTIVE'),

-- Restaurant 2 Customers
(3, 2, 'ABC Walk-in Guest', '8888888888', 'walkin@abcrestaurant.com', 'Dine In', 'ACTIVE'),
(4, 2, 'Priya Kapoor (ABC Guest)', '9811122233', 'priya.kapoor@gmail.com', 'Connaught Place, Delhi', 'ACTIVE');

-- 8. Inventory Stock per restaurant
INSERT IGNORE INTO inventory (inventory_id, restaurant_id, item_name, category, quantity, unit, min_threshold, cost_per_unit, supplier_name, supplier_contact, status)
VALUES 
-- Restaurant 1 Stock
(1, 1, 'Fresh Paneer (Malai)', 'Dairy', 35.00, 'kg', 10.00, 320.00, 'Amul Dairy Supplies', '9876500001', 'IN_STOCK'),
(2, 1, 'Basmati Rice (Aged 2 Yr)', 'Grains', 50.00, 'kg', 15.00, 110.00, 'Kohinoor Grain Traders', '9876500002', 'IN_STOCK'),

-- Restaurant 2 Stock
(3, 2, 'ABC Special Spices Pack', 'Spices', 20.00, 'kg', 5.00, 450.00, 'Delhi Spice Hub', '9811100001', 'IN_STOCK'),
(4, 2, 'ABC Amul Cream', 'Dairy', 40.00, 'liters', 10.00, 180.00, 'Delhi Dairy Co', '9811100002', 'IN_STOCK');

-- 9. Table Reservations per restaurant
INSERT IGNORE INTO table_reservation (reservation_id, restaurant_id, reservation_code, guest_name, guest_phone, guest_email, booking_date, booking_time, guest_count, special_request, table_id, status)
VALUES
(1, 1, 'RES-20260825-1001', 'Rajesh Patel', '9898012345', 'harshadrakshe28@gmail.com', '2026-08-25', '07:30 PM', 4, 'Window side table requested', 2, 'CONFIRMED'),
(2, 2, 'RES-ABC-1001', 'Vikramaditya', '9879054321', 'vikram@abc.com', '2026-08-25', '08:30 PM', 2, 'Corner booth requested', 6, 'CONFIRMED');
