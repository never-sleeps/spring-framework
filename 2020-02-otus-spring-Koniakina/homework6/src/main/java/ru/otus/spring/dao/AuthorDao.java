package ru.otus.spring.dao;

import ru.otus.spring.model.Author;

import java.util.List;


public interface AuthorDao {

    long count();

    Author create(Author author);

    Author update(Author author);

    void delete(long id);

    Author getById(long id);

    Author getByName(String fullName);

    List<Author> getAll();
}
