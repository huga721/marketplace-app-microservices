--liquibase formatted sql

--changeset product-service:insert-values
INSERT INTO orders(basket_id, address, keycloak_id, payment_type, postal_code)
VALUES (1, 'Lublin', 'cbe10031-5ab7-4ff6-b740-e9b001d93dd1', 'CARD', '20-400');

INSERT INTO orders(basket_id, address, keycloak_id, payment_type, postal_code)
VALUES (2, 'Lublin', '24caad66-d212-4142-83dc-f409bd1677cc', 'CARD', '20-400');

INSERT INTO orders(basket_id, address, keycloak_id, payment_type, postal_code)
VALUES (3, 'Lublin', '9cd12f86-85b4-428f-a7d3-15958fe531f3', 'CARD', '20-400');

INSERT INTO orders(basket_id, address, keycloak_id, payment_type, postal_code)
VALUES (4, 'Lublin', '4bbbe6ee-d1cf-40d3-bf26-9bf29c81dbca', 'CARD', '20-400');