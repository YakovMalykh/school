create table people
(
    name           text primary key,
    age            integer check ( age > 13 ),
    driver_license boolean default '0'
);
create table vehicles
(
    id    integer primary key,
    brand text not null,
    model text not null,
    price integer check ( price > 0 )
);

alter table people
    add column vehicles_id integer references vehicles (id) not null;

alter table vehicles
    add column user_name text references people (name);

-- создал еще пару доп запросов, для проверки что работает

select people.name,
       people.age,
       people.driver_license,
       vehicles.id,
       vehicles.brand,
       vehicles.model,
       vehicles.price,
       vehicles.user_name
from people
         inner join vehicles on people.vehicles_id = vehicles.id;

select vehicles.id,
       vehicles.brand,
       vehicles.model,
       vehicles.price,
       people.name,
       people.age,
       people.driver_license
from vehicles
         left join people on vehicles.user_name = people.name;



