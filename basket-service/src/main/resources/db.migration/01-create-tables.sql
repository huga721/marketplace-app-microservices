--liquibase formatted sql

--changeset basket-service:creating-basket-schema
CREATE TABLE basket
(
    id             bigserial NOT NULL PRIMARY KEY,
    basket_value   bigint,
    keycloak_id    varchar(255),
    product_number integer,
    status         varchar(255)
);

CREATE TABLE basket_product
(
    id            bigserial NOT NULL PRIMARY KEY,
    product_id    bigint,
    product_value bigint,
    basket_id     bigint,
    CONSTRAINT fk28vw328vteqyvaq1oq2giy96l FOREIGN KEY (basket_id)
        REFERENCES basket (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);