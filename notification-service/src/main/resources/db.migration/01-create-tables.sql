--liquibase formatted sql

--changeset notification-service:creating-notification-schema
CREATE TABLE notification
(
    id        bigserial NOT NULL PRIMARY KEY,
    message   varchar(255) DEFAULT NULL,
    timestamp timestamp(6) DEFAULT NULL,
    keycloak_id varchar(255) DEFAULT NULL
);