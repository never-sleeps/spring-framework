DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS(
                     id BIGSERIAL,
                     title VARCHAR(255) NOT NULL,
                     PRIMARY KEY (id)
);

DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS(
                       id BIGSERIAL,
                       full_name VARCHAR(255) NOT NULL,
                       PRIMARY KEY (id)
);

DROP TABLE IF EXISTS BOOK_AUTHOR;
CREATE TABLE BOOK_AUTHOR(
                            book_id BIGINT NOT NULL REFERENCES BOOKS (id) ON DELETE CASCADE,
                            author_id BIGINT NOT NULL REFERENCES AUTHORS (id),
                            PRIMARY KEY (book_id, author_id)
);

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES(
                      id BIGSERIAL,
                      title VARCHAR(255) NOT NULL,
                      PRIMARY KEY (id)
);

DROP TABLE IF EXISTS BOOK_GENRE;
CREATE TABLE BOOK_GENRE(
                           book_id BIGINT NOT NULL REFERENCES BOOKS (id) ON DELETE CASCADE,
                           genre_id BIGINT NOT NULL REFERENCES GENRES (id),
                           PRIMARY KEY (book_id, genre_id)
);

DROP TABLE IF EXISTS COMMENTS;
CREATE TABLE COMMENTS(
                        id BIGSERIAL,
                        text VARCHAR(1000) NOT NULL,
                        book_id BIGINT NOT NULL REFERENCES BOOKS (id),
                        PRIMARY KEY (id)
);