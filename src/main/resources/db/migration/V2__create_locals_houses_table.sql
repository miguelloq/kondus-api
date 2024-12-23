create table locals (
    id serial primary key,
    street varchar(255),
    number int,
    cep char(8),
    name varchar(20),
    description varchar(255),
    type varchar(255) not null
);

create table houses(
    id serial primary key,
    local_id serial not null,
    description varchar(255) not null,
    foreign key (local_id) references locals(id) on delete cascade
);

create table users_houses(
    user_id serial not null,
    house_id serial not null,
    primary key (user_id,house_id),
    foreign key (user_id) references users(id) on delete cascade,
    foreign key (house_id) references houses(id) on delete cascade
)
