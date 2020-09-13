package ru.otus.spring.service;

import ru.otus.spring.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    int countBooks();

    Book createBook(String bookTitle, String authorName, String genreTitle);

    Book updateBook(long id, String bookTitle, String authorName, String genreTitle);

    void deleteBook(long id);

    Optional<Book> getBookById(long id);

    Optional<Book> getBookByTitle(String title);

    List<Book> getBooksByAuthor(String name);

    List<Book> getBooksByGenre(String title);

    List<Book> getAllBooks();
}
