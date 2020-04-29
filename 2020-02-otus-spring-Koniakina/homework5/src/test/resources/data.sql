insert into AUTHORS (FULLNAME) values ('А.С.Пушкин');
insert into AUTHORS (FULLNAME) values ('Л.Н.Толстой');
insert into AUTHORS (FULLNAME) values ('Ф.М.Достоевский');

insert into GENRES (TITLE) values ('Сказка');
insert into GENRES (TITLE) values ('Роман');
insert into GENRES (TITLE) values ('Стихи');

insert into BOOKS (TITLE, AUTHOR_ID, GENRE_ID) values ('Золотая рыбка', 1, 1);
insert into BOOKS (TITLE, AUTHOR_ID, GENRE_ID) values ('Война и мир', 2, 2);
insert into BOOKS (TITLE, AUTHOR_ID, GENRE_ID) values ('Бесы', 3, 2);
insert into BOOKS (TITLE, AUTHOR_ID, GENRE_ID) values ('Анна Каренина', 2, 2);