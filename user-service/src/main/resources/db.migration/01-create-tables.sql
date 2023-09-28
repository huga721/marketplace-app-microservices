--liquibase formatted sql

--changeset authentication-service:creating-users-schema
CREATE TABLE users
(
    id          serial          NOT NULL    PRIMARY KEY,
    role_id     bigint          DEFAULT NULL,
    username    varchar(255)    DEFAULT NULL,
    keycloak_id    varchar(255)    DEFAULT NULL,
    first_name   varchar(255)    DEFAULT NULL,
    last_name    varchar(255)    DEFAULT NULL,
    email       varchar(255)    DEFAULT NULL,
    role_name    varchar(255)    DEFAULT NULL
);