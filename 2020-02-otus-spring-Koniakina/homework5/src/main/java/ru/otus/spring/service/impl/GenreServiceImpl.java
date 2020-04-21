package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.exception.CreatingEntityException;
import ru.otus.spring.model.Genre;
import ru.otus.spring.service.GenreService;
import ru.otus.spring.view.Console;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Override
    public int countGenres() {
        return genreDao.count();
    }

    @Override
    public Genre createGenre(String title) {
        Genre newGenre = Genre.builder().title(title).build();
        if (genreDao.isExist(newGenre)) {
            throw new CreatingEntityException(String.format("%s уже существует", newGenre.toString()));
        }
        return genreDao.create(newGenre);
    }

    @Override
    public Genre updateGenre(Genre genre) {
        return genreDao.update(genre);
    }

    @Override
    public void deleteGenre(long id) {
        genreDao.delete(id);
    }

    @Override
    public Optional<Genre> getGenreById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public Optional<Genre> getGenreByTitle(String name) {
        return genreDao.getByTitle(name);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAll();
    }

    @Override
    public Genre getRegisteredGenre(String title) {
        Optional<Genre> existingAuthor = getGenreByTitle(title);
        return existingAuthor.orElseGet(() -> createGenre(title));
    }
}
