package ru.otus.spring.dao;

import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

public interface BookDao {

    long count();

    Book create(Book book);

    Book update(Book book);

    void delete(long id);

    Book getById(long id);

    Book getByTitle(String title);

    List<Book> getByAuthor(Author author);

    List<Book> getByGenre(Genre genre);

    List<Book> getAll();
}
