package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.exception.EntityDeleteException;
import ru.otus.spring.exception.EntityUpdateException;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public long countBooks() {
        return bookDao.count();
    }

    @Override
    public Book createBook(String bookTitle, String[] authorNames, String[] genreTitles) {
        Book newBook = Book.builder()
                .title(bookTitle)
                .authors(getRegistredAuthors(authorNames))
                .genres(getRegistredGenres(genreTitles))
                .comments(List.of())
                .build();
        return bookDao.create(newBook);
    }

    @Override
    public Book updateBook(long id, String bookTitle, String[] authorNames, String[] genreTitles) {
        Book book = bookDao.getById(id);
        if (book == null) {
            throw new EntityUpdateException(String.format("Книга с id '%s' не найдена", id));
        } else {
            if(!bookTitle.isEmpty())
                book.setTitle(bookTitle);
            if(!genreTitles[0].isEmpty())
                book.setAuthors(getRegistredAuthors(authorNames));
            if(!genreTitles[0].isEmpty())
                book.setGenres(getRegistredGenres(genreTitles));
            return bookDao.update(book);
        }
    }

    @Override
    public void deleteBook(long id) {
        if(getBookById(id) == null){
            throw new EntityDeleteException(String.format("Книга с id '%d' не найдена", id));
        }
        bookDao.delete(id);
    }

    @Override
    public Book getBookById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public Book getBookByTitle(String title) {
        return bookDao.getByTitle(title);
    }

    @Override
    public List<Book> getBooksByAuthor(String name) {
        Author author = authorService.getAuthorByName(name);
        return bookDao.getByAuthor(author);
    }

    @Override
    public List<Book> getBooksByGenre(String title) {
        Genre genre = genreService.getGenreByTitle(title);
        return bookDao.getByGenre(genre);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }


    private List<Author> getRegistredAuthors(String[] authorNames){
        List<Author> authors = new ArrayList<>();
        for (String authorName: authorNames) {
            authors.add(authorService.getRegisteredAuthor(authorName));
        }
        return authors;
    }

    private List<Genre> getRegistredGenres(String[] genreTitles){
        List<Genre> genres = new ArrayList<>();
        for (String genreTitle: genreTitles) {
            genres.add(genreService.getRegisteredGenre(genreTitle));
        }
        return genres;
    }

}