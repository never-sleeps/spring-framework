package ru.otus.spring.dao;

import ru.otus.spring.model.Comment;

import java.util.List;

public interface CommentDao {

    long count();

    Comment create(Comment comment);

    void delete(long id);

    void deleteByBook(long id);

    Comment getById(long id);

    List<Comment> getByBook(long id);

    List<Comment> getAll();
}
