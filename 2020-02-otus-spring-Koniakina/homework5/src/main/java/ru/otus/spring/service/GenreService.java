package ru.otus.spring.service;

import ru.otus.spring.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    int countGenres();

    Genre createGenre(String title);

    Genre updateGenre(long id, String title);

    void deleteGenre(long id);

    Optional<Genre> getGenreById(long id);

    Optional<Genre> getGenreByTitle(String title);

    List<Genre> getAllGenres();

    Genre getRegisteredGenre(String title);
}