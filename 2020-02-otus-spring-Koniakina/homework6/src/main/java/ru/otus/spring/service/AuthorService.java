package ru.otus.spring.service;


import ru.otus.spring.model.Author;

import java.util.List;

public interface AuthorService {

    long countAuthors();

    Author createAuthor(String fullName);

    Author updateAuthor(long id, String fullName);

    void deleteAuthor(long id);

    Author getAuthorById(long id);

    Author getAuthorByName(String fullName);

    List<Author> getAllAuthors();

    Author getRegisteredAuthor(String fullName);
}
