package ru.otus.spring.service;

import ru.otus.spring.model.Comment;

import java.util.List;

public interface CommentService {

    long countComments();

    Comment createComment(long bookId, String text);

    void deleteComment(long id);

    void deleteCommentByBook(long id);

    Comment getCommentById(long id);

    List<Comment> getCommentsByBook(long id);

    List<Comment> getAllComments();
}
