-- =====================================================
-- SANKALP RMS - COMPLETE MENU & CATEGORIES WITH DOWNLOADS IMAGES
-- Safe to run multiple times (no hardcoded PK collisions)
-- =====================================================

USE smprowor_RMS;

-- -----------------------------------------------------
-- 1. ENSURE MULTIPLE CATEGORIES IN ALL 3 OUTLETS
-- -----------------------------------------------------

-- Outlet 1: Sankalp Restaurant
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Starters & Tandoor', 'Crispy appetizers, soups, and tandoor grills', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Starters & Tandoor');

INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'South Indian & Breakfast', 'Authentic dosas, idlis, poha, upma and snacks', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'South Indian & Breakfast');

INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Main Course & Thali', 'Rich Indian curries, paneer, platters and thalis', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Main Course & Thali');

INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Rice, Biryani & Breads', 'Dum biryanis, jain biryani and butter naan', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Rice, Biryani & Breads');

INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Pizzas, Pasta & Fast Food', 'Wood-fired pizzas, pastas, burgers and noodles', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Pizzas, Pasta & Fast Food');

INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Beverages & Juices', 'Cold coffee, mocktails, fresh juices and masala tea', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Beverages & Juices');

INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Desserts & Ice Cream', 'Traditional sweets, brownies, muffins and ice creams', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Desserts & Ice Cream');

-- Outlet 2: ABC Restaurant
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Global Starters', 'Crispy snacks, soups and tandoori starters', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Global Starters');

INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Pizzas & Pasta', 'Artisan pizzas, garlic bread and creamy pastas', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Pizzas & Pasta');

INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Burgers & Street Bites', 'Gourmet burgers, sandwiches, fries and street food', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Burgers & Street Bites');

INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'North Indian Specials', 'Signature butter chicken, curries and platters', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'North Indian Specials');

INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Mocktails & Desserts', 'Refreshing coolers, brownies and ice creams', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Mocktails & Desserts');

-- Outlet 3: Royal Fine Dining
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Royal Starters & Seafood', 'Tandoori platters, fish fry and prawns fry', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Royal Starters & Seafood');

INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Shahi Curries & Thali', 'Royal curries, malai kofta and chef special thali', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Shahi Curries & Thali');

INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Dum Biryani & Breads', 'Handi biryanis and freshly baked butter naan', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Dum Biryani & Breads');

INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Royal Beverages & Sweets', 'Mocktails, cold coffee, gulab jamun and desserts', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Royal Beverages & Sweets');


-- -----------------------------------------------------
-- 2. INSERT ALL 53 DISHES WITH LOCAL /images/menu/ IMAGES
-- -----------------------------------------------------

-- === OUTLET 1: STARTERS & TANDOOR ===
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Starters & Tandoor' LIMIT 1),
'Paneer Tikka', 'Marinated cottage cheese cubes grilled with spices and peppers', 280.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Tikka');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Starters & Tandoor' LIMIT 1),
'Chicken Tikka', 'Smoky chargrilled chicken marinated in yogurt and spices', 340.00, 'images/menu/Chicken_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Chicken Tikka');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Starters & Tandoor' LIMIT 1),
'Veg Manchurian', 'Crispy vegetable balls tossed in tangy Indo-Chinese garlic sauce', 220.00, 'images/menu/Veg_Manchurian.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Manchurian');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Starters & Tandoor' LIMIT 1),
'Tomato Soup', 'Velvety roasted tomato soup served with crispy croutons', 130.00, 'images/menu/Tomato_Soup.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Tomato Soup');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Starters & Tandoor' LIMIT 1),
'Sweet Corn Soup', 'Warm golden sweet corn broth with finely diced veggies', 140.00, 'images/menu/Sweet_Corn_Soup.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Sweet Corn Soup');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Starters & Tandoor' LIMIT 1),
'Crispy Onion Pakoda', 'Golden spiced fritters served hot with mint chutney', 120.00, 'images/menu/Pakoda.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Crispy Onion Pakoda');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Starters & Tandoor' LIMIT 1),
'Punjabi Samosa (2 Pcs)', 'Flaky pastry stuffed with spiced potatoes and green peas', 80.00, 'images/menu/Samosa.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Punjabi Samosa (2 Pcs)');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Starters & Tandoor' LIMIT 1),
'Fresh Green Salad', 'Crisp cucumber, tomato, carrot and lemon wedges', 110.00, 'images/menu/Green_Salad.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Fresh Green Salad');

-- === OUTLET 1: SOUTH INDIAN & BREAKFAST ===
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'South Indian & Breakfast' LIMIT 1),
'Special Masala Dosa', 'Crispy golden crepe filled with spiced potato masala and chutneys', 160.00, 'images/menu/Masala_Dosa.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Special Masala Dosa');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'South Indian & Breakfast' LIMIT 1),
'Idli Sambar Platter', 'Steamed fluffy rice cakes served with hot drumstick sambar & chutney', 120.00, 'images/menu/Idli_Sambar.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Idli Sambar Platter');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'South Indian & Breakfast' LIMIT 1),
'Kanda Poha', 'Flattened rice tempered with mustard seeds, peanuts and coriander', 90.00, 'images/menu/Poha.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Kanda Poha');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'South Indian & Breakfast' LIMIT 1),
'Ghee Rava Upma', 'Savory semolina breakfast cooked with curry leaves and cashews', 95.00, 'images/menu/Upma.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Ghee Rava Upma');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'South Indian & Breakfast' LIMIT 1),
'Mumbai Vada Pav', 'Spiced potato fritter inside buttered pav with dry garlic chutney', 60.00, 'images/menu/Vada_Pav.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mumbai Vada Pav');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'South Indian & Breakfast' LIMIT 1),
'Butter Pav Bhaji', 'Rich mashed vegetable curry topped with butter and toasted pav', 170.00, 'images/menu/Pav_Bhaji.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Butter Pav Bhaji');

-- === OUTLET 1: MAIN COURSE & THALI ===
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Main Course & Thali' LIMIT 1),
'Paneer Butter Masala', 'Succulent paneer cubes in rich tomato cashew butter gravy', 320.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Butter Masala');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Main Course & Thali' LIMIT 1),
'Malai Kofta', 'Melt-in-mouth paneer potato dumplings in creamy saffron gravy', 330.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Malai Kofta');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Main Course & Thali' LIMIT 1),
'Veg Kolhapuri', 'Mixed seasonal vegetables in fiery Kolhapuri masala', 280.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Kolhapuri');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Main Course & Thali' LIMIT 1),
'Jain Paneer Masala', 'Satvik cottage cheese curry prepared without onion or garlic', 310.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Jain Paneer Masala');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Main Course & Thali' LIMIT 1),
'Chef Special Thali', 'Complete royal platter with 2 curries, dal, rice, naan, salad & sweet', 420.00, 'images/menu/Chef_Special_Thali.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Chef Special Thali');

-- === OUTLET 1: RICE, BIRYANI & BREADS ===
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice, Biryani & Breads' LIMIT 1),
'Hyderabadi Veg Biryani', 'Fragrant basmati rice slow-cooked with vegetables and spices', 290.00, 'images/menu/Veg_Biryani.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Hyderabadi Veg Biryani');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice, Biryani & Breads' LIMIT 1),
'Jain Dum Biryani', 'Aromatic saffron rice cooked without onion or garlic with raita', 280.00, 'images/menu/Jain_Biryani.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Jain Dum Biryani');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice, Biryani & Breads' LIMIT 1),
'Butter Naan', 'Soft tandoor-baked flatbread brushed generously with melted butter', 65.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Butter Naan');

-- === OUTLET 1: PIZZAS, PASTA & FAST FOOD ===
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Pizzas, Pasta & Fast Food' LIMIT 1),
'Margherita Pizza', 'Classic wood-fired crust with San Marzano sauce, mozzarella & basil', 260.00, 'images/menu/Margherita_Pizza.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Margherita Pizza');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Pizzas, Pasta & Fast Food' LIMIT 1),
'Farmhouse Pizza', 'Loaded with bell peppers, mushrooms, onions, sweet corn & cheese', 340.00, 'images/menu/Farmhouse_Pizza.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Farmhouse Pizza');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Pizzas, Pasta & Fast Food' LIMIT 1),
'White Sauce Pasta', 'Creamy Alfredo penne tossed with broccoli, olives and herbs', 270.00, 'images/menu/White_Sauce_Pasta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'White Sauce Pasta');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Pizzas, Pasta & Fast Food' LIMIT 1),
'Veg Hakka Noodles', 'Wok-tossed noodles with crunchy julienned vegetables and soy', 210.00, 'images/menu/Veg_Hakka_Noodles.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Hakka Noodles');

-- === OUTLET 1: BEVERAGES & JUICES ===
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Beverages & Juices' LIMIT 1),
'Cold Coffee', 'Chilled creamy blended coffee topped with chocolate drizzle', 140.00, 'images/menu/Cold_Coffee.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Cold Coffee');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Beverages & Juices' LIMIT 1),
'Virgin Mojito', 'Refreshing lime, fresh mint leaves and sparkling soda', 130.00, 'images/menu/Virgin_Mojito.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Virgin Mojito');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Beverages & Juices' LIMIT 1),
'Blue Lagoon Mocktail', 'Citrus blue curacao cooler with lemon zest and crushed ice', 150.00, 'images/menu/Blue_Lagoon.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Blue Lagoon Mocktail');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Beverages & Juices' LIMIT 1),
'Fresh Mango Juice', 'Thick seasonal Alphonso mango juice served chilled', 130.00, 'images/menu/Mango_Juice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Fresh Mango Juice');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Beverages & Juices' LIMIT 1),
'Fresh Orange Juice', 'Cold-pressed Valencia orange juice with no added sugar', 120.00, 'images/menu/Orange_Juice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Fresh Orange Juice');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Beverages & Juices' LIMIT 1),
'Masala Chai', 'Aromatic Indian spiced milk tea brewed with ginger & cardamom', 50.00, 'images/menu/tea.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Masala Chai');

-- === OUTLET 1: DESSERTS & ICE CREAM ===
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Desserts & Ice Cream' LIMIT 1),
'Gulab Jamun (2 Pcs)', 'Warm khoya dumplings soaked in saffron rose syrup', 110.00, 'images/menu/Gulab_Jamun.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Gulab Jamun (2 Pcs)');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Desserts & Ice Cream' LIMIT 1),
'Chocolate Brownie', 'Fudgy dark chocolate walnut brownie served warm', 160.00, 'images/menu/Chocolate_Brownie.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Chocolate Brownie');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Desserts & Ice Cream' LIMIT 1),
'Chocolate Ice Cream', 'Rich Belgian dark chocolate scoop', 110.00, 'images/menu/Chocolate_Ice_Cream.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Chocolate Ice Cream');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Desserts & Ice Cream' LIMIT 1),
'Vanilla Ice Cream', 'Classic Madagascar vanilla bean scoop', 95.00, 'images/menu/Vanilla_Ice_Cream.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Vanilla Ice Cream');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Desserts & Ice Cream' LIMIT 1),
'Fresh Fruit Salad', 'Seasonal chilled cut fruits with honey-mint drizzle', 140.00, 'images/menu/Fruit_Salad.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Fresh Fruit Salad');


-- === OUTLET 2: ABC RESTAURANT ===
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Global Starters' LIMIT 1),
'Crispy Chicken Nuggets', 'Golden breaded tender chicken bites with honey mustard dip', 240.00, 'images/menu/Chicken_Nuggets.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Crispy Chicken Nuggets');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Pizzas & Pasta' LIMIT 1),
'Double Cheese Pizza', 'Extra mozzarella & cheddar blend on crispy crust', 310.00, 'images/menu/cheese_pizza.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Double Cheese Pizza');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Pizzas & Pasta' LIMIT 1),
'Mini Cheese Pizza', 'Personal-sized cheesy baked pizza', 180.00, 'images/menu/Mini_Pizza.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mini Cheese Pizza');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Pizzas & Pasta' LIMIT 1),
'Garlic Bread Sticks', 'Oven-toasted herb garlic bread with melted cheese', 150.00, 'images/menu/Garlic_Bread.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Garlic Bread Sticks');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Pizzas & Pasta' LIMIT 1),
'Arrabbiata Red Sauce Pasta', 'Spicy garlic tomato basil penne topped with parmesan', 260.00, 'images/menu/Red_Sauce_Pasta.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Arrabbiata Red Sauce Pasta');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Burgers & Street Bites' LIMIT 1),
'Classic Veg Burger', 'Crispy herb veggie patty with lettuce, tomato and mayo', 150.00, 'images/menu/Veg_Burger.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Classic Veg Burger');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Burgers & Street Bites' LIMIT 1),
'Double Cheese Burger', 'Juicy grilled patty with double cheddar slice and pickles', 190.00, 'images/menu/Cheese_Burger.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Double Cheese Burger');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Burgers & Street Bites' LIMIT 1),
'Grilled Veg Club Sandwich', 'Triple-layer toasted sandwich with mint chutney and cheese', 160.00, 'images/menu/Veg_Sandwich.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Grilled Veg Club Sandwich');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Burgers & Street Bites' LIMIT 1),
'Salted French Fries', 'Golden crispy potato fries served with ketchup', 110.00, 'images/menu/French_Fries.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Salted French Fries');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'North Indian Specials' LIMIT 1),
'Old Delhi Butter Chicken', 'Chargrilled chicken tikka in velvety tomato makhani gravy', 410.00, 'images/menu/Butter_Chicken.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Old Delhi Butter Chicken');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Mocktails & Desserts' LIMIT 1),
'Chocolate Choco-Chip Muffin', 'Soft bakery muffin loaded with dark chocolate chunks', 130.00, 'images/menu/Chocolate_Muffin.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Chocolate Choco-Chip Muffin');


-- === OUTLET 3: ROYAL FINE DINING ===
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Royal Starters & Seafood' LIMIT 1),
'Royal Chicken Platter', 'Assorted tandoori chicken, seekh kebab and tikka platter', 580.00, 'images/menu/Royal_Chicken_Platter.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Royal Chicken Platter');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Royal Starters & Seafood' LIMIT 1),
'Amritsari Fish Fry', 'Crisp spiced coastal fish fillets served with tartar dip', 390.00, 'images/menu/Fish_Fry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Amritsari Fish Fry');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Royal Starters & Seafood' LIMIT 1),
'Golden Prawns Fry', 'Pan-seared king prawns tossed in garlic butter and chilli', 460.00, 'images/menu/Prawns_Fry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Golden Prawns Fry');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Shahi Curries & Thali' LIMIT 1),
'Royal Mutton Curry', 'Slow-braised tender mutton in rich aromatic Kashmiri gravy', 490.00, 'images/menu/Mutton_Curry.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Royal Mutton Curry');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Shahi Curries & Thali' LIMIT 1),
'Royal Butter Chicken', 'Classic smoky chicken in rich cashew tomato gravy', 430.00, 'images/menu/Butter_Chicken.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Royal Butter Chicken');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Dum Biryani & Breads' LIMIT 1),
'Spicy Chicken Dum Biryani', 'Authentic Hyderabadi dum chicken biryani with mirchi ka salan', 420.00, 'images/menu/Spicy_chicken_biryani.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Spicy Chicken Dum Biryani');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Dum Biryani & Breads' LIMIT 1),
'Royal Butter Naan', 'Fluffy tandoor naan finished with clarified butter', 75.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Royal Butter Naan');

INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Royal Beverages & Sweets' LIMIT 1),
'Royal Gulab Jamun', 'Warm saffron cardamom gulab jamun', 130.00, 'images/menu/Gulab_Jamun.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Royal Gulab Jamun');

-- -----------------------------------------------------
-- 3. UPDATE EXISTING ITEMS WITH LOCAL IMAGES
-- -----------------------------------------------------
UPDATE menu SET image = 'images/menu/Paneer_Tikka.jpg' WHERE item_name = 'Paneer Tikka';
UPDATE menu SET image = 'images/menu/Masala_Dosa.png' WHERE item_name = 'Special Masala Dosa';
UPDATE menu SET image = 'images/menu/Paneer_Butter_Masala.png' WHERE item_name = 'Paneer Butter Masala';
UPDATE menu SET image = 'images/menu/Cold_Coffee.jpg' WHERE item_name = 'Special Filter Coffee';

SELECT restaurant_id, COUNT(*) AS total_dishes FROM menu GROUP BY restaurant_id ORDER BY restaurant_id;
