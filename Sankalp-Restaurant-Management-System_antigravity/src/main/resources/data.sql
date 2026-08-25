-- =====================================================
-- SANKALP RESTAURANT MANAGEMENT SYSTEM (RMS)
-- SEED DATA
-- Default password for all users: admin123
-- BCrypt hash: $2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
-- =====================================================

-- 1. Default Users
-- Update existing accounts to user's registered email
UPDATE users SET email = 'harshadrakshe28@gmail.com' WHERE email LIKE '%@sankalp.com' OR email IS NULL OR username IN ('admin', 'adminUser', 'chef', 'staff', 'chef1', 'staff1', 'chefUser', 'staffUser');

-- Passwords: admin123 / chef123 / staff123
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

-- 2. Categories
INSERT IGNORE INTO category (category_id, category_name, description, status)
VALUES 
(1, 'Starters', 'Delicious appetizers and snacks', 'ACTIVE'),
(2, 'South Indian', 'Traditional dosas, idlis and vadas', 'ACTIVE'),
(3, 'Main Course', 'Rich curries, paneer, and dal specials', 'ACTIVE'),
(4, 'Breads & Rice', 'Tandoori rotis, naans, and aromatic biryanis', 'ACTIVE'),
(5, 'Desserts', 'Sweet treats and traditional desserts', 'ACTIVE'),
(6, 'Beverages', 'Refreshing mocktails, lassi, and coffee', 'ACTIVE');

-- 3. Menu Items
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

-- 4. Restaurant Tables
INSERT IGNORE INTO restaurant_tables (table_id, table_number, capacity, table_status)
VALUES 
(1, 'T-01', 2, 'AVAILABLE'),
(2, 'T-02', 4, 'AVAILABLE'),
(3, 'T-03', 4, 'AVAILABLE'),
(4, 'T-04', 6, 'AVAILABLE'),
(5, 'T-05', 8, 'AVAILABLE'),
(6, 'T-06', 4, 'AVAILABLE');

-- 5. Default Customers
INSERT IGNORE INTO customer (customer_id, customer_name, mobile, email, address, status)
VALUES 
(1, 'Walk-in Customer', '9999999999', 'walkin@restaurant.com', 'Dine In', 'ACTIVE'),
(2, 'Amitabh Sharma', '9820012345', 'amitabh@gmail.com', 'MG Road, Bangalore', 'ACTIVE'),
(3, 'Neha Verma', '9830067890', 'neha.v@yahoo.com', 'Indiranagar, Bangalore', 'ACTIVE');

-- 6. Initial Inventory Stock
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

-- 7. Initial Table Reservations
INSERT IGNORE INTO table_reservation (reservation_id, reservation_code, guest_name, guest_phone, guest_email, booking_date, booking_time, guest_count, special_request, table_id, status)
VALUES
(1, 'RES-20260825-1001', 'Rajesh Patel', '9898012345', 'harshadrakshe28@gmail.com', '2026-08-25', '07:30 PM', 4, 'Window side table requested', 2, 'CONFIRMED'),
(2, 'RES-20260825-1002', 'Priya Sharma', '9879054321', 'harshadrakshe28@gmail.com', '2026-08-25', '08:30 PM', 2, 'Birthday celebration decor', NULL, 'PENDING');



