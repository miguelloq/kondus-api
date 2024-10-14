create table items(
    id serial primary key,
    description varchar(255) not null,
    user_id serial not null,
    category varchar(255) not null,
    price real,
    foreign key (user_id) references users(id) on delete cascade
)

create table registrations(
    id serial primary key,
    item_id serial not null,
    dealer_id serial not null,
    first_time timestamp not null
    foreign key (item_id) references items(id) on delete cascade,
    foreign key (dealer_id) references users(id) on delete cascade
)

