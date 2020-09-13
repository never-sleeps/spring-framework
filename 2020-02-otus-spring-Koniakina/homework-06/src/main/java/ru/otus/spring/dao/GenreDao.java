package ru.otus.spring.dao;

import ru.otus.spring.model.Genre;

import java.util.List;

public interface GenreDao {

    long count();

    Genre create(Genre genre);

    Genre update(Genre genre);

    void delete(long id);

    Genre getById(long id);

    Genre getByTitle(String title);

    List<Genre> getAll();
}
