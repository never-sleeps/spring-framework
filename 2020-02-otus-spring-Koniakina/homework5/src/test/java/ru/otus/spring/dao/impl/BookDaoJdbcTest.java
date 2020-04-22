package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка получения данных об книгах: ")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDao bookDao;

    private final int DEFAULT_BOOKS_COUNT = 4;
    private final int DEFAULT_BOOKS_BY_TOLSTOY_COUNT = 2;
    private final int DEFAULT_BOOKS_BY_NOVEL_COUNT = 3;
    private final int EXISTING_BOOK_ID = 2;
    private final int EXISTING_AUTHOR_ID = 2;
    private final int EXISTING_GENRE_ID = 2;
    private final String EXISTING_BOOK_TITLE = "Война и мир";
    private final String EXISTING_AUTHOR_FULLNAME = "Л.Н.Толстой";
    private final String EXISTING_GENRE_TITLE = "Роман";
    private final int NOT_EXISTING_BOOK_ID = 99;
    private final String NOT_EXISTING_BOOK_TITLE = "Десять негритят";
    private final String NOT_EXISTING_AUTHOR_FULLNAME = "Агата Кристи";
    private final String NOT_EXISTING_GENRE_TITLE = "Детектив";

    @DisplayName("получение количества книг")
    @Test
    void shouldReturnExpectedBookCount() {
        int count = bookDao.count();
        assertThat(count).isEqualTo(DEFAULT_BOOKS_COUNT);
    }

    @DisplayName("добавление новой книги")
    @Test
    void shouldCreateBook() {
        Author author = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FULLNAME);
        Genre genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TITLE);
        Book newBook = Book.builder().author(author).genre(genre).title(NOT_EXISTING_BOOK_TITLE).build();

        Book expected = bookDao.create(newBook);
        Book actual = bookDao.getById(expected.getId()).get();
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @DisplayName("изменение данных книги")
    @Test
    void shouldUpdateBook() {
        Author existingAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FULLNAME);
        Genre existingGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_TITLE);
        Book updatingBook = new Book(EXISTING_BOOK_ID, NOT_EXISTING_BOOK_TITLE, existingAuthor, existingGenre);

        Book expected = bookDao.update(updatingBook);
        Book actual = bookDao.getById(EXISTING_BOOK_ID).get();
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @DisplayName("существование книги в базе")
    @Test
    void shouldReturnIsExist() {
        Author author = Author.builder().fullName(EXISTING_AUTHOR_FULLNAME).build();
        Genre genre = Genre.builder().title(EXISTING_GENRE_TITLE).build();
        Book book = Book.builder().author(author).genre(genre).title(EXISTING_BOOK_TITLE).build();
        assertTrue(bookDao.isExist(book));
    }

    @DisplayName("отсутствие книги в базе")
    @Test
    void shouldReturnIsNotExist() {
        Author author = Author.builder().fullName(NOT_EXISTING_AUTHOR_FULLNAME).build();
        Genre genre = Genre.builder().title(NOT_EXISTING_GENRE_TITLE).build();
        Book book = Book.builder().author(author).genre(genre).title(NOT_EXISTING_BOOK_TITLE).build();
        assertFalse(bookDao.isExist(book));
    }

    @DisplayName("удаление книги")
    @Test
    void shouldDeleteBook() {
        bookDao.delete(EXISTING_BOOK_ID);
        assertFalse(bookDao.getById(EXISTING_BOOK_ID).isPresent());
    }

    @DisplayName("получение книги по id при существовании такого id")
    @Test
    void shouldGetBookById() {
        Book book = bookDao.getById(EXISTING_BOOK_ID).get();
        assertThat(book.getId()).isEqualTo(EXISTING_BOOK_ID);
    }

    @DisplayName("получение книги по id при отсутствии такого id")
    @Test
    void shouldDoNotGetBookById() {
        assertFalse(bookDao.getById(NOT_EXISTING_BOOK_ID).isPresent());
    }

    @DisplayName("получение книги по name при существовании такого name")
    @Test
    void shouldGetBookByName() {
        Book book = bookDao.getByTitle(EXISTING_BOOK_TITLE).get();
        assertThat(book.getTitle()).isEqualTo(EXISTING_BOOK_TITLE);
    }

    @DisplayName("получение книги по name при отсутствии такого name")
    @Test
    void shouldDoNotGetBookByName() {
        assertFalse(bookDao.getByTitle(NOT_EXISTING_BOOK_TITLE).isPresent());
    }

    @DisplayName("получение списка книг по автору")
    @Test
    void shouldGetAllBooksByAuthor() {
        List<Book> books = bookDao.getByAuthor(EXISTING_AUTHOR_FULLNAME);
        for (Book book: books) {
            assertThat(book.getAuthor().getFullName()).isEqualTo(EXISTING_AUTHOR_FULLNAME);
        }
        assertThat(books.size()).isEqualTo(DEFAULT_BOOKS_BY_TOLSTOY_COUNT);
    }

    @DisplayName("получение списка книг по жанру")
    @Test
    void shouldGetAllBooksByGenre() {
        List<Book> books = bookDao.getByGenre(EXISTING_GENRE_TITLE);
        for (Book book: books) {
            assertThat(book.getGenre().getTitle()).isEqualTo(EXISTING_GENRE_TITLE);
        }
        assertThat(books.size()).isEqualTo(DEFAULT_BOOKS_BY_NOVEL_COUNT);
    }

    @DisplayName("получение списка книг")
    @Test
    void shouldGetAllBooks() {
        List<Book> books = bookDao.getAll();
        assertThat(books.size()).isEqualTo(DEFAULT_BOOKS_COUNT);
    }
}