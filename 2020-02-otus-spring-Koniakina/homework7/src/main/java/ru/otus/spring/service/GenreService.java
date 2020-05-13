package ru.otus.spring.service;

import ru.otus.spring.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    long countGenres();

    Genre createGenre(String title);

    Genre updateGenre(long id, String title);

    void deleteGenre(long id);

    Optional<Genre> findGenreById(long id);

    Genre findGenreByTitle(String title);

    List<Genre> findAllGenres();

    Genre getRegisteredGenre(String title);
}