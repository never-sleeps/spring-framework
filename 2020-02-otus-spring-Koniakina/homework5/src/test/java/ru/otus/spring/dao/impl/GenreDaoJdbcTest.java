package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка получения данных об жанрах: ")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDao genreDao;

    private final int DEFAULT_GENRES_COUNT = 3;
    private final int EXISTING_GENRE_ID = 2;
    private final int NOT_EXISTING_GENRE_ID = 99;
    private final String EXISTING_GENRE_TITLE = "Сказка";
    private final String NOT_EXISTING_GENRE_TITLE = "Поэма";

    @DisplayName("получение количества жанров")
    @Test
    void shouldReturnExpectedGenreCount() {
        int count = genreDao.count();
        assertThat(count).isEqualTo(DEFAULT_GENRES_COUNT);
    }

    @DisplayName("добавление нового жанра")
    @Test
    void shouldCreateGenre() {
        Genre expected = genreDao.create(Genre.builder().title(NOT_EXISTING_GENRE_TITLE).build());
        Genre actual = genreDao.getById(expected.getId()).get();
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @DisplayName("существование жанра в базе")
    @Test
    void shouldReturnIsExist() {
        Genre genre = Genre.builder().title(EXISTING_GENRE_TITLE).build();
        assertTrue(genreDao.isExist(genre));
    }

    @DisplayName("отсутствие жанра в базе")
    @Test
    void shouldReturnIsNotExist() {
        Genre genre = Genre.builder().title(NOT_EXISTING_GENRE_TITLE).build();
        assertFalse(genreDao.isExist(genre));
    }

    @DisplayName("изменение данных жанра")
    @Test
    void shouldUpdateGenre() {
        Genre expected = new Genre(EXISTING_GENRE_ID, NOT_EXISTING_GENRE_TITLE);
        genreDao.update(expected);
        Genre actual = genreDao.getById(EXISTING_GENRE_ID).get();
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @DisplayName("удаление жанра")
    @Test
    void shouldDeleteGenre() {
        genreDao.delete(EXISTING_GENRE_ID);
        assertFalse(genreDao.getById(EXISTING_GENRE_ID).isPresent());
    }

    @DisplayName("получение жанра по id при существовании такого id")
    @Test
    void shouldGetGenreById() {
        Genre genre = genreDao.getById(EXISTING_GENRE_ID).get();
        assertThat(genre.getId()).isEqualTo(EXISTING_GENRE_ID);
    }

    @DisplayName("получение жанра по id при отсутствии такого id")
    @Test
    void shouldDoNotGetGenreById() {
        assertFalse(genreDao.getById(NOT_EXISTING_GENRE_ID).isPresent());
    }

    @DisplayName("получение жанра по name при существовании такого name")
    @Test
    void shouldGetGenreByName() {
        Genre genre = genreDao.getByTitle(EXISTING_GENRE_TITLE).get();
        assertThat(genre.getTitle()).isEqualTo(EXISTING_GENRE_TITLE);
    }

    @DisplayName("получение жанра по name при отсутствии такого name")
    @Test
    void shouldDoNotGetGenreByName() {
        assertFalse(genreDao.getByTitle(NOT_EXISTING_GENRE_TITLE).isPresent());
    }

    @DisplayName("получение списка жанров")
    @Test
    void shouldGetAllGenres() {
        List<Genre> genres = genreDao.getAll();
        assertThat(genres.size()).isEqualTo(DEFAULT_GENRES_COUNT);
    }
}