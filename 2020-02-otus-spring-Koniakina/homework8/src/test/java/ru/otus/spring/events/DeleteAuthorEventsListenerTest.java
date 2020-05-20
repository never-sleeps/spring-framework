package ru.otus.spring.events;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Слушатель события удаления автора ")
@DataMongoTest
@Import(MongoConfig.class)
@ExtendWith(SpringExtension.class)
class DeleteAuthorEventsListenerTest {

    private static final String FIRST_AUTHOR_ID = "1";

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private SpringMongock mongock;

    @BeforeEach
    void init() {
        mongock.execute();
    }

    @DisplayName("должен удалять книги автора перед удалением самого автора")
    @Test
    void shouldDeleteBooksBeforeDeleteAuthors() {
        val author = authorRepository.findById(FIRST_AUTHOR_ID).get();
        val bookListBeforeDelete = bookRepository.findAllByAuthorsContains(author);
        assertThat(bookListBeforeDelete).isNotNull().hasSizeGreaterThan(0);
        authorRepository.delete(author);
        val bookListAfterDelete = bookRepository.findAllByAuthorsContains(author);
        assertThat(bookListAfterDelete).isNull();
    }
}