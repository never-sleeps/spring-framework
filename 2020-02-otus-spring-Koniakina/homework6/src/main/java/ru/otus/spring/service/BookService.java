package ru.otus.spring.service;

import ru.otus.spring.model.Book;

import java.util.List;

public interface BookService {

    long countBooks();

    Book createBook(String bookTitle, String[] authorName, String[] genreTitle);

    Book updateBook(long id, String bookTitle, String authorName[], String genreTitle[]);

    void deleteBook(long id);

    Book getBookById(long id);

    Book getBookByTitle(String title);

    List<Book> getBooksByAuthor(String name);

    List<Book> getBooksByGenre(String title);

    List<Book> getAllBooks();
}
