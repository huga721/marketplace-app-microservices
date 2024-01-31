--liquibase formatted sql

--changeset order-service:creating-basket-schema
CREATE TABLE orders
(
    id bigserial NOT NULL PRIMARY KEY,
    basket_id bigserial,
    address varchar(255),
    keycloak_id varchar(255),
    payment_type varchar(255),
    postal_code character varying(255)
)