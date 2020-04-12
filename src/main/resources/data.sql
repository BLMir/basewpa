INSERT INTO users(email, firstName, lastName, password, enabled)
values('user@gmail.com','Biel','Mir', 'pass',true);

INSERT INTO users(email, username, password, enabled)
values('admin@gmail.com','Arribadulla', 'admin','pass',true);

INSERT INTO users_rol (email, rol)
values('user@gmail.com', 'ROLE_USER');

INSERT INTO users_rol (email, rol)
values ('admin@gmail.com','ROLE_ADMIN');

INSERT INTO users_rol (email, rol)
values ('admin@gmail.com','ROLE_USER');