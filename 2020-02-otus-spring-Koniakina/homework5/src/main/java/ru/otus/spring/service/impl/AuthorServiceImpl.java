package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.exception.CreatingEntityException;
import ru.otus.spring.exception.UpdatingEntityException;
import ru.otus.spring.model.Author;
import ru.otus.spring.service.AuthorService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    @Override
    public int countAuthors() {
        return authorDao.count();
    }

    @Override
    public Author createAuthor(String fullName) {
        Author newAuthor = Author.builder().fullName(fullName).build();
        if (authorDao.isExist(newAuthor)) {
            throw new CreatingEntityException(String.format("%s уже существует", newAuthor.toString()));
        }
        return authorDao.create(newAuthor);
    }

    @Override
    public Author getRegisteredAuthor(String fullName){
        Optional<Author> existingAuthor = getAuthorByName(fullName);
        return existingAuthor.orElseGet(() -> createAuthor(fullName));
    }

    @Override
    public Author updateAuthor(long id, String fullName) {
        if (!authorDao.getById(id).isPresent()) {
            return null;
        } else {
            Author newAuthor = Author.builder().id(id).fullName(fullName).build();
            if (authorDao.isExist(newAuthor)) {
                throw new UpdatingEntityException(String.format("%s уже существует", newAuthor.toString()));
            }
            return authorDao.update(newAuthor);
        }
    }

    @Override
    public void deleteAuthor(long id) {
        authorDao.delete(id);
    }

    @Override
    public Optional<Author> getAuthorById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public Optional<Author> getAuthorByName(String name) {
        return authorDao.getByName(name);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAll();
    }
}
