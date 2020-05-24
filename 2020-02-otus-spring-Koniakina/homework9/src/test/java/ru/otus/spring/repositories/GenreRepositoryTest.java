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
import ru.otus.spring.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data MongoDB для работы с жанрами ")
@DataMongoTest
@Import(MongoConfig.class)
class GenreRepositoryTest {

    private static final String FIRST_GENRE_ID = "1";
    private static final String NOT_USED_GENRE_ID = "6";
    private static final String FIRST_GENRE_TITLE = "Роман";
    private static final String NEW_GENRE_TITLE = "Поэзия";

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SpringMongock mongock;

    @BeforeEach
    void init() {
        mongock.execute();
    }

    @DisplayName("должен возвращать количество жанров")
    @Test
    void shouldReturnCountOfGenres() {
        val actualCount = genreRepository.count();
        val expectedCount = mongoTemplate.count(new Query(), Genre.class);
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("должен корректно сохранять информацию о жанре")
    @Test
    void shouldSaveGenreInfo() {
        val newGenre = new Genre(NEW_GENRE_TITLE);
        genreRepository.save(newGenre);

        val expectedGenre = mongoTemplate.findById(newGenre.getId(), Genre.class);
        assertThat(newGenre).isNotNull().isEqualToComparingFieldByField(expectedGenre)
                .matches(s -> s.getTitle().equals(NEW_GENRE_TITLE));
    }

    @DisplayName("должен изменять данные жанра по его id")
    @Test
    void shouldUpdateGenreById() {
        val firstGenre = mongoTemplate.findById(FIRST_GENRE_ID, Genre.class);
        String oldTitle = firstGenre.getTitle();

        firstGenre.setTitle(NEW_GENRE_TITLE);
        genreRepository.save(firstGenre);

        val updatedGenre = mongoTemplate.findById(FIRST_GENRE_ID, Genre.class);
        assertThat(updatedGenre.getTitle()).isNotEmpty()
                .isNotEqualTo(oldTitle).isEqualTo(NEW_GENRE_TITLE);

        firstGenre.setTitle(oldTitle);
        genreRepository.save(firstGenre);
    }

    @DisplayName("должен удалять неиспользуемый жанр по его id")
    @Test
    void shouldDeleteGenreById() {
        val genre = mongoTemplate.findById(NOT_USED_GENRE_ID, Genre.class);
        assertThat(genre).isNotNull();

        genreRepository.delete(genre);
        val deletedGenre = mongoTemplate.findById(NOT_USED_GENRE_ID, Genre.class);
        assertThat(deletedGenre).isNull();
    }

    @DisplayName("должен загружать информацию о жанре по его id")
    @Test
    void shouldFindExpectedGenreById() {
        val optionalActualGenre = genreRepository.findById(FIRST_GENRE_ID);
        val expectedGenre = mongoTemplate.findById(FIRST_GENRE_ID, Genre.class);
        assertThat(optionalActualGenre).isPresent();
        assertThat(optionalActualGenre.get()).isEqualToComparingFieldByField(expectedGenre);
    }

    @DisplayName("должен загружать информацию о жанре по его имени")
    @Test
    void shouldFindExpectedGenreByTitle() {
        val optionalActualGenre = genreRepository.findByTitle(FIRST_GENRE_TITLE);
        val expectedGenre = mongoTemplate.findById(FIRST_GENRE_ID, Genre.class);
        assertThat(optionalActualGenre).isPresent();
        assertThat(optionalActualGenre.get()).isEqualToComparingFieldByField(expectedGenre);
    }

    @DisplayName("должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresListWithAllInfo() {
        val listExpected = genreRepository.findAll();
        val listActual = mongoTemplate.findAll(Genre.class);
        assertThat(listExpected).hasSameSizeAs(listActual).hasSameElementsAs(listActual);
    }

}