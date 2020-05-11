package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.exception.EntityDeleteException;
import ru.otus.spring.exception.EntityUpdateException;
import ru.otus.spring.model.Author;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.service.AuthorService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public long countAuthors() {
        return authorRepository.count();
    }

    @Override
    public Author createAuthor(String fullName) {
        Author newAuthor = Author.builder().fullName(fullName).build();
        return authorRepository.save(newAuthor);
    }

    @Override
    public Author updateAuthor(long id, String fullName) {
        Optional<Author> optionalAuthor = findAuthorById(id);
        if (optionalAuthor.isEmpty()) {
            throw new EntityUpdateException(String.format("Автор с id '%s' не найден", id));
        }
        Author author = optionalAuthor.get();
        author.setFullName(fullName);
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(long id) {
        Optional<Author> author = findAuthorById(id);
        if(author.isEmpty()){
            throw new EntityDeleteException(String.format("Автор с id '%d' не найден", id));
        }
        authorRepository.delete(author.get());
    }

    @Override
    public Optional<Author> findAuthorById(long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author findAuthorByName(String name) {
        return authorRepository.findByFullName(name);
    }

    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getRegisteredAuthor(String authorName){
        Author existingAuthor = findAuthorByName(authorName);
        return (existingAuthor == null) ? createAuthor(authorName) : existingAuthor;
    }
}
