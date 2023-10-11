--liquibase formatted sql

--changeset user-service:creating-users-schema
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id          bigserial     NOT NULL    PRIMARY KEY,
    username    varchar(255)    DEFAULT NULL,
    keycloak_id    varchar(255)    DEFAULT NULL,
    first_name   varchar(255)    DEFAULT NULL,
    last_name    varchar(255)    DEFAULT NULL,
    email       varchar(255)    DEFAULT NULL,
    role_name    varchar(255)    DEFAULT NULL
);