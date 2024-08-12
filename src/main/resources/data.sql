INSERT INTO eldar.roles
(`role`)
VALUES('ROLE_ADMIN');

INSERT INTO eldar.roles
(`role`)
VALUES('ROLE_USER');

INSERT INTO eldar.users
( address, email, full_name, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, password, telephone)
VALUES('domicilio del admin', 'admin@gmail.com', 'Admin', 1,
       1, 1, 1,
       '$2a$10$cW2PgLv8pvW99DQrESqWz.NraZMHRIzt1ruLkn/AqhHfWgUu3b9pK', '111111111');

INSERT INTO eldar.users_roles
(user_id, role_id)
VALUES(1, 1);

INSERT INTO eldar.users_roles
(user_id, role_id)
VALUES(1, 2);