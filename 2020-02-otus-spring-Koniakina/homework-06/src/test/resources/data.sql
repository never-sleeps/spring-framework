insert into AUTHORS (ID, FULL_NAME) values (1, 'Илья Ильф');
insert into AUTHORS (ID, FULL_NAME) values (2, 'Евгений Петров');
insert into AUTHORS (ID, FULL_NAME) values (3, 'Эрик Фримен');
insert into AUTHORS (ID, FULL_NAME) values (4, 'Элизабет Фримен');
insert into AUTHORS (ID, FULL_NAME) values (5, 'Стивен Хокинг');
insert into AUTHORS (ID, FULL_NAME) values (6, 'Люси Хокинг');
insert into AUTHORS (ID, FULL_NAME) values (7, 'Роберт Мартин');

insert into GENRES (ID, TITLE) values (1, 'Роман');
insert into GENRES (ID, TITLE) values (2, 'Приключения');
insert into GENRES (ID, TITLE) values (3, 'Комедия');
insert into GENRES (ID, TITLE) values (4, 'Учебная литература');
insert into GENRES (ID, TITLE) values (5, 'Детская литература');

insert into BOOKS (ID, TITLE) values (1, 'Двенадцать стульев');
insert into BOOKS (ID, TITLE) values (2, 'Одноэтажная Америка');
insert into BOOKS (ID, TITLE) values (3, 'Паттерны проектирования');
insert into BOOKS (ID, TITLE) values (4, 'Джордж и тайны Вселенной');

insert into BOOK_AUTHOR(BOOK_ID, AUTHOR_ID) values(1, 1);
insert into BOOK_AUTHOR(BOOK_ID, AUTHOR_ID) values(1, 2);
insert into BOOK_AUTHOR(BOOK_ID, AUTHOR_ID) values(2, 1);
insert into BOOK_AUTHOR(BOOK_ID, AUTHOR_ID) values(2, 2);
insert into BOOK_AUTHOR(BOOK_ID, AUTHOR_ID) values(3, 3);
insert into BOOK_AUTHOR(BOOK_ID, AUTHOR_ID) values(3, 4);
insert into BOOK_AUTHOR(BOOK_ID, AUTHOR_ID) values(4, 5);
insert into BOOK_AUTHOR(BOOK_ID, AUTHOR_ID) values(4, 6);

insert into BOOK_GENRE(BOOK_ID, GENRE_ID) values (1, 2);
insert into BOOK_GENRE(BOOK_ID, GENRE_ID) values (1, 3);
insert into BOOK_GENRE(BOOK_ID, GENRE_ID) values (2, 2);
insert into BOOK_GENRE(BOOK_ID, GENRE_ID) values (3, 4);
insert into BOOK_GENRE(BOOK_ID, GENRE_ID) values (4, 2);
insert into BOOK_GENRE(BOOK_ID, GENRE_ID) values (4, 4);
insert into BOOK_GENRE(BOOK_ID, GENRE_ID) values (4, 5);

INSERT INTO COMMENTS(ID, TEXT, BOOK_ID) values (1, 'Полезно для програмиста', 3);
INSERT INTO COMMENTS(ID, TEXT, BOOK_ID) values (2, 'Про космос', 4);
INSERT INTO COMMENTS(ID, TEXT, BOOK_ID) values (3, 'Для детей от 7 до 11 лет', 4);