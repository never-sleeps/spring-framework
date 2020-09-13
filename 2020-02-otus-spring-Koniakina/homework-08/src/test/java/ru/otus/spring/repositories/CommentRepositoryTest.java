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
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data MongoDB для работы с комментариями ")
@DataMongoTest
@Import(MongoConfig.class)
class CommentRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_BOOK2_COMMENTS = 2;
    private static final String FIRST_COMMENT_ID = "1";
    private static final String FIRST_BOOK_ID = "1";
    private static final String BOOK_WITH_COMMENTS_ID = "3";
    private static final String NEW_COMMENT_TEXT = "Новый комментарий к книге";

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SpringMongock mongock;

    @BeforeEach
    void init() {
        mongock.execute();
    }

    @DisplayName("должен возвращать количество комментариев")
    @Test
    void shouldReturnCountOfComments() {
        var actualCount = commentRepository.count();
        val expectedCount = mongoTemplate.count(new Query(), Comment.class);
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("должен корректно сохранять информацию о комментарии")
    @Test
    void shouldSaveCommentInfo() {
        val book = bookRepository.findById(FIRST_BOOK_ID);
        val newComment = new Comment(NEW_COMMENT_TEXT, book.get());
        commentRepository.save(newComment);

        val actualComment = mongoTemplate.findById(newComment.getId(), Comment.class);
        assertThat(actualComment).isNotNull().isEqualTo(newComment);
    }

    @DisplayName("должен удалять комментарий по его id")
    @Test
    void shouldDeleteCommentById() {
        val firstComment = mongoTemplate.findById(FIRST_COMMENT_ID, Comment.class);
        assertThat(firstComment).isNotNull();

        commentRepository.delete(firstComment);
        val deletedComment = mongoTemplate.findById(FIRST_COMMENT_ID, Comment.class);
        assertThat(deletedComment).isNull();
    }

    @DisplayName("должен удалять комментарий по id книги")
    @Test
    void shouldDeleteCommentByBookId() {
        val optionalBook = bookRepository.findById(BOOK_WITH_COMMENTS_ID);
        assertThat(optionalBook).isPresent();
        val comments = commentRepository.findAllByBook(optionalBook.get());
        assertThat(comments).isNotNull().hasSizeGreaterThan(0);

        commentRepository.deleteAllByBook(optionalBook.get());

        Book bookAfterDelete = mongoTemplate.findById(BOOK_WITH_COMMENTS_ID, Book.class);
        assertThat(bookAfterDelete).isNotNull();
        assertThat(bookAfterDelete.getComments()).isEmpty();

        commentRepository.saveAll(comments);
    }

    @DisplayName("должен загружать информацию о комментарии по его id")
    @Test
    void shouldFindExpectedCommentById() {
        val optionalActualComment = commentRepository.findById(FIRST_COMMENT_ID);
        val expectedComment = mongoTemplate.findById(FIRST_COMMENT_ID, Comment.class);
        assertThat(optionalActualComment).isPresent();
        assertThat(optionalActualComment.get()).isEqualToComparingFieldByField(expectedComment);
    }

    @DisplayName("должен возвращать список комментариев к книге по id книги")
    @Test
    void shouldReturnCorrectCommentsListByBook() {
        val optionalBook = bookRepository.findById(BOOK_WITH_COMMENTS_ID);
        assertThat(optionalBook).isPresent();
        val comments = commentRepository.findAllByBook(optionalBook.get());
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOK2_COMMENTS)
                .allMatch(s -> !s.getText().isEmpty());
    }

    @DisplayName("должен загружать список всех комментариев")
    @Test
    void shouldReturnCorrectCommentsList() {
        val expectedList = commentRepository.findAll();
        val actualList = mongoTemplate.findAll(Comment.class);
        assertThat(expectedList).isNotNull().hasSameSizeAs(actualList).hasSameElementsAs(actualList);
    }
}