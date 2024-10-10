create table locals (
    id serial primary key,
    street varchar(255),
    number varchar(10),
    cep char(8),
    name varchar(20),
    description varchar(255)
);

create table houses(
    id serial primary key,
    local_id serial not null,
    description varchar(255),
    foreign key (local_id) references locals(id) on delete cascade
);
