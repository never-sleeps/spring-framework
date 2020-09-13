package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка получения данных об авторах: ")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDao authorDao;

    private final int DEFAULT_AUTHORS_COUNT = 3;
    private final int EXISTING_AUTHOR_ID = 2;
    private final int NOT_EXISTING_AUTHOR_ID = 99;
    private final String EXISTING_AUTHOR_FULLNAME = "Л.Н.Толстой";
    private final String NOT_EXISTING_AUTHOR_FULLNAME = "М.Ю.Лермонтов";



    @DisplayName("получение количества авторов")
    @Test
    void shouldReturnExpectedAuthorCount() {
        int count = authorDao.count();
        assertThat(count).isEqualTo(DEFAULT_AUTHORS_COUNT);
    }

    @DisplayName("добавление нового автора")
    @Test
    void shouldCreateAuthor() {
        Author expected = authorDao.create(Author.builder().fullName(NOT_EXISTING_AUTHOR_FULLNAME).build());
        Author actual = authorDao.getById(expected.getId()).get();
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @DisplayName("существование автора в базе")
    @Test
    void shouldReturnIsExist() {
        Author author = Author.builder().fullName(EXISTING_AUTHOR_FULLNAME).build();
        assertTrue(authorDao.isExist(author));
    }

    @DisplayName("отсутствие автора в базе")
    @Test
    void shouldReturnIsNotExist() {
        Author author = Author.builder().fullName(NOT_EXISTING_AUTHOR_FULLNAME).build();
        assertFalse(authorDao.isExist(author));
    }

    @DisplayName("изменение данных автора")
    @Test
    void shouldUpdateAuthor() {
        Author expected = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_FULLNAME);
        authorDao.update(expected);
        Author actual = authorDao.getById(EXISTING_AUTHOR_ID).get();
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @DisplayName("удаление автора")
    @Test
    void shouldDeleteAuthor() {
        authorDao.delete(EXISTING_AUTHOR_ID);
        assertFalse(authorDao.getById(EXISTING_AUTHOR_ID).isPresent());
    }

    @DisplayName("получение автора по id при существовании такого id")
    @Test
    void shouldGetAuthorById() {
        Author author = authorDao.getById(EXISTING_AUTHOR_ID).get();
        assertThat(author.getId()).isEqualTo(EXISTING_AUTHOR_ID);
    }

    @DisplayName("получение автора по id при отсутствии такого id")
    @Test
    void shouldDoNotGetAuthorById() {
        assertFalse(authorDao.getById(NOT_EXISTING_AUTHOR_ID).isPresent());
    }

    @DisplayName("получение автора по name при существовании такого name")
    @Test
    void shouldGetAuthorByName() {
        Author author = authorDao.getByName(EXISTING_AUTHOR_FULLNAME).get();
        assertThat(author.getFullName()).isEqualTo(EXISTING_AUTHOR_FULLNAME);
    }

    @DisplayName("получение автора по name при отсутствии такого name")
    @Test
    void shouldDoNotGetAuthorByName() {
        assertFalse(authorDao.getByName(NOT_EXISTING_AUTHOR_FULLNAME).isPresent());
    }

    @DisplayName("получение списка авторов")
    @Test
    void shouldGetAllAuthors() {
        List<Author> authors = authorDao.getAll();
        assertThat(authors.size()).isEqualTo(DEFAULT_AUTHORS_COUNT);
    }
}