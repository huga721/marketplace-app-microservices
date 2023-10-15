--liquibase formatted sql

--changeset product-service:insert-values
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('iPhone 14', 'Brand new iphone 14, selling because unwanted gift', 3000, 'cbe10031-5ab7-4ff6-b740-e9b001d93dd1', 'NEW', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Ikea desk', 'New ikea desk', 150, 'cbe10031-5ab7-4ff6-b740-e9b001d93dd1', 'NEW', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Blue Jeans', 'Classic blue jeans for men', 40, 'cbe10031-5ab7-4ff6-b740-e9b001d93dd1', 'NEW', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Floral Dress', 'Elegant floral dress for women', 60, 'cbe10031-5ab7-4ff6-b740-e9b001d93dd1', 'NEW', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Samsung 50-inch Smart TV', 'Smart TV with 4K resolution', 500, 'cbe10031-5ab7-4ff6-b740-e9b001d93dd1', 'NEW', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Apple MacBook Pro', 'Powerful laptop for professionals', 1500, 'cbe10031-5ab7-4ff6-b740-e9b001d93dd1', 'NEW', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('KitchenAid Stand Mixer', 'High-quality kitchen mixer', 250, '24caad66-d212-4142-83dc-f409bd1677cc', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Ceramic Coffee Mug Set', 'Set of 4 ceramic coffee mugs', 20, '24caad66-d212-4142-83dc-f409bd1677cc', 'NEW', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Sony 65-inch 4K Smart TV', 'Ultra HD Smart TV with stunning picture quality', 700, '24caad66-d212-4142-83dc-f409bd1677cc', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Dining Table Set', 'Elegant dining table with chairs for your dining room', 450, '24caad66-d212-4142-83dc-f409bd1677cc', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Used Bicycle', 'Mountain bike in good condition, perfect for outdoor adventures', 200, '24caad66-d212-4142-83dc-f409bd1677cc', 'SATISFACTORY', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('HP LaserJet Pro Printer', 'High-quality laser printer for office use', 300, '24caad66-d212-4142-83dc-f409bd1677cc', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Wardrobe Closet', 'Spacious wardrobe closet for organizing your clothes', 350, '9cd12f86-85b4-428f-a7d3-15958fe531f3', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Electric Blender', 'Powerful blender for making smoothies and shakes', 60, '9cd12f86-85b4-428f-a7d3-15958fe531f3', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Collectible Coins', 'Rare collectible coins from around the world', 120, '9cd12f86-85b4-428f-a7d3-15958fe531f3', 'SATISFACTORY', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Digital Camera', 'Compact digital camera for capturing memories', 150, '9cd12f86-85b4-428f-a7d3-15958fe531f3', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Gaming Keyboard and Mouse Combo', 'High-performance gaming peripherals', 80, '9cd12f86-85b4-428f-a7d3-15958fe531f3', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Antique Wooden Chest', 'Beautifully crafted antique chest for storage', 300, '9cd12f86-85b4-428f-a7d3-15958fe531f3', 'SATISFACTORY', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Retro Record Player', 'Vintage-style record player for vinyl enthusiasts', 150, 'e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Outdoor Gas Grill', 'Stainless steel grill for backyard barbecues', 350, 'e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Childrens Bicycle', 'Small bicycle for kids to enjoy outdoor rides', 80, 'e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Designer Sunglasses', 'High-end sunglasses for a touch of style', 120, 'e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88', 'NEW', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Lawn Mower', 'Gas-powered lawn mower for a well-manicured lawn', 250, 'e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Fitness Tracker Watch', 'Track your fitness goals with this smartwatch', 60, 'e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Vinyl Records', 'Collection of classic vinyl records', 100, '4bbbe6ee-d1cf-40d3-bf26-9bf29c81dbca', 'SATISFACTORY', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Gaming Console', 'Popular gaming console with a variety of games', 300, '4bbbe6ee-d1cf-40d3-bf26-9bf29c81dbca', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Designer Handbag', 'Luxury handbag for a touch of elegance', 400, '4bbbe6ee-d1cf-40d3-bf26-9bf29c81dbca', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Electric Drill Kit', 'Complete set of power tools for DIY projects', 120, '4bbbe6ee-d1cf-40d3-bf26-9bf29c81dbca', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Smartphone', 'Non-functional smartphone for parts or repair', 50, '4bbbe6ee-d1cf-40d3-bf26-9bf29c81dbca', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Leather Sofa', 'Quality leather sofa in excellent condition', 800, '4bbbe6ee-d1cf-40d3-bf26-9bf29c81dbca', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Antique Pocket Watch', 'Collectible pocket watch in working condition', 150, '05a1c0ab-9229-41bb-a57b-6e82a8f3d6b5', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Wooden Bookshelf', 'Sturdy wooden bookshelf for your home library', 100, '05a1c0ab-9229-41bb-a57b-6e82a8f3d6b5', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken VCR Player', 'Non-working VCR player for vintage enthusiasts', 30, '05a1c0ab-9229-41bb-a57b-6e82a8f3d6b5', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Artificial Christmas Tree', 'Festive Christmas tree for holiday decorating', 40, '05a1c0ab-9229-41bb-a57b-6e82a8f3d6b5', 'SATISFACTORY', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Electric Fan', 'Non-functional electric fan for parts or repair', 20, '05a1c0ab-9229-41bb-a57b-6e82a8f3d6b5', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Sewing Machine', 'Antique sewing machine in working condition', 70, '05a1c0ab-9229-41bb-a57b-6e82a8f3d6b5', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Toaster Oven', 'Non-working toaster oven for parts or repair', 15, 'ea720d0f-cb15-496f-b476-3d0b21839391', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Modern Coffee Table', 'Contemporary coffee table in excellent condition', 120, 'ea720d0f-cb15-496f-b476-3d0b21839391', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Turntable', 'Classic record player for vinyl enthusiasts', 120, 'ea720d0f-cb15-496f-b476-3d0b21839391', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Bicycle', 'Non-functional bicycle for parts or repair', 30, 'ea720d0f-cb15-496f-b476-3d0b21839391', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Antique Tea Set', 'Vintage tea set in excellent condition', 80, 'ea720d0f-cb15-496f-b476-3d0b21839391', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Fishing Gear Bundle', 'Complete fishing gear package for outdoor enthusiasts', 200, 'ea720d0f-cb15-496f-b476-3d0b21839391', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Record Collection', 'Non-playable vintage vinyl records for collectors', 10, 'f584e8b3-f42d-4a23-a75f-0fe75b2e5731', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Childrens Play Kitchen', 'Toy kitchen set for kids to enjoy pretend cooking', 50, 'f584e8b3-f42d-4a23-a75f-0fe75b2e5731', 'NEW', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Vacuum Cleaner', 'Non-functional vacuum cleaner for parts or repair', 25, 'f584e8b3-f42d-4a23-a75f-0fe75b2e5731', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Collectible Action Figures', 'Rare collectible action figures from popular franchises', 75, 'f584e8b3-f42d-4a23-a75f-0fe75b2e5731', 'SATISFACTORY', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Blender', 'Non-functional blender for parts or repair', 15, 'f584e8b3-f42d-4a23-a75f-0fe75b2e5731', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Classic Board Games', 'Collection of classic board games for family fun', 40, 'f584e8b3-f42d-4a23-a75f-0fe75b2e5731', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Camera', 'Classic film camera for photography enthusiasts', 90, 'abe836c3-3da1-4346-958f-ea35b84fc00f', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Laptop', 'Non-functional laptop for parts or repair', 50, 'abe836c3-3da1-4346-958f-ea35b84fc00f', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Collectible Coins Set', 'Rare collectible coin set from different countries', 150, 'abe836c3-3da1-4346-958f-ea35b84fc00f', 'SATISFACTORY', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Microwave', 'Non-functional microwave for parts or repair', 20, 'abe836c3-3da1-4346-958f-ea35b84fc00f', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Art Supplies Set', 'Complete art supplies kit for aspiring artists', 70, 'abe836c3-3da1-4346-958f-ea35b84fc00f', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Power Drill', 'Non-functional power drill for parts or repair', 25, 'abe836c3-3da1-4346-958f-ea35b84fc00f', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Classic Vinyl Records', 'Collection of classic vinyl records from the 70s', 80, '3de5e5e9-2aa6-43cf-86fc-cf6ce4430498', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Refrigerator', 'Non-functional refrigerator for parts or repair', 30, '3de5e5e9-2aa6-43cf-86fc-cf6ce4430498', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Watch Collection', 'Assortment of vintage wristwatches from various eras', 120, '3de5e5e9-2aa6-43cf-86fc-cf6ce4430498', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Coffee Maker', 'Non-functional coffee maker for parts or repair', 15, '3de5e5e9-2aa6-43cf-86fc-cf6ce4430498', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Washing Machine', 'Non-functional washing machine for parts or repair', 70, '3de5e5e9-2aa6-43cf-86fc-cf6ce4430498', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Chess Set', 'Classic chess set for board game enthusiasts', 60, '3de5e5e9-2aa6-43cf-86fc-cf6ce4430498', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Treadmill', 'Non-functional treadmill for parts or repair', 40, 'c4a37747-9c0d-4bfa-a9c3-6f593c500c96', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Musical Keyboard', 'Electronic keyboard for music enthusiasts', 120, 'c4a37747-9c0d-4bfa-a9c3-6f593c500c96', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Lawn Mower', 'Non-functional lawn mower for parts or repair', 30, 'c4a37747-9c0d-4bfa-a9c3-6f593c500c96', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Vinyl Records', 'Collection of classic vinyl records from the 80s', 70, 'c4a37747-9c0d-4bfa-a9c3-6f593c500c96', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Blender', 'Non-functional blender for parts or repair', 25, 'c4a37747-9c0d-4bfa-a9c3-6f593c500c96', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Art Supplies Set', 'Complete art supplies kit for aspiring artists', 70, 'c4a37747-9c0d-4bfa-a9c3-6f593c500c96', 'SATISFACTORY', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Power Drill', 'Non-functional power drill for parts or repair', 35, '2c5e07e3-b2a6-489c-b3fa-6263100ed949', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Classic Vinyl Records', 'Collection of classic vinyl records from the 90s', 60, '2c5e07e3-b2a6-489c-b3fa-6263100ed949', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Record Player', 'Classic turntable for vinyl enthusiasts', 110, '2c5e07e3-b2a6-489c-b3fa-6263100ed949', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Tablet', 'Non-functional tablet for parts or repair', 40, '2c5e07e3-b2a6-489c-b3fa-6263100ed949', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Collectible Stamps', 'Rare collectible stamps from various countries', 90, '2c5e07e3-b2a6-489c-b3fa-6263100ed949', 'SATISFACTORY', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Oven', 'Non-functional oven for parts or repair', 30, '2c5e07e3-b2a6-489c-b3fa-6263100ed949', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Art Supplies Bundle', 'Comprehensive art supplies package for artists', 80, '90b0a647-ae7f-4d19-971a-f98ab5fb8c10', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Refrigerator', 'Non-functional refrigerator for parts or repair', 50, '90b0a647-ae7f-4d19-971a-f98ab5fb8c10', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Film Camera', 'Classic film camera for photography enthusiasts', 85, '90b0a647-ae7f-4d19-971a-f98ab5fb8c10', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Vacuum Cleaner', 'Non-functional vacuum cleaner for parts or repair', 35, '90b0a647-ae7f-4d19-971a-f98ab5fb8c10', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Comic Books', 'Collection of vintage comic books from different eras', 75, '90b0a647-ae7f-4d19-971a-f98ab5fb8c10', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Blender', 'Non-functional blender for parts or repair', 20, '90b0a647-ae7f-4d19-971a-f98ab5fb8c10', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Fountain Pen', 'Classic fountain pen with gold nib', 70, 'ef5a6014-fa82-4f4a-a8cf-3a757ce4e05f', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Bicycle', 'Non-functional bicycle for parts or repair', 30, 'ef5a6014-fa82-4f4a-a8cf-3a757ce4e05f', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Leather Briefcase', 'Quality leather briefcase for professionals', 50, 'ef5a6014-fa82-4f4a-a8cf-3a757ce4e05f', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Digital Camera', 'Non-functional digital camera for parts or repair', 25, 'ef5a6014-fa82-4f4a-a8cf-3a757ce4e05f', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Cookware Set', 'Complete set of high-quality cookware', 90, 'ef5a6014-fa82-4f4a-a8cf-3a757ce4e05f', 'NEW', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Watch Collection', 'Non-functional watch collection for repair or parts', 20, 'ef5a6014-fa82-4f4a-a8cf-3a757ce4e05f', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Posters Collection', 'Assortment of vintage posters from different eras', 60, 'ceca643d-b6cd-42c1-bfc7-aaf48d456728', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Alarm Clock', 'Non-functional alarm clock for parts or repair', 8, 'ceca643d-b6cd-42c1-bfc7-aaf48d456728', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Home Office Desk', 'Sturdy desk for a productive home office', 120, 'ceca643d-b6cd-42c1-bfc7-aaf48d456728', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken DVD Player', 'Non-working DVD player for parts or repair', 12, 'ceca643d-b6cd-42c1-bfc7-aaf48d456728', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Hair Curler', 'Non-functional hair curler for parts or repair', 18, 'ceca643d-b6cd-42c1-bfc7-aaf48d456728', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Vinyl Records', 'Collection of classic vinyl records from the 70s', 80, 'ceca643d-b6cd-42c1-bfc7-aaf48d456728', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Microwave Oven', 'Non-functional microwave oven for parts or repair', 30, 'ceca643d-b6cd-42c1-bfc7-aaf48d456728', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Professional Art Easel', 'Sturdy art easel for painters and artists', 40, '301e6147-504a-47ef-b409-6106c2d2c5ca', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Vacuum Cleaner', 'Non-functional vacuum cleaner for parts or repair', 30, '301e6147-504a-47ef-b409-6106c2d2c5ca', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Wooden Chess Set', 'Handcrafted wooden chess set for strategic games', 50, '301e6147-504a-47ef-b409-6106c2d2c5ca', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Coffee Grinder', 'Non-functional coffee grinder for parts or repair', 18, '301e6147-504a-47ef-b409-6106c2d2c5ca', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Fountain Pen', 'Classic fountain pen with gold nib', 70, '301e6147-504a-47ef-b409-6106c2d2c5ca', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Bicycle', 'Non-functional bicycle for parts or repair', 30, '301e6147-504a-47ef-b409-6106c2d2c5ca', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Leather Briefcase', 'Quality leather briefcase for professionals', 50, '971383e4-40ab-4c40-9db9-907cfca3a034', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Laptop Charger', 'Non-functional laptop charger for parts or repair', 12, '971383e4-40ab-4c40-9db9-907cfca3a034', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Pocket Watch', 'Classic pocket watch with intricate design', 65, '971383e4-40ab-4c40-9db9-907cfca3a034', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Sewing Machine', 'Non-functional sewing machine for parts or repair', 45, '971383e4-40ab-4c40-9db9-907cfca3a034', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Antique Wall Clock', 'Vintage wall clock with pendulum and chimes', 100, '971383e4-40ab-4c40-9db9-907cfca3a034', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Vacuum Cleaner', 'Non-functional vacuum cleaner for parts or repair', 30, '971383e4-40ab-4c40-9db9-907cfca3a034', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Classic Board Games Collection', 'Set of classic board games for family fun', 60, '5a50d901-b333-40e9-b913-1d10b6912fa0', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Hair Dryer', 'Non-functional hair dryer for parts or repair', 15, '5a50d901-b333-40e9-b913-1d10b6912fa0', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Professional Drawing Tablet', 'High-quality drawing tablet for digital artists', 80, '5a50d901-b333-40e9-b913-1d10b6912fa0', 'NEW', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Coffee Maker', 'Non-functional coffee maker for parts or repair', 20, '5a50d901-b333-40e9-b913-1d10b6912fa0', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Record Player', 'Classic record player with built-in speakers', 70, '5a50d901-b333-40e9-b913-1d10b6912fa0', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Blender', 'Non-functional blender for parts or repair', 25, '5a50d901-b333-40e9-b913-1d10b6912fa0', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Antique Writing Desk', 'Elegant desk with intricate woodwork', 300, 'fcdf92a2-5ccb-40c8-80d4-6c6b621a498e', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Cordless Phone', 'Non-functional cordless phone for parts or repair', 15, 'fcdf92a2-5ccb-40c8-80d4-6c6b621a498e', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Art Supplies Set', 'Complete art supplies kit for aspiring artists', 70, 'fcdf92a2-5ccb-40c8-80d4-6c6b621a498e', 'SATISFACTORY', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Power Drill', 'Non-functional power drill for parts or repair', 35, 'fcdf92a2-5ccb-40c8-80d4-6c6b621a498e', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Classic Vinyl Records', 'Collection of classic vinyl records from the 80s', 60, 'fcdf92a2-5ccb-40c8-80d4-6c6b621a498e', 'GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Toaster', 'Non-functional toaster for parts or repair', 10, 'fcdf92a2-5ccb-40c8-80d4-6c6b621a498e', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));

INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Outdoor Camping Tent', 'Spacious tent for outdoor adventures', 60, '915495a0-fc10-4b33-8bfd-28d4b9171bd2', 'NEW', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Microwave Oven', 'Non-functional microwave oven for parts or repair', 20, '915495a0-fc10-4b33-8bfd-28d4b9171bd2', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Portable Bluetooth Speaker', 'Wireless speaker for music on-the-go', 25, '915495a0-fc10-4b33-8bfd-28d4b9171bd2', 'SATISFACTORY', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Record Player', 'Non-functional vintage record player for parts or repair', 40, '915495a0-fc10-4b33-8bfd-28d4b9171bd2', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Vintage Leather Armchair', 'Classic leather armchair for a cozy living room', 150, '915495a0-fc10-4b33-8bfd-28d4b9171bd2', 'VERY_GOOD', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
INSERT INTO product(name, description, price, keycloak_id, quality, status, created_time)
VALUES ('Broken Digital Camera', 'Non-functional digital camera for parts or repair', 25, '915495a0-fc10-4b33-8bfd-28d4b9171bd2', 'BROKEN', 'ACTIVE',
    TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'));
