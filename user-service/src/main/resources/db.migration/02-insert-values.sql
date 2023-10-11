--liquibase formatted sql

--changeset user-service:insert-values
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('moderator', 'caedd3a9-062b-457b-94a1-b893f0b21b4e', 'Karol', 'Sztaba', 'ksfake@gmail.com', 'ROLE_ADMIN');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('szybki', 'f780c314-3381-4dc9-911c-16d5501a6dec', 'Aleksander', 'Wolny', 'awfake@gmail.com', 'ROLE_ADMIN');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('user', 'cbe10031-5ab7-4ff6-b740-e9b001d93dd1', 'Josh', 'Kentucky', 'jkfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('huga', '24caad66-d212-4142-83dc-f409bd1677cc', 'Hubert', 'Haba', 'hhfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('simba', '9cd12f86-85b4-428f-a7d3-15958fe531f3', 'Simon', 'Res', 'srfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('string', 'e06919ed-dff5-4ca0-a0ad-ab0ca0a90a88', 'Matheo', 'Erwin', 'mefake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('konrad', '4bbbe6ee-d1cf-40d3-bf26-9bf29c81dbca', 'Konrad', 'Waga', 'kwfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('mikolaj', '05a1c0ab-9229-41bb-a57b-6e82a8f3d6b5', 'Mikolaj', 'Stoklosa', 'msfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('thom', 'ea720d0f-cb15-496f-b476-3d0b21839391', 'Thomas', 'Czech', 'tcfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('cuba', 'f584e8b3-f42d-4a23-a75f-0fe75b2e5731', 'Jacob', 'Pullover', 'jpfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('mustang', 'abe836c3-3da1-4346-958f-ea35b84fc00f', 'Mateusz', 'Miller', 'mmfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('gaga', '3de5e5e9-2aa6-43cf-86fc-cf6ce4430498', 'Gabriela', 'Nerkowiec', 'gnfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('marco', 'c4a37747-9c0d-4bfa-a9c3-6f593c500c96', 'Patryk', 'Marek', 'pmfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('dzini', '2c5e07e3-b2a6-489c-b3fa-6263100ed949', 'Wojtek', 'Skowron', 'wsfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('pinkie', '90b0a647-ae7f-4d19-971a-f98ab5fb8c10', 'Josh', 'Hawkins', 'jhfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('andrew', 'ef5a6014-fa82-4f4a-a8cf-3a757ce4e05f', 'Andrew', 'Golota', 'agfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('micki', 'ceca643d-b6cd-42c1-bfc7-aaf48d456728', 'Micheal', 'Schulz', 'msfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('chacky', '301e6147-504a-47ef-b409-6106c2d2c5ca', 'Robert', 'Crab', 'rcfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('maxwell', '971383e4-40ab-4c40-9db9-907cfca3a034', 'Maxim', 'Actimel', 'mafake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('kesoju', '5a50d901-b333-40e9-b913-1d10b6912fa0', 'Morris', 'Penguin', 'mpfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('neo', 'fcdf92a2-5ccb-40c8-80d4-6c6b621a498e', 'Nataniel', 'Gracz', 'npfake@gmail.com', 'ROLE_USER');
INSERT INTO users(username, keycloak_id, first_name, last_name, email, role_name)
values ('getRight', '915495a0-fc10-4b33-8bfd-28d4b9171bd2', 'Leon', 'Win', 'lwfake@gmail.com', 'ROLE_USER');