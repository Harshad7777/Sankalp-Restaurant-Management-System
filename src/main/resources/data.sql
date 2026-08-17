-- =====================================================
-- SANKALP RESTAURANT MANAGEMENT SYSTEM (RMS)
-- SEED DATA
-- Default password for all users: admin123
-- BCrypt hash: $2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
-- =====================================================

-- 1. Default Users
INSERT IGNORE INTO users (user_id, full_name, username, password, role, mobile, email, status)
VALUES 
(1, 'System Administrator', 'admin', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'ADMIN', '9876543210', 'admin@sankalp.com', 'ACTIVE'),
(2, 'Head Chef Vikas', 'chef1', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'CHEF', '9876543211', 'chef@sankalp.com', 'ACTIVE'),
(3, 'Staff Waiter Rohan', 'staff1', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'STAFF', '9876543212', 'staff@sankalp.com', 'ACTIVE');

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
