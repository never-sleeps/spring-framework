package ru.otus.spring.dao;

import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    int count();

    Book create(Book book);

    Book update(Book book);

    boolean isExist(Book book);

    void delete(long id);

    Optional<Book> getById(long id);

    Optional<Book> getByTitle(String title);

    List<Book> getByAuthor(String fullName);

    List<Book> getByGenre(String title);

    List<Book> getAll();
}
