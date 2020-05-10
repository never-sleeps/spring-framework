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
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import(CommentDaoJpa.class)
class CommentDaoJpaTest {

    private static final int EXPECTED_QUERIES_COUNT = 7;
    private static final int EXPECTED_NUMBER_OF_COMMENTS = 3;
    private static final int EXPECTED_NUMBER_OF_BOOK4_COMMENTS = 2;
    private static final long FIRST_COMMENT_ID = 1L;
    private static final long BOOK_WITH_COMMENTS_ID = 4L;

    private static final String NEW_COMMENT_TEXT = "Интересная книга";

    @Autowired
    private CommentDaoJpa commentDaoJpa;
    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("должен возвращать количество комментариев")
    @Test
    void shouldReturnCountOfComments() {
        var actualCount = commentDaoJpa.count();
        assertThat(actualCount).isEqualTo(EXPECTED_NUMBER_OF_COMMENTS);
    }

    @DisplayName("должен корректно сохранять информацию о комментарии")
    @Test
    void shouldSaveCommentInfo() {
        Book book = new Book(1, "Книга", List.of(), List.of(), List.of());
        val newComment = new Comment(0, NEW_COMMENT_TEXT, book);
        commentDaoJpa.create(newComment);
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

        commentDaoJpa.delete(FIRST_COMMENT_ID);
        val deletedComment = testEntityManager.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(deletedComment).isNull();
    }

    @DisplayName("должен удалять комментарий по id книги")
    @Test
    void shouldDeleteCommentByBookId() {
        val comments = commentDaoJpa.getByBook(BOOK_WITH_COMMENTS_ID);
        assertThat(comments).isNotNull().hasSizeGreaterThan(0);
        testEntityManager.clear();

        commentDaoJpa.deleteByBook(BOOK_WITH_COMMENTS_ID);

        Book bookAfterDelete = testEntityManager.find(Book.class, BOOK_WITH_COMMENTS_ID);
        assertThat(bookAfterDelete).isNotNull();
        assertThat(bookAfterDelete.getComments()).isEmpty();
    }

    @DisplayName("должен загружать информацию о комментарии по его id")
    @Test
    void shouldFindExpectedCommentById() {
        val optionalActualComment = commentDaoJpa.getById(FIRST_COMMENT_ID);
        val expectedComment = testEntityManager.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(optionalActualComment).isNotNull();
        assertThat(optionalActualComment).isEqualToComparingFieldByField(expectedComment);
    }

    @DisplayName("должен возвращать список комментариев к книге по id книги")
    @Test
    void shouldReturnCorrectCommentsListByBook() {
        val comments = commentDaoJpa.getByBook(BOOK_WITH_COMMENTS_ID);
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOK4_COMMENTS)
                .allMatch(s -> !s.getText().isEmpty());
    }

    @DisplayName("должен загружать список всех комментариев")
    @Test
    void shouldReturnCorrectCommentsList() {
        SessionFactory sessionFactory = testEntityManager
                .getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val comments = commentDaoJpa.getAll();
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(s -> !s.getText().isEmpty());
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }
}