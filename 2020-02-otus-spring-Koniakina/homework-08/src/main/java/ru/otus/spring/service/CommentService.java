package ru.otus.spring.service;

import ru.otus.spring.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    long countComments();

    Comment createComment(String bookId, String text);

    void deleteComment(String id);

    void deleteCommentsByBook(String id);

    Optional <Comment> findCommentById(String id);

    List<Comment> findCommentsByBook(String id);

    List<Comment> findAllComments();
}
