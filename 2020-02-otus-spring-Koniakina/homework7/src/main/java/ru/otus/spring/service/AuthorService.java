package ru.otus.spring.service;


import ru.otus.spring.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    long countAuthors();

    Author createAuthor(String fullName);

    Author updateAuthor(long id, String fullName);

    void deleteAuthor(long id);

    Optional<Author> findAuthorById(long id);

    Author findAuthorByName(String fullName);

    List<Author> findAllAuthors();

    Author getRegisteredAuthor(String fullName);
}
