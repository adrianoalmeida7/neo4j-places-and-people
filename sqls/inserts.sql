/*
Perguntas:
1: quem viajou pro mesmo lugar que o Paniz 
  
  PersonDAO.whoTraveledToTheSamePlacesThan(Person p);

2: quem viajou para Tokio também **VIAJOU** para?
  PlaceDAO.alsoTraveledAs(Place p)

3: quem **passou por** Paris também passou por?
(passou por pode ser viajando ou morando)
*/









insert into persons values (1, 'Ceci');
insert into persons values (2, 'Adriano');
insert into persons values (3, 'Paniz');
insert into persons values (4, 'Paulo');
insert into persons values (5, 'Gui');
insert into persons values (6, 'Torti');

insert into places values (1, 'Toronto', 'Canada');
insert into places values (2, 'Roma', 'Italia');
insert into places values (3, 'Berlin', 'Alemanha');
insert into places values (4, 'Antuerpia', 'Belgica');
insert into places values (5, 'Paris', 'Franca');
insert into places values (6, 'Nova Iorque', 'EUA');
insert into places values (7, 'Tokio', 'Japao');

insert into lived_at (id_place, id_person, starting_at, `until`) values(1, 2, now(), now());
insert into lived_at (id_place, id_person, starting_at, `until`) values(3, 4, now(), now());
insert into lived_at (id_place, id_person, starting_at, `until`) values(3, 5, now(), now());
insert into lived_at (id_place, id_person, starting_at, `until`) values(5, 2, now(), now());
insert into lived_at (id_place, id_person, starting_at, `until`) values(6, 1, now(), now());

insert into travels (id_person, id_place, date) values (1, 5, now());
insert into travels (id_person, id_place, date) values (2, 2, now());
insert into travels (id_person, id_place, date) values (2, 4, now());
insert into travels (id_person, id_place, date) values (3, 2, now());
insert into travels (id_person, id_place, date) values (4, 6, now());
insert into travels (id_person, id_place, date) values (4, 7, now());
insert into travels (id_person, id_place, date) values (5, 4, now());
insert into travels (id_person, id_place, date) values (5, 5, now());
insert into travels (id_person, id_place, date) values (5, 7, now());
insert into travels (id_person, id_place, date) values (6, 6, now());