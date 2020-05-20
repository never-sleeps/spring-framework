package ru.otus.spring.repositories;

import com.github.cloudyrock.mongock.SpringMongock;
import config.MongoConfig;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.model.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data MongoDB для работы с авторами")
@DataMongoTest
@Import(MongoConfig.class)
@ExtendWith(SpringExtension.class)
class AuthorRepositoryTest {

    private static final String FIRST_AUTHOR_ID = "1";
    private static final String NOT_USED_AUTHOR_ID = "6";
    private static final String FIRST_AUTHOR_FULLNAME = "Илья Ильф";
    private static final String NEW_AUTHOR_FULLNAME = "А.С. Пушкин";

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SpringMongock mongock;

    @BeforeEach
    void init() {
        mongock.execute();
    }

    @DisplayName("должен возвращать количество авторов")
    @Test
    void shouldReturnCountOfAuthors() {
        val actualCount = authorRepository.count();
        val expectedCount = mongoTemplate.count(new Query(), Author.class);
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("должен корректно сохранять информацию об авторе")
    @Test
    void shouldSaveAuthorInfo() {
        val newAuthor = new Author(NEW_AUTHOR_FULLNAME);
        authorRepository.save(newAuthor);

        val expectedAuthor = mongoTemplate.findById(newAuthor.getId(), Author.class);
        assertThat(newAuthor).isNotNull().isEqualToComparingFieldByField(expectedAuthor)
                .matches(s -> s.getFullName().equals(NEW_AUTHOR_FULLNAME));
    }

    @DisplayName("должен изменять данные автора по его id")
    @Test
    void shouldUpdateAuthorById() {
        val firstAuthor = mongoTemplate.findById(FIRST_AUTHOR_ID, Author.class);
        String oldName = firstAuthor.getFullName();

        firstAuthor.setFullName(NEW_AUTHOR_FULLNAME);
        authorRepository.save(firstAuthor);

        val updatedAuthor = mongoTemplate.findById(FIRST_AUTHOR_ID, Author.class);
        assertThat(updatedAuthor.getFullName()).isNotEmpty()
                .isNotEqualTo(oldName).isEqualTo(NEW_AUTHOR_FULLNAME);

        firstAuthor.setFullName(oldName);
        authorRepository.save(firstAuthor);
    }

    @DisplayName("должен удалять автора, у которого нет книг, по его id")
    @Test
    void shouldDeleteAuthorById() {
        val author = mongoTemplate.findById(NOT_USED_AUTHOR_ID, Author.class);
        assertThat(author).isNotNull();

        authorRepository.delete(author);
        val deletedAuthor = mongoTemplate.findById(NOT_USED_AUTHOR_ID, Author.class);
        assertThat(deletedAuthor).isNull();
    }

    @DisplayName("должен загружать информацию об авторе по его id")
    @Test
    void shouldFindExpectedAuthorById() {
        val optionalActualAuthor = authorRepository.findById(FIRST_AUTHOR_ID);
        val expectedAuthor = mongoTemplate.findById(FIRST_AUTHOR_ID, Author.class);
        assertThat(optionalActualAuthor).isPresent();
        assertThat(optionalActualAuthor.get()).isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName("должен загружать информацию об авторе по его имени")
    @Test
    void shouldFindExpectedAuthorByName() {
        val optionalActualAuthor = authorRepository.findByFullName(FIRST_AUTHOR_FULLNAME);
        val expectedAuthor = mongoTemplate.findById(FIRST_AUTHOR_ID, Author.class);
        assertThat(optionalActualAuthor).isPresent();
        assertThat(optionalActualAuthor.get()).isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsListWithAllInfo() {
        val listExpected = authorRepository.findAll();
        val listActual = mongoTemplate.findAll(Author.class);
        assertThat(listExpected).hasSameSizeAs(listActual).hasSameElementsAs(listActual);
    }
}