package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.exception.EntityDeleteException;
import ru.otus.spring.exception.EntityUpdateException;
import ru.otus.spring.model.Genre;
import ru.otus.spring.repositories.GenreRepository;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public long countGenres() {
        return genreRepository.count();
    }

    @Override
    public Genre createGenre(String title) {
        Genre newGenre = Genre.builder().title(title).build();
        return genreRepository.save(newGenre);
    }

    @Override
    public Genre updateGenre(String id, String title) {
        Optional<Genre> optionalGenre = findGenreById(id);
        if (optionalGenre.isEmpty()) {
            throw new EntityUpdateException(String.format("Жанр с id '%s' не найден", id));
        }
        Genre genre = optionalGenre.get();
        genre.setTitle(title);
        return genreRepository.save(genre);
    }

    @Override
    public void deleteGenre(String id) {
        Optional<Genre> optionalGenre = findGenreById(id);
        if (optionalGenre.isEmpty()) {
            throw new EntityDeleteException(String.format("Жанр с id '%s' не найден", id));
        }
        genreRepository.delete(optionalGenre.get());
    }

    @Override
    public Optional<Genre> findGenreById(String id) {
        return genreRepository.findById(id);
    }

    @Override
    public Optional<Genre> findGenreByTitle(String title) {
        return genreRepository.findByTitle(title);
    }

    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getRegisteredGenre(String title) {
        Optional<Genre> existingGenre = findGenreByTitle(title);
        return existingGenre.orElseGet(() -> createGenre(title));
    }
}
