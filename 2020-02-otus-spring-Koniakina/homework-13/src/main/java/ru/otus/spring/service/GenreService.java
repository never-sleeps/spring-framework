package ru.otus.spring.service;

import ru.otus.spring.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    long countGenres();

    Genre createGenre(String title);

    Genre updateGenre(String id, String title);

    void deleteGenre(String id);

    Optional<Genre> findGenreById(String id);

    Optional<Genre> findGenreByTitle(String title);

    List<Genre> findAllGenres();

    Genre getRegisteredGenre(String title);
}