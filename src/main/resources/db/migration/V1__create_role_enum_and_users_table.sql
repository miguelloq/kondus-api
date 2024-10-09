create TYPE role as enum ('ADMIN', 'DEFAULT');

create table users (
    id serial primary key,
    email text not null,
    password text not null,
    name text not null,
    role role not null default 'DEFAULT'
);
