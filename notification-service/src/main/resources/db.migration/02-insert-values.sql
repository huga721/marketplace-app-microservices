--liquibase formatted sql

--changeset product-service:insert-values
INSERT INTO notification(message, timestamp, keycloak_id)
VALUES ('Product with id: 1 was created.',
        TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'),
        'cbe10031-5ab7-4ff6-b740-e9b001d93dd1');

INSERT INTO notification(message, timestamp, keycloak_id)
VALUES ('Product with id: 5 was created.',
        TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'),
        'cbe10031-5ab7-4ff6-b740-e9b001d93dd1');

INSERT INTO notification(message, timestamp, keycloak_id)
VALUES ('Product with id: 9 was created.',
        TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'),
        '24caad66-d212-4142-83dc-f409bd1677cc');

INSERT INTO notification(message, timestamp, keycloak_id)
VALUES ('Product with id: 11 was created.',
        TIMESTAMP '2023-09-15' + random() * (TIMESTAMP '2023-10-15' - TIMESTAMP '2023-09-15'),
        '24caad66-d212-4142-83dc-f409bd1677cc');


