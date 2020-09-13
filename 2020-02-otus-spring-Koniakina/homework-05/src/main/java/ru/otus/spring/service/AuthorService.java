package ru.otus.spring.service;


import ru.otus.spring.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    int countAuthors();

    Author createAuthor(String fullName);

    Author updateAuthor(long id, String fullName);

    void deleteAuthor(long id);

    Optional<Author> getAuthorById(long id);

    Optional<Author> getAuthorByName(String fullName);

    List<Author> getAllAuthors();

    Author getRegisteredAuthor(String fullName);
}
