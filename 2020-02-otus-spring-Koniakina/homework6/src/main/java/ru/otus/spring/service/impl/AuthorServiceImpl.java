package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.exception.EntityDeleteException;
import ru.otus.spring.exception.EntityUpdateException;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Genre;
import ru.otus.spring.service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    @Override
    public long countAuthors() {
        return authorDao.count();
    }

    @Override
    public Author createAuthor(String fullName) {
        Author newAuthor = Author.builder().fullName(fullName).build();
        return authorDao.create(newAuthor);
    }

    @Override
    public Author updateAuthor(long id, String fullName) {
        Author author = getAuthorById(id);
        if (author == null) {
            throw new EntityUpdateException(String.format("Автор с id '%s' не найден", id));
        } else {
            author.setFullName(fullName);
            return authorDao.update(author);
        }
    }

    @Override
    public void deleteAuthor(long id) {
        if(getAuthorById(id) == null){
            throw new EntityDeleteException(String.format("Автор с id '%d' не найден", id));
        }
        authorDao.delete(id);
    }

    @Override
    public Author getAuthorById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public Author getAuthorByName(String name) {
        return authorDao.getByName(name);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAll();
    }

    @Override
    public Author getRegisteredAuthor(String authorName){
        Author existingAuthor = getAuthorByName(authorName);
        return (existingAuthor == null) ? createAuthor(authorName) : existingAuthor;
    }
}
