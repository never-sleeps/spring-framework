package ru.otus.spring.dao;

import ru.otus.spring.model.Author;
import ru.otus.spring.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    int count();

    Genre create(Genre genre);

    Genre update(Genre genre);

    void delete(long id);

    boolean isExist(Genre author);

    Optional<Genre> getById(long id);

    Optional<Genre> getByTitle(String title);

    List<Genre> getAll();
}
