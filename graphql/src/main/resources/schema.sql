
create table author (
    id integer not null AUTO_INCREMENT,
    name varchar(128) not null,
    primary key (id)
);

create table book (
    id integer not null AUTO_INCREMENT,
    name varchar(128) not null,
    publish_year integer,
    primary key (id)
);

create table book_author(
    author integer not null,
    book integer not null,
    primary key (author, book),
    foreign key (author) references author(id) ON DELETE CASCADE,
    foreign key (book) references book(id) ON DELETE CASCADE
);

insert into book(id, name, publish_year) values
                                             (-1, 'Сброс! Книга 1', 2022),
                                             (-2, 'Богатырские сказки', 2022),
                                             (-3, 'Посторонним вход воспрещён', 2022),
                                             (-4, 'На память', 2022),
                                             (-5, 'Физика 10 класс', 2017),
                                             (-6, 'Математика 11 класс', 2016);

insert into author(id, name) values
                                 (-1, 'Борис Борисович Батыршин'),
                                 (-2, 'Грильяж'),
                                 (-3, 'Тея Лав'),
                                 (-4, 'Борис Борисович Буховцев'),
                                 (-5, 'Геннадий Яковлевич Мякишев'),
                                 (-6, 'Николай Николаевич Сотский')
                                 ;

insert into book_author(author, book) values
                                          (-2, -1),
                                          (-1, -3),
                                          (-3, -4),
                                          (-4, -5),
                                          (-5, -5),
                                          (-6, -5),
                                          (-5, -6);