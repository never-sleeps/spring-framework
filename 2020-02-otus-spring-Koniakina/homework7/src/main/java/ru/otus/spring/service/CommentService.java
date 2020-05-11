package ru.otus.spring.service;

import ru.otus.spring.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    long countComments();

    Comment createComment(long bookId, String text);

    void deleteComment(long id);

    void deleteCommentByBook(long id);

    Optional <Comment> findCommentById(long id);

    List<Comment> findCommentsByBook(long id);

    List<Comment> findAllComments();
}
