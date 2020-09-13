DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS(
                     id CHAR(24),
                     title VARCHAR(255) NOT NULL,
                     PRIMARY KEY (id)
);

DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS(
                       id CHAR(24),
                       full_name VARCHAR(255) NOT NULL,
                       PRIMARY KEY (id)
);

DROP TABLE IF EXISTS BOOK_AUTHOR;
CREATE TABLE BOOK_AUTHOR(
                            book_id CHAR(24) NOT NULL REFERENCES BOOKS (id) ON DELETE CASCADE,
                            author_id CHAR(24) NOT NULL REFERENCES AUTHORS (id),
                            PRIMARY KEY (book_id, author_id)
);

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES(
                      id CHAR(24),
                      title VARCHAR(255) NOT NULL,
                      PRIMARY KEY (id)
);

DROP TABLE IF EXISTS BOOK_GENRE;
CREATE TABLE BOOK_GENRE(
                           book_id CHAR(24) NOT NULL REFERENCES BOOKS (id) ON DELETE CASCADE,
                           genre_id CHAR(24) NOT NULL REFERENCES GENRES (id),
                           PRIMARY KEY (book_id, genre_id)
);

DROP TABLE IF EXISTS COMMENTS;
CREATE TABLE COMMENTS(
                        id CHAR(24),
                        text VARCHAR(1000) NOT NULL,
                        book_id CHAR(24) NOT NULL REFERENCES BOOKS (id),
                        PRIMARY KEY (id)
);