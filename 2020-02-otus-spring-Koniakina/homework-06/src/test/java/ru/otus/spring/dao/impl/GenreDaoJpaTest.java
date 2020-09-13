package ru.otus.spring.dao.impl;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.model.Genre;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {

    private static final long FIRST_GENRE_ID = 1L;
    private static final String FIRST_GENRE_TITLE = "Роман";
    private static final String NEW_GENRE_TITLE = "Поэзия";
    private static final int EXPECTED_NUMBER_OF_GENRES = 5;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private GenreDaoJpa genreDaoJpa;
    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("должен возвращать количество жанров")
    @Test
    void shouldReturnCountOfGenres() throws SQLException {
        var actualCount = genreDaoJpa.count();
        assertThat(actualCount).isEqualTo(EXPECTED_NUMBER_OF_GENRES);
    }

    @DisplayName("должен корректно сохранять информацию о жанре")
    @Test
    void shouldSaveGenreInfo() {
        val newGenre = new Genre(0, NEW_GENRE_TITLE);
        genreDaoJpa.create(newGenre);
        assertThat(newGenre.getId()).isGreaterThan(0);

        val actualGenre = testEntityManager.find(Genre.class, newGenre.getId());
        assertThat(actualGenre).isNotNull()
                .matches(s -> s.getTitle().equals(NEW_GENRE_TITLE));
    }

    @DisplayName("должен изменять данные жанра по его id")
    @Test
    void shouldUpdateGenreById() {
        val firstGenre = testEntityManager.find(Genre.class, FIRST_GENRE_ID);
        String oldTitle = firstGenre.getTitle();
        testEntityManager.detach(firstGenre);

        firstGenre.setTitle(NEW_GENRE_TITLE);
        genreDaoJpa.update(firstGenre);

        val updatedGenre = testEntityManager.find(Genre.class, FIRST_GENRE_ID);
        assertThat(updatedGenre.getTitle()).isNotEqualTo(oldTitle).isEqualTo(NEW_GENRE_TITLE);
    }

    @DisplayName("должен удалять неиспользуемый жанр по его id")
    @Test
    void shouldDeleteGenreById() {
        val firstGenre = testEntityManager.find(Genre.class, FIRST_GENRE_ID);
        assertThat(firstGenre).isNotNull();
        testEntityManager.detach(firstGenre);

        genreDaoJpa.delete(FIRST_GENRE_ID);
        val deletedGenre = testEntityManager.find(Genre.class, FIRST_GENRE_ID);
        assertThat(deletedGenre).isNull();
    }

    @DisplayName("должен загружать информацию о жанре по его id")
    @Test
    void shouldFindExpectedGenreById() {
        val optionalActualGenre = genreDaoJpa.getById(FIRST_GENRE_ID);
        val expectedGenre = testEntityManager.find(Genre.class, FIRST_GENRE_ID);
        assertThat(optionalActualGenre).isNotNull();
        assertThat(optionalActualGenre).isEqualToComparingFieldByField(expectedGenre);
    }

    @DisplayName("должен загружать информацию о жанре по его имени")
    @Test
    void shouldFindExpectedGenreByTitle() {
        val optionalActualGenre = genreDaoJpa.getByTitle(FIRST_GENRE_TITLE);
        val expectedGenre = testEntityManager.find(Genre.class, FIRST_GENRE_ID);
        assertThat(optionalActualGenre).isNotNull();
        assertThat(optionalActualGenre).isEqualToComparingFieldByField(expectedGenre);
    }

    @DisplayName("должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresListWithAllInfo() {
        SessionFactory sessionFactory = testEntityManager
                .getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val genres = genreDaoJpa.getAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES)
                .allMatch(s -> !s.getTitle().isEmpty());
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }
}