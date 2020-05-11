package ru.otus.spring.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data Jpa для работы с комментариями ")
@DataJpaTest
class CommentRepositoryTest {

    private static final int EXPECTED_QUERIES_COUNT = 7;
    private static final int EXPECTED_NUMBER_OF_COMMENTS = 3;
    private static final int EXPECTED_NUMBER_OF_BOOK4_COMMENTS = 2;
    private static final long FIRST_COMMENT_ID = 1L;
    private static final long BOOK_WITH_COMMENTS_ID = 4L;

    private static final String NEW_COMMENT_TEXT = "Интересная книга";

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("должен возвращать количество комментариев")
    @Test
    void shouldReturnCountOfComments() {
        var actualCount = commentRepository.count();
        assertThat(actualCount).isEqualTo(EXPECTED_NUMBER_OF_COMMENTS);
    }

    @DisplayName("должен корректно сохранять информацию о комментарии")
    @Test
    void shouldSaveCommentInfo() {
        Book book = new Book(1, "Книга", List.of(), List.of(), List.of());
        val newComment = new Comment(0, NEW_COMMENT_TEXT, book);
        commentRepository.save(newComment);
        assertThat(newComment.getId()).isGreaterThan(0);

        val actualComment = testEntityManager.find(Comment.class, newComment.getId());
        assertThat(actualComment).isNotNull()
                .matches(s -> s.getText().equals(NEW_COMMENT_TEXT));
    }

    @DisplayName("должен удалять комментарий по его id")
    @Test
    void shouldDeleteCommentById() {
        val firstComment = testEntityManager.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(firstComment).isNotNull();
        testEntityManager.clear();

        commentRepository.delete(firstComment);
        val deletedComment = testEntityManager.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(deletedComment).isNull();
    }

    @DisplayName("должен удалять комментарий по id книги")
    @Test
    void shouldDeleteCommentByBookId() {
        val optionalBook = bookRepository.findById(BOOK_WITH_COMMENTS_ID);
        assertThat(optionalBook).isPresent();

        val comments = commentRepository.findAllByBook(optionalBook.get());
        assertThat(comments).isNotNull().hasSizeGreaterThan(0);
        testEntityManager.clear();

        commentRepository.deleteAllByBook(optionalBook.get());

        Book bookAfterDelete = testEntityManager.find(Book.class, BOOK_WITH_COMMENTS_ID);
        assertThat(bookAfterDelete).isNotNull();
        assertThat(bookAfterDelete.getComments()).isEmpty();
    }

    @DisplayName("должен загружать информацию о комментарии по его id")
    @Test
    void shouldFindExpectedCommentById() {
        val optionalActualComment = commentRepository.findById(FIRST_COMMENT_ID);
        val expectedComment = testEntityManager.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(optionalActualComment).isPresent();
        assertThat(optionalActualComment.get()).isEqualToComparingFieldByField(expectedComment);
    }

    @DisplayName("должен возвращать список комментариев к книге по id книги")
    @Test
    void shouldReturnCorrectCommentsListByBook() {
        val optionalBook = bookRepository.findById(BOOK_WITH_COMMENTS_ID);
        assertThat(optionalBook).isPresent();
        val comments = commentRepository.findAllByBook(optionalBook.get());
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOK4_COMMENTS)
                .allMatch(s -> !s.getText().isEmpty());
    }

    @DisplayName("должен загружать список всех комментариев")
    @Test
    void shouldReturnCorrectCommentsList() {
        SessionFactory sessionFactory = testEntityManager
                .getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val comments = commentRepository.findAll();
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(s -> !s.getText().isEmpty());
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }
}