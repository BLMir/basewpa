INSERT INTO users(email, username, password, enabled)
values('user@gmail.com','user', 'pass',true);

INSERT INTO users(email, username, password, enabled)
values('admin@gmail.com','admin','pass',true);

INSERT INTO rol (email, rol)
values('user@gmail.com', 'ROLE_USER');

INSERT INTO rol (email, rol)
values ('admin@gmail.com','ROLE_ADMIN');