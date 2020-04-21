package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.exception.CreatingEntityException;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;
import ru.otus.spring.view.Console;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final Console console;

    @Override
    public int countBooks() {
        return bookDao.count();
    }

    @Override
    public Book createBook(String bookTitle, String authorName, String genreTitle) {

        Author author = (authorDao.getByName(authorName).isPresent())
                ? authorDao.getByName(authorName).get() : authorService.createAuthor(authorName);
        Genre genre = (genreDao.getByTitle(genreTitle).isPresent())
                ? genreDao.getByTitle(genreTitle).get() : genreService.createGenre(genreTitle);
        Book newBook = Book.builder().author(author).genre(genre).title(bookTitle).build();
        if (bookDao.isExist(newBook)) {
            throw new CreatingEntityException(String.format("%s уже существует", newBook.toString()));
        }
        return bookDao.create(newBook);
    }

    @Override
    public Book updateBook(long id, String bookTitle, String authorName, String genreTitle) {
        if (!bookDao.getById(id).isPresent()) {
            return null;
        } else {
            Author author = authorService.getRegisteredAuthor(authorName);
            Genre genre = genreService.getRegisteredGenre(genreTitle);
            Book book = Book.builder()
                    .id(id)
                    .author(author)
                    .genre(genre)
                    .title(bookTitle)
                    .build();
            return bookDao.update(book);
        }
    }

    @Override
    public void deleteBook(long id) {
        bookDao.delete(id);
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public Optional<Book> getBookByTitle(String title) {
        return bookDao.getByTitle(title);
    }

    @Override
    public List<Book> getBooksByAuthor(String name) {
        return bookDao.getByAuthor(name);
    }

    @Override
    public List<Book> getBooksByGenre(String title) {
        return bookDao.getByGenre(title);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }
}