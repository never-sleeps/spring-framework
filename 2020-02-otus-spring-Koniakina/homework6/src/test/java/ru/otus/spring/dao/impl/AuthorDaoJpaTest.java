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

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long NOT_USED_AUTHOR_ID = 7L;
    private static final String FIRST_AUTHOR_FULLNAME = "Илья Ильф";
    private static final int EXPECTED_NUMBER_OF_AUTHORS = 7;
    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final String NEW_AUTHOR_FULLNAME = "А.С. Пушкин";

    @Autowired
    private AuthorDaoJpa authorDaoJpa;
    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("должен возвращать количество авторов")
    @Test
    void shouldReturnCountOfAuthors() {
        var actualCount = authorDaoJpa.count();
        assertThat(actualCount).isEqualTo(EXPECTED_NUMBER_OF_AUTHORS);
    }

    @DisplayName("должен корректно сохранять информацию об авторе")
    @Test
    void shouldSaveAuthorInfo() {
        val newAuthor = new Author(0, NEW_AUTHOR_FULLNAME);
        authorDaoJpa.create(newAuthor);
        assertThat(newAuthor.getId()).isGreaterThan(0);

        val actualAuthor = testEntityManager.find(Author.class, newAuthor.getId());
        assertThat(actualAuthor).isNotNull()
                .matches(s -> s.getFullName().equals(NEW_AUTHOR_FULLNAME));
    }

    @DisplayName("должен изменять данные автора по его id")
    @Test
    void shouldUpdateAuthorById() {
        val firstAuthor = testEntityManager.find(Author.class, FIRST_AUTHOR_ID);
        String oldName = firstAuthor.getFullName();
        testEntityManager.detach(firstAuthor);

        firstAuthor.setFullName(NEW_AUTHOR_FULLNAME);
        authorDaoJpa.update(firstAuthor);

        val updatedAuthor = testEntityManager.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(updatedAuthor.getFullName()).isNotEqualTo(oldName).isEqualTo(NEW_AUTHOR_FULLNAME);
    }

    @DisplayName("должен удалять автора, у которого нет книг, по его id")
    @Test
    void shouldDeleteAuthorById() {
        val author = testEntityManager.find(Author.class, NOT_USED_AUTHOR_ID);
        assertThat(author).isNotNull();
        testEntityManager.detach(author);

        authorDaoJpa.delete(NOT_USED_AUTHOR_ID);
        val deletedAuthor = testEntityManager.find(Author.class, NOT_USED_AUTHOR_ID);
        assertThat(deletedAuthor).isNull();
    }

    @DisplayName("должен загружать информацию об авторе по его id")
    @Test
    void shouldFindExpectedAuthorById() {
        val optionalActualAuthor = authorDaoJpa.getById(FIRST_AUTHOR_ID);
        val expectedAuthor = testEntityManager.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(optionalActualAuthor).isNotNull();
        assertThat(optionalActualAuthor).isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName("должен загружать информацию об авторе по его имени")
    @Test
    void shouldFindExpectedAuthorByName() {
        val optionalActualAuthor = authorDaoJpa.getByName(FIRST_AUTHOR_FULLNAME);
        val expectedAuthor = testEntityManager.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(optionalActualAuthor).isNotNull();
        assertThat(optionalActualAuthor).isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsListWithAllInfo() {
        SessionFactory sessionFactory = testEntityManager
                .getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val authors = authorDaoJpa.getAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(s -> !s.getFullName().isEmpty());
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }
}