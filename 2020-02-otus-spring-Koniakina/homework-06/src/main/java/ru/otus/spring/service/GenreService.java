package ru.otus.spring.service;

import ru.otus.spring.model.Genre;

import java.util.List;

public interface GenreService {

    long countGenres();

    Genre createGenre(String title);

    Genre updateGenre(long id, String title);

    void deleteGenre(long id);

    Genre getGenreById(long id);

    Genre getGenreByTitle(String title);

    List<Genre> getAllGenres();

    Genre getRegisteredGenre(String title);
}