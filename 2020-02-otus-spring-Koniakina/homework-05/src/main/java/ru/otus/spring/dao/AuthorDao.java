package ru.otus.spring.dao;

import ru.otus.spring.model.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorDao {

    int count();

    Author create(Author author);

    Author update(Author author);

    boolean isExist(Author author);

    void delete(long id);

    Optional<Author> getById(long id);

    Optional<Author> getByName(String fullName);

    List<Author> getAll();
}
