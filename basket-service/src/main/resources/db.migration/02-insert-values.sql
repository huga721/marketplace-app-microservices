--liquibase formatted sql

--changeset basket-service:insert-values
INSERT INTO basket(basket_value, keycloak_id, product_number, status)
VALUES (315, 'cbe10031-5ab7-4ff6-b740-e9b001d93dd1', 2, 'INACTIVE');
INSERT INTO basket_product(product_id, product_value, basket_id)
VALUES (55, 230, 1);
INSERT INTO basket_product(product_id, product_value, basket_id)
VALUES (56, 85, 1);

INSERT INTO basket(basket_value, keycloak_id, product_number, status)
VALUES (365, '24caad66-d212-4142-83dc-f409bd1677cc', 3, 'INACTIVE');
INSERT INTO basket_product(product_id, product_value, basket_id)
VALUES (109, 25, 2);
INSERT INTO basket_product(product_id, product_value, basket_id)
VALUES (110, 300, 2);
INSERT INTO basket_product(product_id, product_value, basket_id)
VALUES (20, 40, 2);

INSERT INTO basket(basket_value, keycloak_id, product_number, status)
VALUES (465, '9cd12f86-85b4-428f-a7d3-15958fe531f3', 4, 'INACTIVE');
INSERT INTO basket_product(product_id, product_value, basket_id)
VALUES (60, 25, 3);
INSERT INTO basket_product(product_id, product_value, basket_id)
VALUES (61, 300, 3);
INSERT INTO basket_product(product_id, product_value, basket_id)
VALUES (62, 40, 3);
INSERT INTO basket_product(product_id, product_value, basket_id)
VALUES (63, 100, 3);

INSERT INTO basket(basket_value, keycloak_id, product_number, status)
VALUES (760, '4bbbe6ee-d1cf-40d3-bf26-9bf29c81dbca', 4, 'INACTIVE');
INSERT INTO basket_product(product_id, product_value, basket_id)
VALUES (123, 55, 4);
INSERT INTO basket_product(product_id, product_value, basket_id)
VALUES (521, 165, 4);
INSERT INTO basket_product(product_id, product_value, basket_id)
VALUES (321, 320, 4);
INSERT INTO basket_product(product_id, product_value, basket_id)
VALUES (214, 220, 4);