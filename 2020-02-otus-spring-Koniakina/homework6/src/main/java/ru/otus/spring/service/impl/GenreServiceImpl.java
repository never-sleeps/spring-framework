package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.exception.EntityDeleteException;
import ru.otus.spring.exception.EntityUpdateException;
import ru.otus.spring.model.Genre;
import ru.otus.spring.service.GenreService;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Override
    public long countGenres() {
        return genreDao.count();
    }

    @Override
    public Genre createGenre(String title) {
        Genre newGenre = Genre.builder().title(title).build();
        return genreDao.create(newGenre);
    }

    @Override
    public Genre updateGenre(long id, String title) {
        Genre genre = genreDao.getById(id);
        if (genre == null) {
            throw new EntityUpdateException(String.format("Жанр с id '%s' не найден", id));
        } else {
            genre.setTitle(title);
            return genreDao.update(genre);
        }
    }

    @Override
    public void deleteGenre(long id) {
        Genre genre = genreDao.getById(id);
        if (genre == null) {
            throw new EntityDeleteException(String.format("Жанр с id '%s' не найден", id));
        }
        genreDao.delete(id);
    }

    @Override
    public Genre getGenreById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public Genre getGenreByTitle(String title) {
        return genreDao.getByTitle(title);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAll();
    }

    public Genre getRegisteredGenre(String title) {
        Genre existingGenre = genreDao.getByTitle(title);
        return (existingGenre == null) ? createGenre(title) : existingGenre;
    }
}
