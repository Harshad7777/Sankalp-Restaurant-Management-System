-- =====================================================
-- SANKALPA PURE VEG - COMPLETE MENU & CATEGORIES SEED
-- Transcribed from Sankalpa Pure Veg menu photos
-- Applied to all 3 Sankalp branches (Chakan=1, Khed=2, Pune=3)
-- Safe to run multiple times (idempotent)
-- =====================================================

USE smprowor_RMS;

-- -----------------------------------------------------
-- OUTLET 1 CATEGORIES
-- -----------------------------------------------------
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Special Dishes', 'Sankalpa Pure Veg chef special curries', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Special Dishes');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Kaju Special', 'Rich cashew nut gravies and specials', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Kaju Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Mushroom Special', 'Fresh mushroom curries and masala dishes', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Mushroom Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Paneer Special', 'Signature cottage cheese gravies and tikkas', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Paneer Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Punjabi Dishes', 'Traditional Punjabi and Maharashtrian sabzis', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Chinese', 'Indo-Chinese starters and noodles', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Chinese');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Sweet Bhaji', 'Mildly spiced creamy and sweet curries', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Sweet Bhaji');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Aalu Special', 'Classic potato gravies and dum aalu', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Aalu Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Dal Special', 'Homestyle and restaurant-style lentils', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Dal Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Handi', 'Full and half handi clay-pot specials', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Handi');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Snacks', 'Hot crispy snacks and quick bites', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Snacks');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Soup', 'Freshly prepared hot soups', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Soup');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Rice', 'Steamed rice, jeera rice, biryani and fried rice', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Rice');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Roti', 'Fresh tandoori rotis, naans and parathas', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Roti');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Papad', 'Roasted, fried and masala papad', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Papad');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 1, 'Cold Drinks', 'Soft drinks, lassi, tak and mineral water', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 1 AND category_name = 'Cold Drinks');

-- OUTLET 1 MENU ITEMS (171 dishes)
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Tawa', 'Mixed vegetables tossed on hot tawa with spices', 150.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Tawa');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Kadhai', 'Seasonal veggies cooked in aromatic kadhai masala', 140.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Kadhai');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Maharani', 'Rich creamy mixed vegetable royal curry', 150.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Maharani');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Haidrabadi', 'Spicy Hyderabadi style green herb vegetable curry', 150.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Haidrabadi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Jaipuri', 'Jaipuri style mixed veg gravy with crisp papad', 150.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Jaipuri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Makkhanwala', 'Mixed vegetables simmered in rich buttery tomato gravy', 150.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Makkhanwala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Maratha', 'Spicy Maharashtrian style vegetable kofta curry', 130.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Maratha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Bhuna', 'Slow-roasted spiced vegetable masala', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Bhuna');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Dilruba', 'Chef special rich red & golden veg curry', 150.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Dilruba');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Banjara', 'Smoky dhaba-style spiced vegetable curry', 150.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Banjara');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Sundari', 'Special Sankalpa signature veg preparation', 150.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Sundari');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Kaju Special' LIMIT 1), 'Kaju Kari', 'Roasted cashews simmered in spiced onion-tomato curry', 130.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Kaju Kari');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Kaju Special' LIMIT 1), 'Kaju Masala', 'Rich cashew nut masala with aromatic Indian spices', 130.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Kaju Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Kaju Special' LIMIT 1), 'Kaju Paneer', 'Cashews and soft paneer cubes in creamy gravy', 160.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Kaju Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Kaju Special' LIMIT 1), 'Kaju Fry', 'Crispy golden butter-roasted salted cashews', 250.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Kaju Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Masala', 'Fresh button mushrooms in spiced onion-tomato gravy', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mushroom Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Fry', 'Crispy sauteed mushrooms with black pepper and herbs', 160.00, 'images/menu/Veg_Manchurian.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mushroom Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Babycorn', 'Mushrooms and tender babycorn in rich masala', 180.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mushroom Babycorn');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Paneer Masala', 'Mushrooms and paneer cooked in thick masala gravy', 150.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mushroom Paneer Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Kaju Masala', 'Mushrooms and cashews in creamy spiced gravy', 150.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mushroom Kaju Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Butter Masala', 'Mushrooms simmered in velvety butter tomato gravy', 140.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mushroom Butter Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Tikka Masala', 'Char-grilled mushrooms in smoky tikka masala', 140.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mushroom Tikka Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Masala', 'Classic cottage cheese cubes in spiced masala gravy', 120.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Butter Masala', 'Soft paneer in rich creamy tomato butter gravy', 130.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Butter Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Tikka Masala', 'Tandoori paneer tikka in smoky spiced gravy', 130.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Tikka Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Burji', 'Scrambled paneer cooked with onions, tomatoes and spices', 140.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Burji');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Kadhai', 'Paneer and bell peppers tossed in kadhai masala', 150.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Kadhai');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Kopta', 'Soft paneer dumplings in rich creamy gravy', 150.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Kopta');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Paneer Special' LIMIT 1), 'Mutter Paneer', 'Green peas and paneer cubes in homestyle tomato gravy', 130.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mutter Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Paneer Special' LIMIT 1), 'Green Peas Paneer', 'Fresh green peas and cottage cheese masala', 130.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Green Peas Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Pasanda', 'Stuffed paneer triangles in rich Mughlai gravy', 180.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Pasanda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Paneer Special' LIMIT 1), 'Shahi Paneer', 'Royal creamy cashew-saffron paneer curry', 140.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Shahi Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Anguri', 'Mini paneer balls in aromatic rich gravy', 180.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Anguri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Paneer Special' LIMIT 1), 'Palak Paneer', 'Fresh spinach puree cooked with soft paneer cubes', 130.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Palak Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Shev Bhaji', 'Traditional Maharashtrian spicy sev curry', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Shev Bhaji');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Green Peas', 'Fresh green peas cooked in spiced gravy', 70.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Green Peas');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Green Peas Fry', 'Tossed green peas with onion and Indian spices', 80.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Green Peas Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Green Peas Dry', 'Dry spiced green peas preparation', 80.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Green Peas Dry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Chana Masala', 'Chickpeas simmered in tangy Punjabi masala', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Chana Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Chana Masala Fry', 'Fried spiced chickpeas with onion-tomato tadka', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Chana Masala Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Chana Masala Dry', 'Dry roasted spicy chana masala', 80.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Chana Masala Dry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Baingan Masala', 'Stuffed baby brinjals in spiced peanut-onion gravy', 70.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Baingan Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Baingan Bharta', 'Smoky roasted mashed eggplant with spices', 80.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Baingan Bharta');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Shimla Masala', 'Capsicum cooked in flavorful masala gravy', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Shimla Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Shimla Fry', 'Crispy sauteed spiced capsicum', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Shimla Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Stuff Shimla', 'Capsicum stuffed with spiced potato-paneer filling', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Stuff Shimla');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Plain Palak', 'Smoothly tempered spinach curry', 70.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Plain Palak');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Soyabin Palak', 'Protein-rich soya chunks in spinach gravy', 70.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Soyabin Palak');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Bhendi Fry', 'Crispy okra tossed with turmeric and spices', 75.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Bhendi Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Bhendi Masala', 'Okra cooked in rich onion-tomato masala', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Bhendi Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Soyabin Masala', 'Soya chunks simmered in spicy masala gravy', 70.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Soyabin Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Soyabin Fry', 'Pan-tossed spiced soya chunks', 75.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Soyabin Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Soyabin Dry', 'Dry roasted spicy soya preparation', 75.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Soyabin Dry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Mataki Masala', 'Sprouted moth beans in traditional Maharashtrian masala', 70.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mataki Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Mataki Fry', 'Dry spiced sprouted matki usal', 75.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mataki Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Rajma Masala', 'Red kidney beans cooked in thick Punjabi gravy', 70.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Rajma Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Rajma Fry', 'Tempered spiced rajma masala', 75.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Rajma Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Akkha Masur Masala', 'Whole brown lentils in spicy Kolhapuri masala', 70.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Akkha Masur Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Akkha Masur Fry', 'Rich garlic-tempered whole masoor fry', 75.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Akkha Masur Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Patiyala', 'Stuffed papad rolls served in rich Punjabi gravy', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Patiyala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Butter Ball', 'Crispy veg balls in creamy butter masala', 130.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Butter Ball');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Anguri', 'Spiced vegetable dumplings in rich gravy', 140.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Anguri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Tiranga', 'Three-layered tricolor signature veg delicacy', 150.00, 'images/menu/Chef_Special_Thali.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Tiranga');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Maharaja', 'Royal mixed vegetable curry with rich spices', 110.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Maharaja');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Kolhapuri', 'Fiery Kolhapuri spiced mixed vegetables', 85.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Kolhapuri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Mix Veg', 'Assorted seasonal vegetables in homestyle gravy', 80.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mix Veg');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Khurma', 'Mildly spiced creamy vegetable korma', 110.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Khurma');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Singapuri', 'Sweet-spicy Singapuri style vegetable curry', 120.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Singapuri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Green Garden', 'Fresh green herbs and vegetable specialty', 140.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Green Garden');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Kopta', 'Crispy vegetable koftas in rich gravy', 120.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Kopta');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Hariyali', 'Vegetables cooked in vibrant spinach-coriander gravy', 110.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Hariyali');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Mumtaj', 'Rich Mughlai style mixed vegetable curry', 110.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Mumtaj');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Lengcha', 'Special cylindrical veg rolls in spiced gravy', 110.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Lengcha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Khima', 'Finely minced vegetables cooked in aromatic spices', 110.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Khima');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Khandala', 'Special spicy hill-station style veg masala', 110.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Khandala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Tamatar Chatani', 'Tangy spiced tomato chutney curry', 90.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Tamatar Chatani');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Stuff Tamatar', 'Whole tomatoes stuffed with spiced potato-paneer', 120.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Stuff Tamatar');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Chinese' LIMIT 1), 'Veg Manchurian', 'Crispy vegetable balls tossed in tangy Manchurian sauce', 80.00, 'images/menu/Veg_Manchurian.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Manchurian');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Chinese' LIMIT 1), 'Soyabin Chilli', 'Crispy soya chunks tossed with chillies and soy sauce', 85.00, 'images/menu/Veg_Manchurian.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Soyabin Chilli');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Chinese' LIMIT 1), 'Paneer Chilli', 'Wok-tossed paneer cubes with peppers and chilli sauce', 110.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Chilli');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Chinese' LIMIT 1), 'Mushroom Chilli', 'Crispy mushrooms tossed in spicy garlic chilli sauce', 110.00, 'images/menu/Veg_Manchurian.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mushroom Chilli');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Chinese' LIMIT 1), 'Veg Hakka Noodles', 'Stir-fried noodles with crunchy vegetables', 100.00, 'images/menu/Veg_Hakka_Noodles.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Hakka Noodles');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Sweet Bhaji' LIMIT 1), 'Malai Kopta', 'Melt-in-mouth malai koftas in creamy sweet gravy', 120.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Malai Kopta');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Sweet Bhaji' LIMIT 1), 'Navratna Kurma', 'Nine-gem mixed vegetables and fruits in creamy korma', 120.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Navratna Kurma');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Sweet Bhaji' LIMIT 1), 'Paneer Shan Savera', 'Spinach and paneer koftas in rich golden gravy', 140.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Shan Savera');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Sweet Bhaji' LIMIT 1), 'Paneer Pasanda (Sweet)', 'Rich creamy stuffed paneer pasanda', 140.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Pasanda (Sweet)');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Aalu Special' LIMIT 1), 'Dam Aalu', 'Baby potatoes simmered in spiced yogurt-tomato gravy', 120.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Dam Aalu');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Aalu Special' LIMIT 1), 'Dam Aalu Panjabi', 'Rich Punjabi style stuffed dum aloo', 130.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Dam Aalu Panjabi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Aalu Special' LIMIT 1), 'Dam Aalu Kashmiri', 'Aromatic Kashmiri style dum aloo with saffron notes', 140.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Dam Aalu Kashmiri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Aalu Special' LIMIT 1), 'Green Peas Aalu', 'Potatoes and green peas in homestyle curry', 80.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Green Peas Aalu');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Aalu Special' LIMIT 1), 'Aalu Mutter', 'Classic aloo mutter curry', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Aalu Mutter');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Aalu Special' LIMIT 1), 'Aalu Palak', 'Potatoes cooked in fresh spiced spinach gravy', 75.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Aalu Palak');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Aalu Special' LIMIT 1), 'Aalu Jeera', 'Cumin-tempered golden pan-roasted potatoes', 80.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Aalu Jeera');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Aalu Special' LIMIT 1), 'Aalu Gobi', 'Cauliflower and potatoes tossed in Indian spices', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Aalu Gobi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Dal Special' LIMIT 1), 'Dal Kolhapuri', 'Spicy Kolhapuri tempered yellow lentils', 80.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Dal Kolhapuri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Dal Special' LIMIT 1), 'Dal Fry', 'Yellow lentils tempered with onion, tomato and cumin', 70.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Dal Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Dal Special' LIMIT 1), 'Dal Tadka', 'Rich ghee-garlic and red chilli tempered dal', 80.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Dal Tadka');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Dal Special' LIMIT 1), 'Dal Butter', 'Creamy butter-enriched yellow dal', 80.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Dal Butter');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Dal Special' LIMIT 1), 'Dal Kashmiri', 'Mildly spiced aromatic Kashmiri dal', 85.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Dal Kashmiri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Dal Special' LIMIT 1), 'Dal Makhani', 'Slow-simmered black lentils with butter and cream', 85.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Dal Makhani');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Sankalp Special Handi Full', 'Family-size Sankalp signature handi', 250.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Sankalp Special Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Sankalp Special Handi Half', 'Half portion Sankalp signature handi', 140.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Sankalp Special Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Kaju Kari Handi Full', 'Full handi of rich roasted cashew curry', 350.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Kaju Kari Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Kaju Kari Handi Half', 'Half handi of rich roasted cashew curry', 200.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Kaju Kari Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Paneer Handi Full', 'Full handi of spiced paneer curry', 330.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Paneer Handi Half', 'Half handi of spiced paneer curry', 180.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Veg Kolhapuri Handi Full', 'Full handi of spicy Veg Kolhapuri', 250.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Kolhapuri Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Veg Kolhapuri Handi Half', 'Half handi of spicy Veg Kolhapuri', 140.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Kolhapuri Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Mushroom Handi Full', 'Full handi of fresh mushroom masala', 330.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mushroom Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Mushroom Handi Half', 'Half handi of fresh mushroom masala', 180.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mushroom Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Green Peas Handi Full', 'Full handi of spiced green peas curry', 240.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Green Peas Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Green Peas Handi Half', 'Half handi of spiced green peas curry', 120.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Green Peas Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Akkha Masur Handi Full', 'Full handi of Kolhapuri akkha masoor', 240.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Akkha Masur Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Akkha Masur Handi Half', 'Half handi of Kolhapuri akkha masoor', 120.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Akkha Masur Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Paneer Tikka Handi Full', 'Full handi of smoky paneer tikka masala', 350.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Tikka Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Paneer Tikka Handi Half', 'Half handi of smoky paneer tikka masala', 180.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Tikka Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Alu Mutter Handi Full', 'Full handi of homestyle aloo mutter', 240.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Alu Mutter Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Alu Mutter Handi Half', 'Half handi of homestyle aloo mutter', 120.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Alu Mutter Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Kaju Paneer Handi Full', 'Full handi of cashews and paneer in rich gravy', 380.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Kaju Paneer Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Kaju Paneer Handi Half', 'Half handi of cashews and paneer in rich gravy', 240.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Kaju Paneer Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Rajma Handi Full', 'Full handi of Punjabi rajma masala', 240.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Rajma Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Rajma Handi Half', 'Half handi of Punjabi rajma masala', 120.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Rajma Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Dal Tadka Full', 'Full handi of ghee-garlic dal tadka', 240.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Dal Tadka Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Dal Tadka Half', 'Half handi of ghee-garlic dal tadka', 120.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Dal Tadka Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Veg Handi Full', 'Full handi of assorted seasonal vegetables', 240.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Handi' LIMIT 1), 'Veg Handi Half', 'Half handi of assorted seasonal vegetables', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Snacks' LIMIT 1), 'Veg Harbhara Kabab', 'Crispy spinach and green pea patties', 140.00, 'images/menu/Pakoda.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Harbhara Kabab');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Snacks' LIMIT 1), 'Veg Pakoda', 'Crispy assorted vegetable fritters', 90.00, 'images/menu/Pakoda.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Pakoda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Snacks' LIMIT 1), 'Finger Chips', 'Golden crispy salted French fries', 50.00, 'images/menu/French_Fries.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Finger Chips');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Snacks' LIMIT 1), 'Chhole Bhatura', 'Fluffy bhaturas served with spicy Punjabi chole', 100.00, 'images/menu/Samosa.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Chhole Bhatura');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Snacks' LIMIT 1), 'Paneer Pakoda', 'Spiced cottage cheese fritters in gram flour batter', 100.00, 'images/menu/Pakoda.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Pakoda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Snacks' LIMIT 1), 'Wafers', 'Crispy salted potato wafers', 10.00, 'images/menu/French_Fries.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Wafers');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Snacks' LIMIT 1), 'Khichadi', 'Light spiced sabudana / comforting khichdi', 50.00, 'images/menu/Upma.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Khichadi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Soup' LIMIT 1), 'Tomato Soup', 'Classic creamy roasted tomato soup with croutons', 60.00, 'images/menu/Tomato_Soup.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Tomato Soup');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Soup' LIMIT 1), 'Veg Manchow Soup', 'Spicy Indo-Chinese soup topped with crispy noodles', 60.00, 'images/menu/Sweet_Corn_Soup.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Manchow Soup');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice' LIMIT 1), 'Plain Rice Full', 'Steamed fluffy basmati rice (Full)', 70.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Plain Rice Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice' LIMIT 1), 'Plain Rice Half', 'Steamed fluffy basmati rice (Half)', 50.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Plain Rice Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice' LIMIT 1), 'Jeera Rice Full', 'Cumin-tempered fragrant basmati rice (Full)', 80.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Jeera Rice Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice' LIMIT 1), 'Jeera Rice Half', 'Cumin-tempered fragrant basmati rice (Half)', 60.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Jeera Rice Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice' LIMIT 1), 'Steam Rice', 'Aromatic steamed rice', 80.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Steam Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice' LIMIT 1), 'Masala Rice', 'Spiced Maharashtrian masala bhat', 120.00, 'images/menu/Veg_Biryani.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Masala Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice' LIMIT 1), 'Veg Biryani', 'Fragrant basmati rice layered with spiced vegetables', 140.00, 'images/menu/Veg_Biryani.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Biryani');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice' LIMIT 1), 'Veg Pulav', 'Mildly spiced vegetable pulao', 140.00, 'images/menu/Jain_Biryani.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Pulav');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice' LIMIT 1), 'Green Peas Pulav', 'Aromatic rice cooked with sweet green peas', 130.00, 'images/menu/Jain_Biryani.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Green Peas Pulav');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice' LIMIT 1), 'Veg Fry Rice', 'Wok-tossed vegetable fried rice', 130.00, 'images/menu/Veg_Hakka_Noodles.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Veg Fry Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice' LIMIT 1), 'Shezwan Fry Rice', 'Spicy Schezwan vegetable fried rice', 140.00, 'images/menu/Veg_Hakka_Noodles.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Shezwan Fry Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice' LIMIT 1), 'Dal Khichadi', 'Comforting ghee-tempered dal and rice khichdi', 130.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Dal Khichadi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Rice' LIMIT 1), 'Mushroom Fry Rice', 'Wok-tossed fried rice with fresh mushrooms', 140.00, 'images/menu/Veg_Biryani.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mushroom Fry Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Roti' LIMIT 1), 'Roti', 'Fresh tandoori wheat roti', 10.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Roti');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Roti' LIMIT 1), 'Butter Roti', 'Tandoori wheat roti brushed with butter', 15.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Butter Roti');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Roti' LIMIT 1), 'Parotha', 'Layered flaky whole-wheat paratha', 30.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Roti' LIMIT 1), 'Butter Parotha', 'Flaky layered paratha with butter', 35.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Butter Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Roti' LIMIT 1), 'Masala Parotha', 'Spiced crispy layered paratha', 50.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Masala Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Roti' LIMIT 1), 'Aalu Parotha', 'Stuffed spiced potato paratha', 50.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Aalu Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Roti' LIMIT 1), 'Paneer Parotha', 'Stuffed spiced cottage cheese paratha', 70.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Paneer Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Roti' LIMIT 1), 'Naan', 'Soft tandoor-baked naan', 30.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Naan');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Roti' LIMIT 1), 'Butter Naan', 'Soft tandoori naan glazed with butter', 35.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Butter Naan');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Roti' LIMIT 1), 'Kashmiri Naan', 'Sweet dry-fruit stuffed Kashmiri naan', 40.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Kashmiri Naan');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Roti' LIMIT 1), 'Kulcha', 'Soft tandoor-baked kulcha', 40.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Kulcha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Papad' LIMIT 1), 'Roast Papad', 'Crisp flame-roasted urad papad', 20.00, 'images/menu/Masala_Papad.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Roast Papad');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Papad' LIMIT 1), 'Fry Papad', 'Crispy golden fried papad', 15.00, 'images/menu/Masala_Papad.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Fry Papad');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Papad' LIMIT 1), 'Masala Papad', 'Crispy papad topped with spiced onion, tomato and sev', 35.00, 'images/menu/Masala_Papad.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Masala Papad');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Papad' LIMIT 1), 'Papad Burji', 'Crushed spiced papad tossed with onions and chillies', 50.00, 'images/menu/Masala_Papad.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Papad Burji');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Cold Drinks' LIMIT 1), 'Thums Up', 'Chilled Thums Up soft drink', 20.00, 'images/menu/Blue_Lagoon.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Thums Up');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Cold Drinks' LIMIT 1), 'Limca', 'Chilled lemon-lime Limca', 20.00, 'images/menu/Virgin_Mojito.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Limca');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Cold Drinks' LIMIT 1), 'Maaza', 'Chilled mango drink', 20.00, 'images/menu/Mango_Juice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Maaza');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Cold Drinks' LIMIT 1), 'Soda', 'Chilled sparkling soda', 15.00, 'images/menu/Virgin_Mojito.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Soda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Cold Drinks' LIMIT 1), 'Jeera Soda', 'Refreshing cumin-spiced jeera soda', 20.00, 'images/menu/Virgin_Mojito.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Jeera Soda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Cold Drinks' LIMIT 1), 'Lassi', 'Creamy chilled sweet Punjabi lassi', 20.00, 'images/menu/Lassi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Lassi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Cold Drinks' LIMIT 1), 'Tak', 'Refreshing spiced chaas / buttermilk', 20.00, 'images/menu/Lassi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Tak');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 1, (SELECT category_id FROM category WHERE restaurant_id = 1 AND category_name = 'Cold Drinks' LIMIT 1), 'Mineral Water', 'Packaged 1L mineral drinking water', 20.00, 'images/menu/Blue_Lagoon.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 1 AND item_name = 'Mineral Water');

-- -----------------------------------------------------
-- OUTLET 2 CATEGORIES
-- -----------------------------------------------------
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Special Dishes', 'Sankalpa Pure Veg chef special curries', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Special Dishes');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Kaju Special', 'Rich cashew nut gravies and specials', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Kaju Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Mushroom Special', 'Fresh mushroom curries and masala dishes', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Mushroom Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Paneer Special', 'Signature cottage cheese gravies and tikkas', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Paneer Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Punjabi Dishes', 'Traditional Punjabi and Maharashtrian sabzis', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Chinese', 'Indo-Chinese starters and noodles', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Chinese');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Sweet Bhaji', 'Mildly spiced creamy and sweet curries', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Sweet Bhaji');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Aalu Special', 'Classic potato gravies and dum aalu', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Aalu Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Dal Special', 'Homestyle and restaurant-style lentils', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Dal Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Handi', 'Full and half handi clay-pot specials', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Handi');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Snacks', 'Hot crispy snacks and quick bites', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Snacks');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Soup', 'Freshly prepared hot soups', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Soup');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Rice', 'Steamed rice, jeera rice, biryani and fried rice', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Rice');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Roti', 'Fresh tandoori rotis, naans and parathas', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Roti');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Papad', 'Roasted, fried and masala papad', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Papad');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 2, 'Cold Drinks', 'Soft drinks, lassi, tak and mineral water', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 2 AND category_name = 'Cold Drinks');

-- OUTLET 2 MENU ITEMS (171 dishes)
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Tawa', 'Mixed vegetables tossed on hot tawa with spices', 150.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Tawa');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Kadhai', 'Seasonal veggies cooked in aromatic kadhai masala', 140.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Kadhai');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Maharani', 'Rich creamy mixed vegetable royal curry', 150.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Maharani');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Haidrabadi', 'Spicy Hyderabadi style green herb vegetable curry', 150.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Haidrabadi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Jaipuri', 'Jaipuri style mixed veg gravy with crisp papad', 150.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Jaipuri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Makkhanwala', 'Mixed vegetables simmered in rich buttery tomato gravy', 150.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Makkhanwala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Maratha', 'Spicy Maharashtrian style vegetable kofta curry', 130.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Maratha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Bhuna', 'Slow-roasted spiced vegetable masala', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Bhuna');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Dilruba', 'Chef special rich red & golden veg curry', 150.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Dilruba');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Banjara', 'Smoky dhaba-style spiced vegetable curry', 150.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Banjara');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Sundari', 'Special Sankalpa signature veg preparation', 150.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Sundari');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Kaju Special' LIMIT 1), 'Kaju Kari', 'Roasted cashews simmered in spiced onion-tomato curry', 130.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Kaju Kari');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Kaju Special' LIMIT 1), 'Kaju Masala', 'Rich cashew nut masala with aromatic Indian spices', 130.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Kaju Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Kaju Special' LIMIT 1), 'Kaju Paneer', 'Cashews and soft paneer cubes in creamy gravy', 160.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Kaju Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Kaju Special' LIMIT 1), 'Kaju Fry', 'Crispy golden butter-roasted salted cashews', 250.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Kaju Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Masala', 'Fresh button mushrooms in spiced onion-tomato gravy', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mushroom Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Fry', 'Crispy sauteed mushrooms with black pepper and herbs', 160.00, 'images/menu/Veg_Manchurian.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mushroom Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Babycorn', 'Mushrooms and tender babycorn in rich masala', 180.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mushroom Babycorn');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Paneer Masala', 'Mushrooms and paneer cooked in thick masala gravy', 150.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mushroom Paneer Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Kaju Masala', 'Mushrooms and cashews in creamy spiced gravy', 150.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mushroom Kaju Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Butter Masala', 'Mushrooms simmered in velvety butter tomato gravy', 140.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mushroom Butter Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Tikka Masala', 'Char-grilled mushrooms in smoky tikka masala', 140.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mushroom Tikka Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Masala', 'Classic cottage cheese cubes in spiced masala gravy', 120.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Butter Masala', 'Soft paneer in rich creamy tomato butter gravy', 130.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Butter Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Tikka Masala', 'Tandoori paneer tikka in smoky spiced gravy', 130.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Tikka Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Burji', 'Scrambled paneer cooked with onions, tomatoes and spices', 140.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Burji');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Kadhai', 'Paneer and bell peppers tossed in kadhai masala', 150.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Kadhai');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Kopta', 'Soft paneer dumplings in rich creamy gravy', 150.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Kopta');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Paneer Special' LIMIT 1), 'Mutter Paneer', 'Green peas and paneer cubes in homestyle tomato gravy', 130.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mutter Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Paneer Special' LIMIT 1), 'Green Peas Paneer', 'Fresh green peas and cottage cheese masala', 130.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Green Peas Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Pasanda', 'Stuffed paneer triangles in rich Mughlai gravy', 180.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Pasanda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Paneer Special' LIMIT 1), 'Shahi Paneer', 'Royal creamy cashew-saffron paneer curry', 140.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Shahi Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Anguri', 'Mini paneer balls in aromatic rich gravy', 180.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Anguri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Paneer Special' LIMIT 1), 'Palak Paneer', 'Fresh spinach puree cooked with soft paneer cubes', 130.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Palak Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Shev Bhaji', 'Traditional Maharashtrian spicy sev curry', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Shev Bhaji');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Green Peas', 'Fresh green peas cooked in spiced gravy', 70.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Green Peas');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Green Peas Fry', 'Tossed green peas with onion and Indian spices', 80.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Green Peas Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Green Peas Dry', 'Dry spiced green peas preparation', 80.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Green Peas Dry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Chana Masala', 'Chickpeas simmered in tangy Punjabi masala', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Chana Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Chana Masala Fry', 'Fried spiced chickpeas with onion-tomato tadka', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Chana Masala Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Chana Masala Dry', 'Dry roasted spicy chana masala', 80.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Chana Masala Dry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Baingan Masala', 'Stuffed baby brinjals in spiced peanut-onion gravy', 70.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Baingan Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Baingan Bharta', 'Smoky roasted mashed eggplant with spices', 80.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Baingan Bharta');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Shimla Masala', 'Capsicum cooked in flavorful masala gravy', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Shimla Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Shimla Fry', 'Crispy sauteed spiced capsicum', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Shimla Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Stuff Shimla', 'Capsicum stuffed with spiced potato-paneer filling', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Stuff Shimla');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Plain Palak', 'Smoothly tempered spinach curry', 70.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Plain Palak');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Soyabin Palak', 'Protein-rich soya chunks in spinach gravy', 70.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Soyabin Palak');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Bhendi Fry', 'Crispy okra tossed with turmeric and spices', 75.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Bhendi Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Bhendi Masala', 'Okra cooked in rich onion-tomato masala', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Bhendi Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Soyabin Masala', 'Soya chunks simmered in spicy masala gravy', 70.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Soyabin Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Soyabin Fry', 'Pan-tossed spiced soya chunks', 75.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Soyabin Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Soyabin Dry', 'Dry roasted spicy soya preparation', 75.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Soyabin Dry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Mataki Masala', 'Sprouted moth beans in traditional Maharashtrian masala', 70.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mataki Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Mataki Fry', 'Dry spiced sprouted matki usal', 75.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mataki Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Rajma Masala', 'Red kidney beans cooked in thick Punjabi gravy', 70.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Rajma Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Rajma Fry', 'Tempered spiced rajma masala', 75.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Rajma Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Akkha Masur Masala', 'Whole brown lentils in spicy Kolhapuri masala', 70.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Akkha Masur Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Akkha Masur Fry', 'Rich garlic-tempered whole masoor fry', 75.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Akkha Masur Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Patiyala', 'Stuffed papad rolls served in rich Punjabi gravy', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Patiyala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Butter Ball', 'Crispy veg balls in creamy butter masala', 130.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Butter Ball');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Anguri', 'Spiced vegetable dumplings in rich gravy', 140.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Anguri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Tiranga', 'Three-layered tricolor signature veg delicacy', 150.00, 'images/menu/Chef_Special_Thali.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Tiranga');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Maharaja', 'Royal mixed vegetable curry with rich spices', 110.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Maharaja');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Kolhapuri', 'Fiery Kolhapuri spiced mixed vegetables', 85.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Kolhapuri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Mix Veg', 'Assorted seasonal vegetables in homestyle gravy', 80.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mix Veg');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Khurma', 'Mildly spiced creamy vegetable korma', 110.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Khurma');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Singapuri', 'Sweet-spicy Singapuri style vegetable curry', 120.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Singapuri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Green Garden', 'Fresh green herbs and vegetable specialty', 140.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Green Garden');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Kopta', 'Crispy vegetable koftas in rich gravy', 120.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Kopta');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Hariyali', 'Vegetables cooked in vibrant spinach-coriander gravy', 110.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Hariyali');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Mumtaj', 'Rich Mughlai style mixed vegetable curry', 110.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Mumtaj');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Lengcha', 'Special cylindrical veg rolls in spiced gravy', 110.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Lengcha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Khima', 'Finely minced vegetables cooked in aromatic spices', 110.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Khima');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Khandala', 'Special spicy hill-station style veg masala', 110.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Khandala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Tamatar Chatani', 'Tangy spiced tomato chutney curry', 90.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Tamatar Chatani');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Stuff Tamatar', 'Whole tomatoes stuffed with spiced potato-paneer', 120.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Stuff Tamatar');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Chinese' LIMIT 1), 'Veg Manchurian', 'Crispy vegetable balls tossed in tangy Manchurian sauce', 80.00, 'images/menu/Veg_Manchurian.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Manchurian');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Chinese' LIMIT 1), 'Soyabin Chilli', 'Crispy soya chunks tossed with chillies and soy sauce', 85.00, 'images/menu/Veg_Manchurian.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Soyabin Chilli');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Chinese' LIMIT 1), 'Paneer Chilli', 'Wok-tossed paneer cubes with peppers and chilli sauce', 110.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Chilli');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Chinese' LIMIT 1), 'Mushroom Chilli', 'Crispy mushrooms tossed in spicy garlic chilli sauce', 110.00, 'images/menu/Veg_Manchurian.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mushroom Chilli');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Chinese' LIMIT 1), 'Veg Hakka Noodles', 'Stir-fried noodles with crunchy vegetables', 100.00, 'images/menu/Veg_Hakka_Noodles.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Hakka Noodles');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Sweet Bhaji' LIMIT 1), 'Malai Kopta', 'Melt-in-mouth malai koftas in creamy sweet gravy', 120.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Malai Kopta');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Sweet Bhaji' LIMIT 1), 'Navratna Kurma', 'Nine-gem mixed vegetables and fruits in creamy korma', 120.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Navratna Kurma');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Sweet Bhaji' LIMIT 1), 'Paneer Shan Savera', 'Spinach and paneer koftas in rich golden gravy', 140.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Shan Savera');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Sweet Bhaji' LIMIT 1), 'Paneer Pasanda (Sweet)', 'Rich creamy stuffed paneer pasanda', 140.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Pasanda (Sweet)');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Aalu Special' LIMIT 1), 'Dam Aalu', 'Baby potatoes simmered in spiced yogurt-tomato gravy', 120.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Dam Aalu');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Aalu Special' LIMIT 1), 'Dam Aalu Panjabi', 'Rich Punjabi style stuffed dum aloo', 130.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Dam Aalu Panjabi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Aalu Special' LIMIT 1), 'Dam Aalu Kashmiri', 'Aromatic Kashmiri style dum aloo with saffron notes', 140.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Dam Aalu Kashmiri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Aalu Special' LIMIT 1), 'Green Peas Aalu', 'Potatoes and green peas in homestyle curry', 80.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Green Peas Aalu');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Aalu Special' LIMIT 1), 'Aalu Mutter', 'Classic aloo mutter curry', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Aalu Mutter');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Aalu Special' LIMIT 1), 'Aalu Palak', 'Potatoes cooked in fresh spiced spinach gravy', 75.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Aalu Palak');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Aalu Special' LIMIT 1), 'Aalu Jeera', 'Cumin-tempered golden pan-roasted potatoes', 80.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Aalu Jeera');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Aalu Special' LIMIT 1), 'Aalu Gobi', 'Cauliflower and potatoes tossed in Indian spices', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Aalu Gobi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Dal Special' LIMIT 1), 'Dal Kolhapuri', 'Spicy Kolhapuri tempered yellow lentils', 80.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Dal Kolhapuri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Dal Special' LIMIT 1), 'Dal Fry', 'Yellow lentils tempered with onion, tomato and cumin', 70.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Dal Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Dal Special' LIMIT 1), 'Dal Tadka', 'Rich ghee-garlic and red chilli tempered dal', 80.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Dal Tadka');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Dal Special' LIMIT 1), 'Dal Butter', 'Creamy butter-enriched yellow dal', 80.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Dal Butter');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Dal Special' LIMIT 1), 'Dal Kashmiri', 'Mildly spiced aromatic Kashmiri dal', 85.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Dal Kashmiri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Dal Special' LIMIT 1), 'Dal Makhani', 'Slow-simmered black lentils with butter and cream', 85.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Dal Makhani');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Sankalp Special Handi Full', 'Family-size Sankalp signature handi', 250.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Sankalp Special Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Sankalp Special Handi Half', 'Half portion Sankalp signature handi', 140.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Sankalp Special Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Kaju Kari Handi Full', 'Full handi of rich roasted cashew curry', 350.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Kaju Kari Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Kaju Kari Handi Half', 'Half handi of rich roasted cashew curry', 200.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Kaju Kari Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Paneer Handi Full', 'Full handi of spiced paneer curry', 330.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Paneer Handi Half', 'Half handi of spiced paneer curry', 180.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Veg Kolhapuri Handi Full', 'Full handi of spicy Veg Kolhapuri', 250.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Kolhapuri Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Veg Kolhapuri Handi Half', 'Half handi of spicy Veg Kolhapuri', 140.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Kolhapuri Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Mushroom Handi Full', 'Full handi of fresh mushroom masala', 330.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mushroom Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Mushroom Handi Half', 'Half handi of fresh mushroom masala', 180.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mushroom Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Green Peas Handi Full', 'Full handi of spiced green peas curry', 240.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Green Peas Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Green Peas Handi Half', 'Half handi of spiced green peas curry', 120.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Green Peas Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Akkha Masur Handi Full', 'Full handi of Kolhapuri akkha masoor', 240.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Akkha Masur Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Akkha Masur Handi Half', 'Half handi of Kolhapuri akkha masoor', 120.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Akkha Masur Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Paneer Tikka Handi Full', 'Full handi of smoky paneer tikka masala', 350.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Tikka Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Paneer Tikka Handi Half', 'Half handi of smoky paneer tikka masala', 180.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Tikka Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Alu Mutter Handi Full', 'Full handi of homestyle aloo mutter', 240.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Alu Mutter Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Alu Mutter Handi Half', 'Half handi of homestyle aloo mutter', 120.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Alu Mutter Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Kaju Paneer Handi Full', 'Full handi of cashews and paneer in rich gravy', 380.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Kaju Paneer Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Kaju Paneer Handi Half', 'Half handi of cashews and paneer in rich gravy', 240.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Kaju Paneer Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Rajma Handi Full', 'Full handi of Punjabi rajma masala', 240.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Rajma Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Rajma Handi Half', 'Half handi of Punjabi rajma masala', 120.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Rajma Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Dal Tadka Full', 'Full handi of ghee-garlic dal tadka', 240.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Dal Tadka Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Dal Tadka Half', 'Half handi of ghee-garlic dal tadka', 120.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Dal Tadka Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Veg Handi Full', 'Full handi of assorted seasonal vegetables', 240.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Handi' LIMIT 1), 'Veg Handi Half', 'Half handi of assorted seasonal vegetables', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Snacks' LIMIT 1), 'Veg Harbhara Kabab', 'Crispy spinach and green pea patties', 140.00, 'images/menu/Pakoda.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Harbhara Kabab');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Snacks' LIMIT 1), 'Veg Pakoda', 'Crispy assorted vegetable fritters', 90.00, 'images/menu/Pakoda.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Pakoda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Snacks' LIMIT 1), 'Finger Chips', 'Golden crispy salted French fries', 50.00, 'images/menu/French_Fries.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Finger Chips');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Snacks' LIMIT 1), 'Chhole Bhatura', 'Fluffy bhaturas served with spicy Punjabi chole', 100.00, 'images/menu/Samosa.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Chhole Bhatura');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Snacks' LIMIT 1), 'Paneer Pakoda', 'Spiced cottage cheese fritters in gram flour batter', 100.00, 'images/menu/Pakoda.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Pakoda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Snacks' LIMIT 1), 'Wafers', 'Crispy salted potato wafers', 10.00, 'images/menu/French_Fries.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Wafers');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Snacks' LIMIT 1), 'Khichadi', 'Light spiced sabudana / comforting khichdi', 50.00, 'images/menu/Upma.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Khichadi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Soup' LIMIT 1), 'Tomato Soup', 'Classic creamy roasted tomato soup with croutons', 60.00, 'images/menu/Tomato_Soup.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Tomato Soup');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Soup' LIMIT 1), 'Veg Manchow Soup', 'Spicy Indo-Chinese soup topped with crispy noodles', 60.00, 'images/menu/Sweet_Corn_Soup.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Manchow Soup');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Rice' LIMIT 1), 'Plain Rice Full', 'Steamed fluffy basmati rice (Full)', 70.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Plain Rice Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Rice' LIMIT 1), 'Plain Rice Half', 'Steamed fluffy basmati rice (Half)', 50.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Plain Rice Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Rice' LIMIT 1), 'Jeera Rice Full', 'Cumin-tempered fragrant basmati rice (Full)', 80.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Jeera Rice Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Rice' LIMIT 1), 'Jeera Rice Half', 'Cumin-tempered fragrant basmati rice (Half)', 60.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Jeera Rice Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Rice' LIMIT 1), 'Steam Rice', 'Aromatic steamed rice', 80.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Steam Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Rice' LIMIT 1), 'Masala Rice', 'Spiced Maharashtrian masala bhat', 120.00, 'images/menu/Veg_Biryani.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Masala Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Rice' LIMIT 1), 'Veg Biryani', 'Fragrant basmati rice layered with spiced vegetables', 140.00, 'images/menu/Veg_Biryani.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Biryani');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Rice' LIMIT 1), 'Veg Pulav', 'Mildly spiced vegetable pulao', 140.00, 'images/menu/Jain_Biryani.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Pulav');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Rice' LIMIT 1), 'Green Peas Pulav', 'Aromatic rice cooked with sweet green peas', 130.00, 'images/menu/Jain_Biryani.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Green Peas Pulav');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Rice' LIMIT 1), 'Veg Fry Rice', 'Wok-tossed vegetable fried rice', 130.00, 'images/menu/Veg_Hakka_Noodles.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Veg Fry Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Rice' LIMIT 1), 'Shezwan Fry Rice', 'Spicy Schezwan vegetable fried rice', 140.00, 'images/menu/Veg_Hakka_Noodles.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Shezwan Fry Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Rice' LIMIT 1), 'Dal Khichadi', 'Comforting ghee-tempered dal and rice khichdi', 130.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Dal Khichadi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Rice' LIMIT 1), 'Mushroom Fry Rice', 'Wok-tossed fried rice with fresh mushrooms', 140.00, 'images/menu/Veg_Biryani.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mushroom Fry Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Roti' LIMIT 1), 'Roti', 'Fresh tandoori wheat roti', 10.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Roti');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Roti' LIMIT 1), 'Butter Roti', 'Tandoori wheat roti brushed with butter', 15.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Butter Roti');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Roti' LIMIT 1), 'Parotha', 'Layered flaky whole-wheat paratha', 30.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Roti' LIMIT 1), 'Butter Parotha', 'Flaky layered paratha with butter', 35.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Butter Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Roti' LIMIT 1), 'Masala Parotha', 'Spiced crispy layered paratha', 50.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Masala Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Roti' LIMIT 1), 'Aalu Parotha', 'Stuffed spiced potato paratha', 50.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Aalu Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Roti' LIMIT 1), 'Paneer Parotha', 'Stuffed spiced cottage cheese paratha', 70.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Paneer Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Roti' LIMIT 1), 'Naan', 'Soft tandoor-baked naan', 30.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Naan');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Roti' LIMIT 1), 'Butter Naan', 'Soft tandoori naan glazed with butter', 35.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Butter Naan');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Roti' LIMIT 1), 'Kashmiri Naan', 'Sweet dry-fruit stuffed Kashmiri naan', 40.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Kashmiri Naan');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Roti' LIMIT 1), 'Kulcha', 'Soft tandoor-baked kulcha', 40.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Kulcha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Papad' LIMIT 1), 'Roast Papad', 'Crisp flame-roasted urad papad', 20.00, 'images/menu/Masala_Papad.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Roast Papad');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Papad' LIMIT 1), 'Fry Papad', 'Crispy golden fried papad', 15.00, 'images/menu/Masala_Papad.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Fry Papad');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Papad' LIMIT 1), 'Masala Papad', 'Crispy papad topped with spiced onion, tomato and sev', 35.00, 'images/menu/Masala_Papad.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Masala Papad');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Papad' LIMIT 1), 'Papad Burji', 'Crushed spiced papad tossed with onions and chillies', 50.00, 'images/menu/Masala_Papad.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Papad Burji');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Cold Drinks' LIMIT 1), 'Thums Up', 'Chilled Thums Up soft drink', 20.00, 'images/menu/Blue_Lagoon.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Thums Up');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Cold Drinks' LIMIT 1), 'Limca', 'Chilled lemon-lime Limca', 20.00, 'images/menu/Virgin_Mojito.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Limca');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Cold Drinks' LIMIT 1), 'Maaza', 'Chilled mango drink', 20.00, 'images/menu/Mango_Juice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Maaza');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Cold Drinks' LIMIT 1), 'Soda', 'Chilled sparkling soda', 15.00, 'images/menu/Virgin_Mojito.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Soda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Cold Drinks' LIMIT 1), 'Jeera Soda', 'Refreshing cumin-spiced jeera soda', 20.00, 'images/menu/Virgin_Mojito.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Jeera Soda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Cold Drinks' LIMIT 1), 'Lassi', 'Creamy chilled sweet Punjabi lassi', 20.00, 'images/menu/Lassi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Lassi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Cold Drinks' LIMIT 1), 'Tak', 'Refreshing spiced chaas / buttermilk', 20.00, 'images/menu/Lassi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Tak');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 2, (SELECT category_id FROM category WHERE restaurant_id = 2 AND category_name = 'Cold Drinks' LIMIT 1), 'Mineral Water', 'Packaged 1L mineral drinking water', 20.00, 'images/menu/Blue_Lagoon.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 2 AND item_name = 'Mineral Water');

-- -----------------------------------------------------
-- OUTLET 3 CATEGORIES
-- -----------------------------------------------------
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Special Dishes', 'Sankalpa Pure Veg chef special curries', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Special Dishes');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Kaju Special', 'Rich cashew nut gravies and specials', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Kaju Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Mushroom Special', 'Fresh mushroom curries and masala dishes', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Mushroom Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Paneer Special', 'Signature cottage cheese gravies and tikkas', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Paneer Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Punjabi Dishes', 'Traditional Punjabi and Maharashtrian sabzis', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Chinese', 'Indo-Chinese starters and noodles', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Chinese');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Sweet Bhaji', 'Mildly spiced creamy and sweet curries', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Sweet Bhaji');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Aalu Special', 'Classic potato gravies and dum aalu', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Aalu Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Dal Special', 'Homestyle and restaurant-style lentils', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Dal Special');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Handi', 'Full and half handi clay-pot specials', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Handi');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Snacks', 'Hot crispy snacks and quick bites', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Snacks');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Soup', 'Freshly prepared hot soups', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Soup');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Rice', 'Steamed rice, jeera rice, biryani and fried rice', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Rice');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Roti', 'Fresh tandoori rotis, naans and parathas', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Roti');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Papad', 'Roasted, fried and masala papad', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Papad');
INSERT INTO category (restaurant_id, category_name, description, status)
SELECT 3, 'Cold Drinks', 'Soft drinks, lassi, tak and mineral water', 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE restaurant_id = 3 AND category_name = 'Cold Drinks');

-- OUTLET 3 MENU ITEMS (171 dishes)
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Tawa', 'Mixed vegetables tossed on hot tawa with spices', 150.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Tawa');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Kadhai', 'Seasonal veggies cooked in aromatic kadhai masala', 140.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Kadhai');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Maharani', 'Rich creamy mixed vegetable royal curry', 150.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Maharani');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Haidrabadi', 'Spicy Hyderabadi style green herb vegetable curry', 150.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Haidrabadi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Jaipuri', 'Jaipuri style mixed veg gravy with crisp papad', 150.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Jaipuri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Makkhanwala', 'Mixed vegetables simmered in rich buttery tomato gravy', 150.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Makkhanwala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Maratha', 'Spicy Maharashtrian style vegetable kofta curry', 130.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Maratha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Bhuna', 'Slow-roasted spiced vegetable masala', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Bhuna');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Dilruba', 'Chef special rich red & golden veg curry', 150.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Dilruba');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Banjara', 'Smoky dhaba-style spiced vegetable curry', 150.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Banjara');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Special Dishes' LIMIT 1), 'Veg Sundari', 'Special Sankalpa signature veg preparation', 150.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Sundari');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Kaju Special' LIMIT 1), 'Kaju Kari', 'Roasted cashews simmered in spiced onion-tomato curry', 130.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Kaju Kari');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Kaju Special' LIMIT 1), 'Kaju Masala', 'Rich cashew nut masala with aromatic Indian spices', 130.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Kaju Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Kaju Special' LIMIT 1), 'Kaju Paneer', 'Cashews and soft paneer cubes in creamy gravy', 160.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Kaju Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Kaju Special' LIMIT 1), 'Kaju Fry', 'Crispy golden butter-roasted salted cashews', 250.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Kaju Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Masala', 'Fresh button mushrooms in spiced onion-tomato gravy', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mushroom Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Fry', 'Crispy sauteed mushrooms with black pepper and herbs', 160.00, 'images/menu/Veg_Manchurian.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mushroom Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Babycorn', 'Mushrooms and tender babycorn in rich masala', 180.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mushroom Babycorn');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Paneer Masala', 'Mushrooms and paneer cooked in thick masala gravy', 150.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mushroom Paneer Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Kaju Masala', 'Mushrooms and cashews in creamy spiced gravy', 150.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mushroom Kaju Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Butter Masala', 'Mushrooms simmered in velvety butter tomato gravy', 140.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mushroom Butter Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Mushroom Special' LIMIT 1), 'Mushroom Tikka Masala', 'Char-grilled mushrooms in smoky tikka masala', 140.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mushroom Tikka Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Masala', 'Classic cottage cheese cubes in spiced masala gravy', 120.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Butter Masala', 'Soft paneer in rich creamy tomato butter gravy', 130.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Butter Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Tikka Masala', 'Tandoori paneer tikka in smoky spiced gravy', 130.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Tikka Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Burji', 'Scrambled paneer cooked with onions, tomatoes and spices', 140.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Burji');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Kadhai', 'Paneer and bell peppers tossed in kadhai masala', 150.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Kadhai');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Kopta', 'Soft paneer dumplings in rich creamy gravy', 150.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Kopta');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Paneer Special' LIMIT 1), 'Mutter Paneer', 'Green peas and paneer cubes in homestyle tomato gravy', 130.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mutter Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Paneer Special' LIMIT 1), 'Green Peas Paneer', 'Fresh green peas and cottage cheese masala', 130.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Green Peas Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Pasanda', 'Stuffed paneer triangles in rich Mughlai gravy', 180.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Pasanda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Paneer Special' LIMIT 1), 'Shahi Paneer', 'Royal creamy cashew-saffron paneer curry', 140.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Shahi Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Paneer Special' LIMIT 1), 'Paneer Anguri', 'Mini paneer balls in aromatic rich gravy', 180.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Anguri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Paneer Special' LIMIT 1), 'Palak Paneer', 'Fresh spinach puree cooked with soft paneer cubes', 130.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Palak Paneer');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Shev Bhaji', 'Traditional Maharashtrian spicy sev curry', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Shev Bhaji');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Green Peas', 'Fresh green peas cooked in spiced gravy', 70.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Green Peas');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Green Peas Fry', 'Tossed green peas with onion and Indian spices', 80.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Green Peas Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Green Peas Dry', 'Dry spiced green peas preparation', 80.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Green Peas Dry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Chana Masala', 'Chickpeas simmered in tangy Punjabi masala', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Chana Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Chana Masala Fry', 'Fried spiced chickpeas with onion-tomato tadka', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Chana Masala Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Chana Masala Dry', 'Dry roasted spicy chana masala', 80.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Chana Masala Dry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Baingan Masala', 'Stuffed baby brinjals in spiced peanut-onion gravy', 70.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Baingan Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Baingan Bharta', 'Smoky roasted mashed eggplant with spices', 80.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Baingan Bharta');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Shimla Masala', 'Capsicum cooked in flavorful masala gravy', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Shimla Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Shimla Fry', 'Crispy sauteed spiced capsicum', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Shimla Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Stuff Shimla', 'Capsicum stuffed with spiced potato-paneer filling', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Stuff Shimla');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Plain Palak', 'Smoothly tempered spinach curry', 70.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Plain Palak');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Soyabin Palak', 'Protein-rich soya chunks in spinach gravy', 70.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Soyabin Palak');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Bhendi Fry', 'Crispy okra tossed with turmeric and spices', 75.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Bhendi Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Bhendi Masala', 'Okra cooked in rich onion-tomato masala', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Bhendi Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Soyabin Masala', 'Soya chunks simmered in spicy masala gravy', 70.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Soyabin Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Soyabin Fry', 'Pan-tossed spiced soya chunks', 75.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Soyabin Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Soyabin Dry', 'Dry roasted spicy soya preparation', 75.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Soyabin Dry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Mataki Masala', 'Sprouted moth beans in traditional Maharashtrian masala', 70.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mataki Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Mataki Fry', 'Dry spiced sprouted matki usal', 75.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mataki Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Rajma Masala', 'Red kidney beans cooked in thick Punjabi gravy', 70.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Rajma Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Rajma Fry', 'Tempered spiced rajma masala', 75.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Rajma Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Akkha Masur Masala', 'Whole brown lentils in spicy Kolhapuri masala', 70.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Akkha Masur Masala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Akkha Masur Fry', 'Rich garlic-tempered whole masoor fry', 75.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Akkha Masur Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Patiyala', 'Stuffed papad rolls served in rich Punjabi gravy', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Patiyala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Butter Ball', 'Crispy veg balls in creamy butter masala', 130.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Butter Ball');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Anguri', 'Spiced vegetable dumplings in rich gravy', 140.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Anguri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Tiranga', 'Three-layered tricolor signature veg delicacy', 150.00, 'images/menu/Chef_Special_Thali.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Tiranga');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Maharaja', 'Royal mixed vegetable curry with rich spices', 110.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Maharaja');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Kolhapuri', 'Fiery Kolhapuri spiced mixed vegetables', 85.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Kolhapuri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Mix Veg', 'Assorted seasonal vegetables in homestyle gravy', 80.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mix Veg');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Khurma', 'Mildly spiced creamy vegetable korma', 110.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Khurma');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Singapuri', 'Sweet-spicy Singapuri style vegetable curry', 120.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Singapuri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Green Garden', 'Fresh green herbs and vegetable specialty', 140.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Green Garden');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Kopta', 'Crispy vegetable koftas in rich gravy', 120.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Kopta');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Hariyali', 'Vegetables cooked in vibrant spinach-coriander gravy', 110.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Hariyali');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Mumtaj', 'Rich Mughlai style mixed vegetable curry', 110.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Mumtaj');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Lengcha', 'Special cylindrical veg rolls in spiced gravy', 110.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Lengcha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Khima', 'Finely minced vegetables cooked in aromatic spices', 110.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Khima');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Veg Khandala', 'Special spicy hill-station style veg masala', 110.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Khandala');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Tamatar Chatani', 'Tangy spiced tomato chutney curry', 90.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Tamatar Chatani');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Punjabi Dishes' LIMIT 1), 'Stuff Tamatar', 'Whole tomatoes stuffed with spiced potato-paneer', 120.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Stuff Tamatar');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Chinese' LIMIT 1), 'Veg Manchurian', 'Crispy vegetable balls tossed in tangy Manchurian sauce', 80.00, 'images/menu/Veg_Manchurian.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Manchurian');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Chinese' LIMIT 1), 'Soyabin Chilli', 'Crispy soya chunks tossed with chillies and soy sauce', 85.00, 'images/menu/Veg_Manchurian.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Soyabin Chilli');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Chinese' LIMIT 1), 'Paneer Chilli', 'Wok-tossed paneer cubes with peppers and chilli sauce', 110.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Chilli');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Chinese' LIMIT 1), 'Mushroom Chilli', 'Crispy mushrooms tossed in spicy garlic chilli sauce', 110.00, 'images/menu/Veg_Manchurian.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mushroom Chilli');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Chinese' LIMIT 1), 'Veg Hakka Noodles', 'Stir-fried noodles with crunchy vegetables', 100.00, 'images/menu/Veg_Hakka_Noodles.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Hakka Noodles');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Sweet Bhaji' LIMIT 1), 'Malai Kopta', 'Melt-in-mouth malai koftas in creamy sweet gravy', 120.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Malai Kopta');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Sweet Bhaji' LIMIT 1), 'Navratna Kurma', 'Nine-gem mixed vegetables and fruits in creamy korma', 120.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Navratna Kurma');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Sweet Bhaji' LIMIT 1), 'Paneer Shan Savera', 'Spinach and paneer koftas in rich golden gravy', 140.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Shan Savera');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Sweet Bhaji' LIMIT 1), 'Paneer Pasanda (Sweet)', 'Rich creamy stuffed paneer pasanda', 140.00, 'images/menu/Jain_Paneer.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Pasanda (Sweet)');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Aalu Special' LIMIT 1), 'Dam Aalu', 'Baby potatoes simmered in spiced yogurt-tomato gravy', 120.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Dam Aalu');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Aalu Special' LIMIT 1), 'Dam Aalu Panjabi', 'Rich Punjabi style stuffed dum aloo', 130.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Dam Aalu Panjabi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Aalu Special' LIMIT 1), 'Dam Aalu Kashmiri', 'Aromatic Kashmiri style dum aloo with saffron notes', 140.00, 'images/menu/Malai_Kofta.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Dam Aalu Kashmiri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Aalu Special' LIMIT 1), 'Green Peas Aalu', 'Potatoes and green peas in homestyle curry', 80.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Green Peas Aalu');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Aalu Special' LIMIT 1), 'Aalu Mutter', 'Classic aloo mutter curry', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Aalu Mutter');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Aalu Special' LIMIT 1), 'Aalu Palak', 'Potatoes cooked in fresh spiced spinach gravy', 75.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Aalu Palak');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Aalu Special' LIMIT 1), 'Aalu Jeera', 'Cumin-tempered golden pan-roasted potatoes', 80.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Aalu Jeera');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Aalu Special' LIMIT 1), 'Aalu Gobi', 'Cauliflower and potatoes tossed in Indian spices', 70.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Aalu Gobi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Dal Special' LIMIT 1), 'Dal Kolhapuri', 'Spicy Kolhapuri tempered yellow lentils', 80.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Dal Kolhapuri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Dal Special' LIMIT 1), 'Dal Fry', 'Yellow lentils tempered with onion, tomato and cumin', 70.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Dal Fry');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Dal Special' LIMIT 1), 'Dal Tadka', 'Rich ghee-garlic and red chilli tempered dal', 80.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Dal Tadka');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Dal Special' LIMIT 1), 'Dal Butter', 'Creamy butter-enriched yellow dal', 80.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Dal Butter');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Dal Special' LIMIT 1), 'Dal Kashmiri', 'Mildly spiced aromatic Kashmiri dal', 85.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Dal Kashmiri');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Dal Special' LIMIT 1), 'Dal Makhani', 'Slow-simmered black lentils with butter and cream', 85.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Dal Makhani');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Sankalp Special Handi Full', 'Family-size Sankalp signature handi', 250.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Sankalp Special Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Sankalp Special Handi Half', 'Half portion Sankalp signature handi', 140.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Sankalp Special Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Kaju Kari Handi Full', 'Full handi of rich roasted cashew curry', 350.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Kaju Kari Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Kaju Kari Handi Half', 'Half handi of rich roasted cashew curry', 200.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Kaju Kari Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Paneer Handi Full', 'Full handi of spiced paneer curry', 330.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Paneer Handi Half', 'Half handi of spiced paneer curry', 180.00, 'images/menu/Paneer_Butter_Masala.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Veg Kolhapuri Handi Full', 'Full handi of spicy Veg Kolhapuri', 250.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Kolhapuri Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Veg Kolhapuri Handi Half', 'Half handi of spicy Veg Kolhapuri', 140.00, 'images/menu/Veg_Kolhapuri.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Kolhapuri Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Mushroom Handi Full', 'Full handi of fresh mushroom masala', 330.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mushroom Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Mushroom Handi Half', 'Half handi of fresh mushroom masala', 180.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mushroom Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Green Peas Handi Full', 'Full handi of spiced green peas curry', 240.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Green Peas Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Green Peas Handi Half', 'Half handi of spiced green peas curry', 120.00, 'images/menu/Palak_Paneer.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Green Peas Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Akkha Masur Handi Full', 'Full handi of Kolhapuri akkha masoor', 240.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Akkha Masur Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Akkha Masur Handi Half', 'Half handi of Kolhapuri akkha masoor', 120.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Akkha Masur Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Paneer Tikka Handi Full', 'Full handi of smoky paneer tikka masala', 350.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Tikka Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Paneer Tikka Handi Half', 'Half handi of smoky paneer tikka masala', 180.00, 'images/menu/Paneer_Tikka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Tikka Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Alu Mutter Handi Full', 'Full handi of homestyle aloo mutter', 240.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Alu Mutter Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Alu Mutter Handi Half', 'Half handi of homestyle aloo mutter', 120.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Alu Mutter Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Kaju Paneer Handi Full', 'Full handi of cashews and paneer in rich gravy', 380.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Kaju Paneer Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Kaju Paneer Handi Half', 'Half handi of cashews and paneer in rich gravy', 240.00, 'images/menu/Kaju_Curry.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Kaju Paneer Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Rajma Handi Full', 'Full handi of Punjabi rajma masala', 240.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Rajma Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Rajma Handi Half', 'Half handi of Punjabi rajma masala', 120.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Rajma Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Dal Tadka Full', 'Full handi of ghee-garlic dal tadka', 240.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Dal Tadka Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Dal Tadka Half', 'Half handi of ghee-garlic dal tadka', 120.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Dal Tadka Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Veg Handi Full', 'Full handi of assorted seasonal vegetables', 240.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Handi Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Handi' LIMIT 1), 'Veg Handi Half', 'Half handi of assorted seasonal vegetables', 130.00, 'images/menu/Veg_Handi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Handi Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Snacks' LIMIT 1), 'Veg Harbhara Kabab', 'Crispy spinach and green pea patties', 140.00, 'images/menu/Pakoda.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Harbhara Kabab');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Snacks' LIMIT 1), 'Veg Pakoda', 'Crispy assorted vegetable fritters', 90.00, 'images/menu/Pakoda.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Pakoda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Snacks' LIMIT 1), 'Finger Chips', 'Golden crispy salted French fries', 50.00, 'images/menu/French_Fries.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Finger Chips');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Snacks' LIMIT 1), 'Chhole Bhatura', 'Fluffy bhaturas served with spicy Punjabi chole', 100.00, 'images/menu/Samosa.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Chhole Bhatura');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Snacks' LIMIT 1), 'Paneer Pakoda', 'Spiced cottage cheese fritters in gram flour batter', 100.00, 'images/menu/Pakoda.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Pakoda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Snacks' LIMIT 1), 'Wafers', 'Crispy salted potato wafers', 10.00, 'images/menu/French_Fries.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Wafers');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Snacks' LIMIT 1), 'Khichadi', 'Light spiced sabudana / comforting khichdi', 50.00, 'images/menu/Upma.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Khichadi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Soup' LIMIT 1), 'Tomato Soup', 'Classic creamy roasted tomato soup with croutons', 60.00, 'images/menu/Tomato_Soup.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Tomato Soup');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Soup' LIMIT 1), 'Veg Manchow Soup', 'Spicy Indo-Chinese soup topped with crispy noodles', 60.00, 'images/menu/Sweet_Corn_Soup.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Manchow Soup');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Rice' LIMIT 1), 'Plain Rice Full', 'Steamed fluffy basmati rice (Full)', 70.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Plain Rice Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Rice' LIMIT 1), 'Plain Rice Half', 'Steamed fluffy basmati rice (Half)', 50.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Plain Rice Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Rice' LIMIT 1), 'Jeera Rice Full', 'Cumin-tempered fragrant basmati rice (Full)', 80.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Jeera Rice Full');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Rice' LIMIT 1), 'Jeera Rice Half', 'Cumin-tempered fragrant basmati rice (Half)', 60.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Jeera Rice Half');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Rice' LIMIT 1), 'Steam Rice', 'Aromatic steamed rice', 80.00, 'images/menu/Jeera_Rice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Steam Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Rice' LIMIT 1), 'Masala Rice', 'Spiced Maharashtrian masala bhat', 120.00, 'images/menu/Veg_Biryani.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Masala Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Rice' LIMIT 1), 'Veg Biryani', 'Fragrant basmati rice layered with spiced vegetables', 140.00, 'images/menu/Veg_Biryani.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Biryani');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Rice' LIMIT 1), 'Veg Pulav', 'Mildly spiced vegetable pulao', 140.00, 'images/menu/Jain_Biryani.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Pulav');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Rice' LIMIT 1), 'Green Peas Pulav', 'Aromatic rice cooked with sweet green peas', 130.00, 'images/menu/Jain_Biryani.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Green Peas Pulav');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Rice' LIMIT 1), 'Veg Fry Rice', 'Wok-tossed vegetable fried rice', 130.00, 'images/menu/Veg_Hakka_Noodles.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Veg Fry Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Rice' LIMIT 1), 'Shezwan Fry Rice', 'Spicy Schezwan vegetable fried rice', 140.00, 'images/menu/Veg_Hakka_Noodles.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Shezwan Fry Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Rice' LIMIT 1), 'Dal Khichadi', 'Comforting ghee-tempered dal and rice khichdi', 130.00, 'images/menu/Dal_Tadka.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Dal Khichadi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Rice' LIMIT 1), 'Mushroom Fry Rice', 'Wok-tossed fried rice with fresh mushrooms', 140.00, 'images/menu/Veg_Biryani.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mushroom Fry Rice');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Roti' LIMIT 1), 'Roti', 'Fresh tandoori wheat roti', 10.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Roti');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Roti' LIMIT 1), 'Butter Roti', 'Tandoori wheat roti brushed with butter', 15.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Butter Roti');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Roti' LIMIT 1), 'Parotha', 'Layered flaky whole-wheat paratha', 30.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Roti' LIMIT 1), 'Butter Parotha', 'Flaky layered paratha with butter', 35.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Butter Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Roti' LIMIT 1), 'Masala Parotha', 'Spiced crispy layered paratha', 50.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Masala Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Roti' LIMIT 1), 'Aalu Parotha', 'Stuffed spiced potato paratha', 50.00, 'images/menu/Butter_Roti.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Aalu Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Roti' LIMIT 1), 'Paneer Parotha', 'Stuffed spiced cottage cheese paratha', 70.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Paneer Parotha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Roti' LIMIT 1), 'Naan', 'Soft tandoor-baked naan', 30.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Naan');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Roti' LIMIT 1), 'Butter Naan', 'Soft tandoori naan glazed with butter', 35.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Butter Naan');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Roti' LIMIT 1), 'Kashmiri Naan', 'Sweet dry-fruit stuffed Kashmiri naan', 40.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Kashmiri Naan');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Roti' LIMIT 1), 'Kulcha', 'Soft tandoor-baked kulcha', 40.00, 'images/menu/Butter_Naan.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Kulcha');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Papad' LIMIT 1), 'Roast Papad', 'Crisp flame-roasted urad papad', 20.00, 'images/menu/Masala_Papad.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Roast Papad');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Papad' LIMIT 1), 'Fry Papad', 'Crispy golden fried papad', 15.00, 'images/menu/Masala_Papad.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Fry Papad');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Papad' LIMIT 1), 'Masala Papad', 'Crispy papad topped with spiced onion, tomato and sev', 35.00, 'images/menu/Masala_Papad.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Masala Papad');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Papad' LIMIT 1), 'Papad Burji', 'Crushed spiced papad tossed with onions and chillies', 50.00, 'images/menu/Masala_Papad.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Papad Burji');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Cold Drinks' LIMIT 1), 'Thums Up', 'Chilled Thums Up soft drink', 20.00, 'images/menu/Blue_Lagoon.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Thums Up');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Cold Drinks' LIMIT 1), 'Limca', 'Chilled lemon-lime Limca', 20.00, 'images/menu/Virgin_Mojito.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Limca');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Cold Drinks' LIMIT 1), 'Maaza', 'Chilled mango drink', 20.00, 'images/menu/Mango_Juice.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Maaza');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Cold Drinks' LIMIT 1), 'Soda', 'Chilled sparkling soda', 15.00, 'images/menu/Virgin_Mojito.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Soda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Cold Drinks' LIMIT 1), 'Jeera Soda', 'Refreshing cumin-spiced jeera soda', 20.00, 'images/menu/Virgin_Mojito.png', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Jeera Soda');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Cold Drinks' LIMIT 1), 'Lassi', 'Creamy chilled sweet Punjabi lassi', 20.00, 'images/menu/Lassi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Lassi');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Cold Drinks' LIMIT 1), 'Tak', 'Refreshing spiced chaas / buttermilk', 20.00, 'images/menu/Lassi.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Tak');
INSERT INTO menu (restaurant_id, category_id, item_name, description, price, image, status)
SELECT 3, (SELECT category_id FROM category WHERE restaurant_id = 3 AND category_name = 'Cold Drinks' LIMIT 1), 'Mineral Water', 'Packaged 1L mineral drinking water', 20.00, 'images/menu/Blue_Lagoon.jpg', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE restaurant_id = 3 AND item_name = 'Mineral Water');

