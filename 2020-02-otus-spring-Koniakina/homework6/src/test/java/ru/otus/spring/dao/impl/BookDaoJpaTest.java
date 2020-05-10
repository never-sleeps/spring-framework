package ru.otus.spring.dao.impl;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import(BookDaoJpa.class)
class BookDaoJpaTest {

    private static final int EXPECTED_QUERIES_COUNT = 7;
    private static final int EXPECTED_NUMBER_OF_BOOKS = 4;
    private static final int EXPECTED_NUMBER_OF_BOOKS_FOR_AUTHOR1 = 2;
    private static final int EXPECTED_NUMBER_OF_BOOKS_FOR_GENRE2 = 3;

    private static final String NEW_BOOK_TITLE = "Как создавался Робинзон";

    private static final long FIRST_BOOK_ID = 1L;
    private static final String FIRST_BOOK_TITLE = "Двенадцать стульев";
    private static final long FIRST_AUTHOR_ID = 1L;
    private static final String FIRST_AUTHOR_NAME = "Илья Ильф";
    private static final long SECOND_GENRE_ID = 2L;
    private static final String SECOND_GENRE_TITLE = "Приключения";

    @Autowired
    private BookDaoJpa bookDaoJpa;
    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("должен возвращать количество книг")
    @Test
    void shouldReturnCountOfBooks() {
        var actualCount = bookDaoJpa.count();
        assertThat(actualCount).isEqualTo(EXPECTED_NUMBER_OF_BOOKS);
    }

    @DisplayName("должен корректно сохранять всю информацию о книге")
    @Test
    void shouldSaveAllBookInfo() {
        val authors = Collections.singletonList(new Author(0, FIRST_AUTHOR_NAME));
        val genres = Collections.singletonList(new Genre(0, SECOND_GENRE_TITLE));
        val newBook = new Book(0, NEW_BOOK_TITLE, authors, genres, List.of());
        bookDaoJpa.create(newBook);

        assertThat(newBook.getId()).isGreaterThan(0);
        val actualBook = testEntityManager.find(Book.class, newBook.getId());
        assertThat(actualBook).isNotNull().matches(s -> !s.getTitle().isEmpty())
                .matches(s -> s.getAuthors() != null && s.getAuthors().size() > 0 && s.getAuthors().get(0).getId() > 0)
                .matches(s -> s.getGenres() != null && s.getGenres().size() > 0 && s.getAuthors().get(0).getId() > 0);
    }

    @DisplayName("должен изменять информацию о книге по её id")
    @Test
    void shouldUpdateBookById() {
        Book firstBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);
        String oldTitle = firstBook.getTitle();
        val oldAuthors = firstBook.getAuthors();
        val oldGenres = firstBook.getGenres();
        testEntityManager.detach(firstBook);

        Author author = new Author(5, "Стивен Хокинг");
        Genre genre = new Genre(2, "Приключения");
        firstBook.setTitle(NEW_BOOK_TITLE);
        firstBook.setAuthors(Collections.singletonList(author));
        firstBook.setGenres(Collections.singletonList(genre));
        bookDaoJpa.update(firstBook);

        val updatedBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);
        assertThat(updatedBook.getTitle()).isNotEqualTo(oldTitle).isEqualTo(NEW_BOOK_TITLE);
        assertThat(updatedBook.getAuthors()).isNotEqualTo(oldAuthors).containsOnly(author);
        assertThat(updatedBook.getGenres()).isNotEqualTo(oldGenres).containsOnly(genre);
    }

    @DisplayName("должен удалять книгу по её id")
    @Test
    void shouldDeleteBookById() {
        val firstBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);
        assertThat(firstBook).isNotNull();
        testEntityManager.detach(firstBook);

        bookDaoJpa.delete(FIRST_BOOK_ID);
        val deletedBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);
        assertThat(deletedBook).isNull();
    }

    @DisplayName("должен загружать информацию о книге по её id")
    @Test
    void shouldFindExpectedBookById() {
        val optionalActualBook = bookDaoJpa.getById(FIRST_BOOK_ID);
        val expectedBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);
        assertThat(optionalActualBook).isNotNull();
        assertThat(optionalActualBook).isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName("должен загружать информацию о книге по её имени")
    @Test
    void shouldFindExpectedBookByTitle() {
        val optionalActualBook = bookDaoJpa.getByTitle(FIRST_BOOK_TITLE);
        val expectedBook = testEntityManager.find(Book.class, FIRST_BOOK_ID);
        assertThat(optionalActualBook).isNotNull();
        assertThat(optionalActualBook).isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName("должен загружать список книг по автору с полной информацией о них")
    @Test
    void shouldReturnCorrectBooksListByAuthor() {
        val books = bookDaoJpa.getByAuthor(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME));
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS_FOR_AUTHOR1)
                .allMatch(s -> !s.getTitle().isEmpty())
                .allMatch(s -> s.getAuthors() != null && s.getAuthors().size() > 0 && s.getAuthors().get(0) != null)
                .allMatch(s -> s.getGenres() != null && s.getGenres().size() > 0 && s.getGenres().get(0) != null);
    }

    @DisplayName("должен загружать список книг по жанру с полной информацией о них")
    @Test
    void shouldReturnCorrectBooksListByGenre() {
        val books = bookDaoJpa.getByGenre(new Genre(SECOND_GENRE_ID, SECOND_GENRE_TITLE));
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS_FOR_GENRE2)
                .allMatch(s -> !s.getTitle().isEmpty())
                .allMatch(s -> s.getAuthors() != null && s.getAuthors().size() > 0 && s.getAuthors().get(0) != null)
                .allMatch(s -> s.getGenres() != null && s.getGenres().size() > 0 && s.getGenres().get(0) != null);
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectStudentsListWithAllInfo() {
        SessionFactory sessionFactory = testEntityManager
                .getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val books = bookDaoJpa.getAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(s -> !s.getTitle().isEmpty())
                .allMatch(s -> s.getAuthors() != null && s.getAuthors().size() > 0 && s.getAuthors().get(0) != null)
                .allMatch(s -> s.getGenres() != null && s.getGenres().size() > 0 && s.getGenres().get(0) != null);

        for (Book b: books) {
            System.out.println(b.toString());
        }
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }
}