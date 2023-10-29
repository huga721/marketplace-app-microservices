--liquibase formatted sql

--changeset product-service:creating-users-schema
CREATE TABLE product
(
    id              bigserial          NOT NULL        PRIMARY KEY,
    name            varchar(255)    DEFAULT NULL,
    description     varchar(255)    DEFAULT NULL,
    price           bigint          DEFAULT NULL,
    created_time    timestamp(6),
    keycloak_id     varchar(255),
    quality         varchar(255) check (quality in ('NEW','VERY_GOOD','GOOD','SATISFACTORY','BROKEN')),
    status          varchar(255) check (status in ('ACTIVE','INACTIVE'))
);