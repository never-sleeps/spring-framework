package ru.otus.spring.service;

import ru.otus.spring.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    long countBooks();

    Book createBook(String bookTitle, String[] authorName, String[] genreTitle);

    Book updateBook(String id, String bookTitle, String authorName[], String genreTitle[]);

    void deleteBook(String id);

    Optional<Book> findBookById(String id);

    Book findBookByTitle(String title);

    List<Book> findBooksByAuthor(String name);

    List<Book> findBooksByGenre(String title);

    List<Book> findAllBooks();
}
