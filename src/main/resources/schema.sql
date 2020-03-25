create table users
(
    email    varchar(150) not null primary key,
    user     varchar(50)  not null,
    password varchar(50)  not null,
    enabled  boolean      not null
);

create table users_rol
(
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT primary key,
    email     varchar(150) not null,
    rol varchar(50)  not null,
    constraint fk_authorities_users foreign key (email) references users (email) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION
);