-- =====================================================
-- SANKALP RMS - COMPLETE MULTI-OUTLET SEED DATA
-- Safe to run multiple times (uses INSERT IGNORE)
-- Default passwords:
--   ADMIN : admin123 ($2a$10$Qu2vNCPJSXP7HUZrVkiUweOH5/tW4mMpNmKzHd.Pg/pAAuS3hwv.S)
--   CHEF  : chef123  ($2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2)
--   STAFF : staff123 ($2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86)
-- =====================================================

USE smprowor_RMS;

-- -----------------------------------------------------
-- 1. OUTLETS (RESTAURANTS 1, 2, 3)
-- -----------------------------------------------------
INSERT IGNORE INTO restaurants (restaurant_id, restaurant_name, tagline, phone, email, website, address, currency_symbol, logo_path, status) VALUES
(1, 'Sankalp - Chakan', 'Authentic South Indian & Fine Dining', '+91 98765 43210', 'chakan@sankalprestaurant.com', 'https://www.sankalprestaurant.com', 'Pune-Nashik Highway, Chakan', '₹', '/images/sankalp_logo.jpg', 'ACTIVE'),
(2, 'Sankalp - Khed', 'Flavors of North & Pure Veg Cuisine', '+91 98765 99999', 'khed@sankalprestaurant.com', 'https://www.sankalprestaurant.com', 'Pune-Nashik Highway, Khed', '₹', '/images/logo.png', 'ACTIVE'),
(3, 'Sankalp - Pune', 'Heritage Pure Veg & Family Dining', '+91 98220 11223', 'pune@sankalprestaurant.com', 'https://www.sankalprestaurant.com', 'FC Road, Shivajinagar, Pune', '₹', '/images/logo1.png', 'ACTIVE');

INSERT IGNORE INTO restaurant_settings (id, restaurant_id, restaurant_name, tagline, phone, email, website, address, currency_symbol, logo_path) VALUES
(1, 1, 'Sankalp - Chakan', 'Authentic South Indian & Fine Dining', '+91 98765 43210', 'chakan@sankalprestaurant.com', 'https://www.sankalprestaurant.com', 'Pune-Nashik Highway, Chakan', '₹', '/images/sankalp_logo.jpg'),
(2, 2, 'Sankalp - Khed', 'Flavors of North & Pure Veg Cuisine', '+91 98765 99999', 'khed@sankalprestaurant.com', 'https://www.sankalprestaurant.com', 'Pune-Nashik Highway, Khed', '₹', '/images/logo.png'),
(3, 3, 'Sankalp - Pune', 'Heritage Pure Veg & Family Dining', '+91 98220 11223', 'pune@sankalprestaurant.com', 'https://www.sankalprestaurant.com', 'FC Road, Shivajinagar, Pune', '₹', '/images/logo1.png');

-- -----------------------------------------------------
-- 2. STAFF, CHEFS & ADMINS ACROSS ALL 3 OUTLETS
-- -----------------------------------------------------
INSERT IGNORE INTO users (user_id, restaurant_id, full_name, username, password, role, mobile, email, status) VALUES
-- Outlet 1: Sankalp Restaurant
(1, 1, 'Sankalp Admin', 'admin', '$2a$10$Qu2vNCPJSXP7HUZrVkiUweOH5/tW4mMpNmKzHd.Pg/pAAuS3hwv.S', 'ADMIN', '9876543210', 'admin@sankalp.com', 'ACTIVE'),
(2, 1, 'Demo Admin Sankalp', 'adminUser', '$2a$10$Qu2vNCPJSXP7HUZrVkiUweOH5/tW4mMpNmKzHd.Pg/pAAuS3hwv.S', 'ADMIN', '9876500006', 'demo.admin@sankalp.com', 'ACTIVE'),
(3, 1, 'Head Chef Vikas', 'chef', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9876543211', 'vikas.chef@sankalp.com', 'ACTIVE'),
(4, 1, 'Sous Chef Rahul', 'chef1', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9876543214', 'rahul.chef@sankalp.com', 'ACTIVE'),
(5, 1, 'Captain Rohan', 'staff', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9876543212', 'rohan.staff@sankalp.com', 'ACTIVE'),
(6, 1, 'Waiter Amit', 'staff1', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9876543215', 'amit.staff@sankalp.com', 'ACTIVE'),
(10, 1, 'Steward Sneha', 'staff_sneha', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9876543216', 'sneha.staff@sankalp.com', 'ACTIVE'),
(11, 1, 'Tandoor Chef Arjun', 'chef_arjun', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9876543217', 'arjun.chef@sankalp.com', 'ACTIVE'),

-- Outlet 2: ABC Restaurant
(7, 2, 'ABC Admin', 'admin2', '$2a$10$Qu2vNCPJSXP7HUZrVkiUweOH5/tW4mMpNmKzHd.Pg/pAAuS3hwv.S', 'ADMIN', '9898989801', 'abc.admin@restaurant.com', 'ACTIVE'),
(8, 2, 'ABC Head Chef Sanjeev', 'chef2', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9898989802', 'abc.chef@restaurant.com', 'ACTIVE'),
(9, 2, 'ABC Captain Kabir', 'staff2', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9898989803', 'abc.staff@restaurant.com', 'ACTIVE'),
(12, 2, 'ABC Waiter Meera', 'staff2_meera', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9898989804', 'meera.staff@abcrestaurant.com', 'ACTIVE'),
(13, 2, 'ABC Pastry Chef Dev', 'chef2_dev', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9898989805', 'dev.chef@abcrestaurant.com', 'ACTIVE'),

-- Outlet 3: Royal Fine Dining
(14, 3, 'Royal Admin', 'royal_admin', '$2a$10$Qu2vNCPJSXP7HUZrVkiUweOH5/tW4mMpNmKzHd.Pg/pAAuS3hwv.S', 'ADMIN', '9822011201', 'admin@royalfinedining.com', 'ACTIVE'),
(15, 3, 'Royal Master Chef Ranveer', 'royal_chef', '$2a$10$kUYyOU/TI5CV9KujegRCG.WFtaqHoIbgwb06FPwa1auCbNszu4bg2', 'CHEF', '9822011202', 'chef@royalfinedining.com', 'ACTIVE'),
(16, 3, 'Royal Steward Vikram', 'royal_staff', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9822011203', 'staff@royalfinedining.com', 'ACTIVE'),
(17, 3, 'Royal Hostess Ananya', 'royal_staff2', '$2a$10$jCVbfrnUwAPDgBMkuhipoeu3p.m3LhNxB/qrzkmI8cMT1ZDZfoJ86', 'STAFF', '9822011204', 'ananya@royalfinedining.com', 'ACTIVE');

-- -----------------------------------------------------
-- 3. MULTIPLE CATEGORIES PER OUTLET
-- -----------------------------------------------------
INSERT IGNORE INTO category (category_id, restaurant_id, category_name, description, status) VALUES
-- Outlet 1: Sankalp Restaurant
(1, 1, 'Starters & Tandoor', 'Charcoal-grilled appetizers and crispy starters', 'ACTIVE'),
(2, 1, 'South Indian Classics', 'Signature dosas, idlis, uttapams and vadas', 'ACTIVE'),
(3, 1, 'Main Course Curries', 'Rich paneer, dal, and regional Indian gravies', 'ACTIVE'),
(4, 1, 'Rice & Biryani', 'Fragrant dum biryanis and ghee pulao', 'ACTIVE'),
(5, 1, 'Breads & Naan', 'Freshly baked tandoori rotis and stuffed naans', 'ACTIVE'),
(6, 1, 'Beverages & Filter Coffee', 'Authentic filter coffee, lassi and coolers', 'ACTIVE'),
(7, 1, 'Desserts', 'Traditional Indian sweets and ice creams', 'ACTIVE'),

-- Outlet 2: ABC Restaurant
(8, 2, 'Global Starters', 'Crispy nachos, bruschetta and fusion bites', 'ACTIVE'),
(9, 2, 'North Indian Specials', 'Mughlai curries and smoky tandoori mains', 'ACTIVE'),
(10, 2, 'Artisan Pasta & Grill', 'Wood-fired mains and continental plates', 'ACTIVE'),
(11, 2, 'Mocktails & Shakes', 'Craft mocktails and thick shakes', 'ACTIVE'),
(12, 2, 'Gourmet Desserts', 'Warm lava cakes and tiramisu', 'ACTIVE'),

-- Outlet 3: Royal Fine Dining
(13, 3, 'Royal Kebabs', 'Nizami kebabs and saffron-infused starters', 'ACTIVE'),
(14, 3, 'Shahi Curries', 'Slow-simmered royal gravies', 'ACTIVE'),
(15, 3, 'Dum Biryani & Pulao', 'Sealed handi biryanis with aged basmati', 'ACTIVE'),
(16, 3, 'Royal Beverages & Sweets', 'Kesar thandai, shahi tukda and gulab jamun', 'ACTIVE');

-- -----------------------------------------------------
-- 4. MENU ITEMS WITH IMAGES ACROSS ALL 3 OUTLETS
-- -----------------------------------------------------
INSERT IGNORE INTO menu (item_id, restaurant_id, category_id, item_name, description, price, image, status) VALUES
-- Outlet 1: Sankalp Restaurant
(1, 1, 1, 'Royal Paneer Tikka', 'Charcoal-grilled cottage cheese marinated in Kashmiri saffron & spices', 320.00, 'https://images.unsplash.com/photo-1567184109191-3783b3218038?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(2, 1, 1, 'Crispy Veg Spring Rolls', 'Golden fried vegetable rolls served with sweet chilli dip', 220.00, 'https://images.unsplash.com/photo-1544025162-d76694265947?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(3, 1, 2, 'Special Mysore Masala Dosa', 'Crispy golden crepe with red garlic chutney and spiced potato masala', 180.00, 'https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(4, 1, 2, 'Ghee Roast Idli Platter', 'Steamed rice cakes tossed in clarified butter and gun powder spice', 140.00, 'https://images.unsplash.com/photo-1589301760014-d929f3979dbc?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(5, 1, 3, 'Paneer Butter Masala', 'Succulent paneer cubes simmered in rich tomato cashew butter gravy', 340.00, 'https://images.unsplash.com/photo-1631452180519-c014fe946bc7?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(6, 1, 3, 'Dal Makhani Grand Cru', 'Black lentils slow-cooked 24 hours with cream and butter', 290.00, 'https://images.unsplash.com/photo-1546833999-b9f581a1996d?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(7, 1, 4, 'Hyderabadi Subz Dum Biryani', 'Fragrant aged basmati rice cooked on dum with saffron and mint', 360.00, 'https://images.unsplash.com/photo-1589302168068-964664d93dc0?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(8, 1, 5, 'Garlic Butter Naan', 'Soft leavened tandoori flatbread brushed with roasted garlic butter', 75.00, 'https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(9, 1, 6, 'Madras Filter Coffee', 'Traditional South Indian frothy decoction coffee in brass davara', 70.00, 'https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(10, 1, 6, 'Mango Kesariya Lassi', 'Churned probiotic yogurt blended with Alphonso mango and saffron', 130.00, 'https://images.unsplash.com/photo-1553530666-ba11a7da3888?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(11, 1, 7, 'Warm Gulab Jamun', 'Cardamom-scented khoya dumplings soaked in rose sugar syrup', 150.00, 'https://images.unsplash.com/photo-1666190092159-3171cf0fbb12?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),

-- Outlet 2: ABC Restaurant
(12, 2, 8, 'ABC Loaded Nachos', 'Tortilla chips topped with jalapenos, salsa and warm cheese fondue', 260.00, 'https://images.unsplash.com/photo-1513456852971-30c0b8199d4d?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(13, 2, 8, 'Herb Garlic Bruschetta', 'Toasted sourdough topped with vine tomatoes, basil and olive oil', 230.00, 'https://images.unsplash.com/photo-1572695157366-5e585ab2b69f?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(14, 2, 9, 'ABC Kadhai Paneer', 'Cottage cheese tossed with bell peppers and crushed coriander seeds', 350.00, 'https://images.unsplash.com/photo-1631452180519-c014fe946bc7?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(15, 2, 9, 'Old Delhi Butter Chicken', 'Chargrilled chicken tikka folded into velvety tomato makhani sauce', 430.00, 'https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(16, 2, 10, 'Truffle Mushroom Penne', 'Al dente penne in roasted garlic cream and wild mushroom sauce', 380.00, 'https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(17, 2, 11, 'Virgin Mint Mojito', 'Fresh muddled lime, mint leaves and sparkling soda', 140.00, 'https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(18, 2, 12, 'Belgian Chocolate Lava Cake', 'Warm dark chocolate cake with molten ganache center & vanilla scoop', 210.00, 'https://images.unsplash.com/photo-1624353365286-3f8d62daad51?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),

-- Outlet 3: Royal Fine Dining
(19, 3, 13, 'Zafrani Seekh Kebab', 'Skewered spiced minced kebabs glazed with saffron butter', 390.00, 'https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(20, 3, 14, 'Shahi Paneer Korma', 'Royal white cashew-saffron gravy with soft paneer medallions', 380.00, 'https://images.unsplash.com/photo-1567184109191-3783b3218038?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(21, 3, 15, 'Nizami Dum Biryani', 'Authentic Hyderabad handi biryani served with mirchi ka salan', 440.00, 'https://images.unsplash.com/photo-1589302168068-964664d93dc0?auto=format&fit=crop&w=800&q=80', 'AVAILABLE'),
(22, 3, 16, 'Royal Kesar Thandai', 'Chilled almond, pistachio and saffron spiced milk elixir', 160.00, 'https://images.unsplash.com/photo-1553530666-ba11a7da3888?auto=format&fit=crop&w=800&q=80', 'AVAILABLE');

-- -----------------------------------------------------
-- 5. RESTAURANT TABLES ACROSS ALL 3 OUTLETS
-- -----------------------------------------------------
INSERT IGNORE INTO restaurant_tables (table_id, restaurant_id, table_number, capacity, table_status, x_pos, y_pos, shape, width, height) VALUES
-- Outlet 1
(1, 1, 'T-01', 2, 'AVAILABLE', 60, 60, 'ROUND', 90, 90),
(2, 1, 'T-02', 4, 'OCCUPIED', 200, 60, 'RECTANGLE', 120, 90),
(3, 1, 'T-03', 4, 'AVAILABLE', 370, 60, 'RECTANGLE', 120, 90),
(4, 1, 'T-04', 6, 'RESERVED', 60, 200, 'RECTANGLE', 150, 100),
(5, 1, 'T-05', 8, 'AVAILABLE', 260, 200, 'RECTANGLE', 170, 100),
-- Outlet 2
(6, 2, 'ABC-T1', 2, 'AVAILABLE', 60, 60, 'ROUND', 90, 90),
(7, 2, 'ABC-T2', 4, 'OCCUPIED', 200, 60, 'RECTANGLE', 120, 90),
(8, 2, 'ABC-T3', 6, 'AVAILABLE', 370, 60, 'RECTANGLE', 140, 100),
(9, 2, 'ABC-T4', 4, 'AVAILABLE', 60, 200, 'RECTANGLE', 120, 90),
-- Outlet 3
(10, 3, 'ROY-01', 4, 'AVAILABLE', 60, 60, 'RECTANGLE', 120, 90),
(11, 3, 'ROY-02', 6, 'RESERVED', 220, 60, 'RECTANGLE', 150, 100),
(12, 3, 'ROY-03', 8, 'AVAILABLE', 410, 60, 'ROUND', 130, 130);

-- -----------------------------------------------------
-- 6. MULTIPLE CUSTOMERS ACROSS ALL 3 OUTLETS
-- -----------------------------------------------------
INSERT IGNORE INTO customer (customer_id, restaurant_id, customer_name, mobile, email, address, status) VALUES
-- Outlet 1
(1, 1, 'Walk-in Customer', '9999999999', 'walkin@sankalp.com', 'Dine In', 'ACTIVE'),
(2, 1, 'Amitabh Sharma', '9820012345', 'amitabh.sharma@gmail.com', 'Indiranagar, Bangalore', 'ACTIVE'),
(3, 1, 'Neha Kulkarni', '9845011223', 'neha.kulkarni@gmail.com', 'Koramangala, Bangalore', 'ACTIVE'),
(4, 1, 'Raghavendra Rao', '9880055443', 'raghav.rao@gmail.com', 'Jayanagar, Bangalore', 'ACTIVE'),
(5, 1, 'Divya Menon', '9740099887', 'divya.menon@gmail.com', 'MG Road, Bangalore', 'ACTIVE'),
-- Outlet 2
(6, 2, 'ABC Walk-in Guest', '8888888888', 'walkin@abcrestaurant.com', 'Dine In', 'ACTIVE'),
(7, 2, 'Priya Kapoor', '9811122233', 'priya.kapoor@gmail.com', 'Connaught Place, New Delhi', 'ACTIVE'),
(8, 2, 'Siddharth Malhotra', '9810044556', 'siddharth.m@gmail.com', 'Vasant Vihar, New Delhi', 'ACTIVE'),
(9, 2, 'Kavita Singhania', '9871166778', 'kavita.s@gmail.com', 'Hauz Khas, New Delhi', 'ACTIVE'),
-- Outlet 3
(10, 3, 'Royal Walk-in Guest', '7777777777', 'walkin@royalfinedining.com', 'Dine In', 'ACTIVE'),
(11, 3, 'Farhan Qureshi', '9849012345', 'farhan.q@gmail.com', 'Banjara Hills, Hyderabad', 'ACTIVE'),
(12, 3, 'Meenakshi Reddy', '9848054321', 'meenakshi.r@gmail.com', 'Jubilee Hills, Hyderabad', 'ACTIVE');

-- -----------------------------------------------------
-- 7. MULTIPLE INVENTORY ITEMS ACROSS ALL 3 OUTLETS
-- -----------------------------------------------------
INSERT IGNORE INTO inventory (inventory_id, restaurant_id, item_name, category, quantity, unit, min_threshold, cost_per_unit, supplier_name, supplier_contact, status) VALUES
-- Outlet 1
(1, 1, 'Fresh Malai Paneer', 'Dairy', 35.00, 'kg', 10.00, 320.00, 'Amul Dairy Supplies', '9876500001', 'IN_STOCK'),
(2, 1, 'Basmati Rice (Aged 2 Yr)', 'Grains', 60.00, 'kg', 15.00, 110.00, 'Kohinoor Grain Traders', '9876500002', 'IN_STOCK'),
(3, 1, 'Pure Cow Ghee', 'Dairy', 18.00, 'liters', 5.00, 580.00, 'Nandini Dairy', '9876500003', 'IN_STOCK'),
(4, 1, 'Filter Coffee Decoction Beans', 'Beverages', 12.00, 'kg', 3.00, 650.00, 'Coorg Estate Roasters', '9876500004', 'IN_STOCK'),
(5, 1, 'Kashmiri Saffron (Kesar)', 'Spices', 0.40, 'kg', 0.50, 24000.00, 'Pampore Spice Co', '9876500005', 'LOW_STOCK'),
(6, 1, 'San Marzano Tomato Puree', 'Produce', 25.00, 'kg', 8.00, 95.00, 'Metro Fresh Foods', '9876500006', 'IN_STOCK'),
-- Outlet 2
(7, 2, 'ABC Special Spices Pack', 'Spices', 20.00, 'kg', 5.00, 450.00, 'Delhi Spice Hub', '9811100001', 'IN_STOCK'),
(8, 2, 'Fresh Heavy Cream', 'Dairy', 40.00, 'liters', 10.00, 180.00, 'Delhi Dairy Co', '9811100002', 'IN_STOCK'),
(9, 2, 'Italian Penne Pasta', 'Grains', 22.00, 'kg', 6.00, 160.00, 'Italiano Imports', '9811100003', 'IN_STOCK'),
(10, 2, 'Belgian Dark Chocolate', 'Bakery', 4.00, 'kg', 5.00, 890.00, 'Callebaut Distributors', '9811100004', 'LOW_STOCK'),
-- Outlet 3
(11, 3, 'Nizami Garam Masala', 'Spices', 15.00, 'kg', 4.00, 520.00, 'Charminar Spices', '9849000001', 'IN_STOCK'),
(12, 3, 'Royal Cashew Paste Base', 'Dry Fruits', 18.00, 'kg', 5.00, 740.00, 'Deccan Nuts & Spices', '9849000002', 'IN_STOCK'),
(13, 3, 'Aged Sella Basmati', 'Grains', 50.00, 'kg', 12.00, 130.00, 'Deccan Grain Corp', '9849000003', 'IN_STOCK');

-- -----------------------------------------------------
-- 8. SAMPLE RESERVATIONS, ORDERS & BILLS
-- -----------------------------------------------------
INSERT IGNORE INTO table_reservation (reservation_id, restaurant_id, reservation_code, guest_name, guest_phone, guest_email, booking_date, booking_time, guest_count, special_request, table_id, status) VALUES
(1, 1, 'RES-2026-1001', 'Neha Kulkarni', '9845011223', 'neha.kulkarni@gmail.com', CURDATE(), '07:30 PM', 4, 'Window side table requested', 4, 'CONFIRMED'),
(2, 2, 'RES-ABC-1001', 'Priya Kapoor', '9811122233', 'priya.kapoor@gmail.com', CURDATE(), '08:30 PM', 2, 'Quiet corner table', 6, 'CONFIRMED'),
(3, 3, 'RES-ROY-1001', 'Farhan Qureshi', '9849012345', 'farhan.q@gmail.com', CURDATE(), '08:00 PM', 6, 'Anniversary dinner setup', 11, 'CONFIRMED');

INSERT IGNORE INTO orders (order_id, restaurant_id, order_no, customer_id, table_id, staff_id, order_date, total_amount, order_status, order_type, aggregator_channel, remarks) VALUES
(1, 1, 'ORD-1001', 2, 1, 5, NOW(), 800.00, 'COMPLETED', 'DINE_IN', 'DIRECT', 'Less spicy'),
(2, 1, 'ORD-1002', 3, 2, 6, NOW(), 500.00, 'PREPARING', 'DINE_IN', 'DIRECT', 'Extra sambar'),
(3, 2, 'ORD-2001', 7, 7, 9, NOW(), 690.00, 'COMPLETED', 'DINE_IN', 'DIRECT', 'No onions in pasta'),
(4, 3, 'ORD-3001', 11, 10, 16, NOW(), 830.00, 'COMPLETED', 'DINE_IN', 'DIRECT', 'Serve hot');

INSERT IGNORE INTO order_details (order_detail_id, order_id, item_id, quantity, price, subtotal, kitchen_note, kitchen_status, is_extra, prepared_by) VALUES
(1, 1, 1, 1, 320.00, 320.00, 'Medium char', 'SERVED', 0, 3),
(2, 1, 5, 1, 340.00, 340.00, 'Rich butter', 'SERVED', 0, 3),
(3, 1, 9, 2, 70.00, 140.00, 'Hot', 'SERVED', 0, 4),
(4, 2, 3, 2, 180.00, 360.00, 'Crispy roast', 'PREPARING', 0, 3),
(5, 2, 4, 1, 140.00, 140.00, 'Extra ghee', 'PREPARING', 0, 4),
(6, 3, 12, 1, 260.00, 260.00, 'Extra cheese', 'SERVED', 0, 8),
(7, 3, 15, 1, 430.00, 430.00, 'Mild spice', 'SERVED', 0, 8),
(8, 4, 19, 1, 390.00, 390.00, 'Saffron glaze', 'SERVED', 0, 15),
(9, 4, 21, 1, 440.00, 440.00, 'With raita', 'SERVED', 0, 15);

INSERT IGNORE INTO bill (bill_id, restaurant_id, bill_no, order_id, bill_date, subtotal, tax_amount, discount_amount, grand_total, payment_mode, payment_status) VALUES
(1, 1, 'INV-1001', 1, NOW(), 800.00, 40.00, 0.00, 840.00, 'UPI', 'PAID'),
(2, 2, 'INV-2001', 3, NOW(), 690.00, 34.50, 0.00, 724.50, 'CARD', 'PAID'),
(3, 3, 'INV-3001', 4, NOW(), 830.00, 41.50, 0.00, 871.50, 'CASH', 'PAID');
