package ru.otus.spring.service;


import ru.otus.spring.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    long countAuthors();

    Author createAuthor(String fullName);

    Author updateAuthor(String id, String fullName);

    void deleteAuthor(String id);

    Optional<Author> findAuthorById(String id);

    Optional<Author> findAuthorByName(String fullName);

    List<Author> findAllAuthors();

    Author getRegisteredAuthor(String fullName);
}
