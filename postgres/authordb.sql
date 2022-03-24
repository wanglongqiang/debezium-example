create table author (
  id int not null primary key,
  name varchar(32) not null,
  age int
);

insert into author (id, name, age) values (1, 'one', 10);
insert into author (id, name, age) values (2, 'two', 20);
insert into author (id, name, age) values (3, 'three', 30);