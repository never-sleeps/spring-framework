package ru.otus.spring.repositories;

import com.github.cloudyrock.mongock.SpringMongock;
import config.MongoConfig;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data MongoDB для работы с книгами ")
@DataMongoTest
@Import(MongoConfig.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SpringMongock mongock;

    private static final String FIRST_BOOK_ID = "1";
    private static final String FIRST_BOOK_TITLE = "Двенадцать стульев";
    private static final String FIRST_AUTHOR_ID = "1";
    private static final String FIRST_AUTHOR_NAME = "Илья Ильф";
    private static final String SECOND_GENRE_ID = "2";
    private static final String SECOND_GENRE_TITLE = "Приключения";
    private static final String NEW_BOOK_TITLE = "Как создавался Робинзон";
    private static final int EXPECTED_NUMBER_OF_BOOKS_FOR_GENRE2 = 2;
    private static final int EXPECTED_NUMBER_OF_BOOKS_FOR_AUTHOR1 = 1;

    @BeforeEach
    void init() {
        mongock.execute();
    }

    @DisplayName("должен возвращать количество книг")
    @Test
    void shouldReturnCountOfBooks() {
        val actualCount = bookRepository.count();
        val expectedCount = mongoTemplate.count(new Query(), Book.class);
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("должен корректно сохранять всю информацию о книге")
    @Test
    void shouldSaveAllBookInfo() {
        val authors = List.of(new Author("1", FIRST_AUTHOR_NAME));
        val genres = List.of(new Genre("1", SECOND_GENRE_TITLE));
        val newBook = new Book(NEW_BOOK_TITLE, authors, genres);
        bookRepository.save(newBook);

        assertThat(newBook.getId()).isNotEmpty();
        val actualBook = mongoTemplate.findById(newBook.getId(), Book.class);
        assertThat(actualBook).isNotNull().matches(s -> !s.getTitle().isEmpty())
                .matches(s -> s.getAuthors() != null && s.getAuthors().size() > 0)
                .matches(s -> s.getGenres() != null && s.getGenres().size() > 0);
        bookRepository.delete(newBook);
    }

    @DisplayName("должен изменять информацию о книге по её id")
    @Test
    void shouldUpdateBookById() {
        Book firstBook = mongoTemplate.findById(FIRST_BOOK_ID, Book.class);
        val oldTitle = firstBook.getTitle();
        val oldAuthors = firstBook.getAuthors();
        val oldGenres = firstBook.getGenres();

        val newAuthor = new Author("5", "Стивен Хокинг");
        val newGenre = new Genre("2", "Приключения");
        firstBook.setTitle(NEW_BOOK_TITLE);
        firstBook.setAuthors(List.of(newAuthor));
        firstBook.setGenres(List.of(newGenre));
        bookRepository.save(firstBook);

        val updatedBook = mongoTemplate.findById(FIRST_BOOK_ID, Book.class);
        assertThat(updatedBook.getTitle()).isNotEqualTo(oldTitle).isEqualTo(NEW_BOOK_TITLE);
        assertThat(updatedBook.getAuthors()).isNotEqualTo(oldAuthors).containsOnly(newAuthor);
        assertThat(updatedBook.getGenres()).isNotEqualTo(oldGenres).containsOnly(newGenre);

        firstBook.setTitle(oldTitle);
        firstBook.setAuthors(oldAuthors);
        firstBook.setGenres(oldGenres);
        bookRepository.save(firstBook);
    }

    @DisplayName("должен удалять книгу по её id")
    @Test
    void shouldDeleteBookById() {
        val book = mongoTemplate.findById(FIRST_BOOK_ID, Book.class);
        assertThat(book).isNotNull();

        bookRepository.delete(book);
        val deletedBook = mongoTemplate.findById(FIRST_BOOK_ID, Book.class);
        assertThat(deletedBook).isNull();

        bookRepository.save(book);
    }

    @DisplayName("должен загружать информацию о книге по её id")
    @Test
    void shouldFindExpectedBookById() {
        val optionalActualBook = bookRepository.findById(FIRST_BOOK_ID);
        val expectedBook = mongoTemplate.findById(FIRST_BOOK_ID, Book.class);
        assertThat(optionalActualBook).isPresent();
        assertThat(optionalActualBook.get()).isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName("должен загружать информацию о книге по её имени")
    @Test
    void shouldFindExpectedBookByTitle() {
        val optionalActualBook = bookRepository.findByTitle(FIRST_BOOK_TITLE);
        val expectedBook = mongoTemplate.findById(FIRST_BOOK_ID, Book.class);
        assertThat(optionalActualBook).isNotNull();
        assertThat(optionalActualBook).isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName("должен загружать список книг по автору с полной информацией о них")
    @Test
    void shouldReturnCorrectBooksListByAuthor() {
        val books = bookRepository.findAllByAuthorsContains(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME));
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS_FOR_AUTHOR1)
                .allMatch(s -> !s.getTitle().isEmpty())
                .allMatch(s -> s.getAuthors() != null && s.getAuthors().size() > 0 && s.getAuthors().get(0) != null)
                .allMatch(s -> s.getGenres() != null && s.getGenres().size() > 0 && s.getGenres().get(0) != null);
    }

    @DisplayName("должен загружать список книг по жанру с полной информацией о них")
    @Test
    void shouldReturnCorrectBooksListByGenre() {
        val books = bookRepository.findAllByGenresContains(new Genre(SECOND_GENRE_ID, SECOND_GENRE_TITLE));
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS_FOR_GENRE2)
                .allMatch(s -> !s.getTitle().isEmpty())
                .allMatch(s -> s.getAuthors() != null && s.getAuthors().size() > 0 && s.getAuthors().get(0) != null)
                .allMatch(s -> s.getGenres() != null && s.getGenres().size() > 0 && s.getGenres().get(0) != null);
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectStudentsListWithAllInfo() {
        val listActual = mongoTemplate.findAll(Book.class);
        val listExpected = bookRepository.findAll();
        assertThat(listExpected).isNotNull().hasSameSizeAs(listActual).hasSameElementsAs(listActual);
        for (Book b: listExpected) {
            System.out.println(b.toString());
        }
    }
}