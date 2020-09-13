package ru.otus.spring.service;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.spring.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    long countBooks();

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    Book createBook(String bookTitle, String authorName, String genreTitle);

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    Book createBook(Book book);

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    Book updateBook(String id, String bookTitle, String authorName, String genreTitle);

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    Book updateBook(Book book);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteBook(String id);

    Optional<Book> findBookById(String id);

    Book findBookByTitle(String title);

    List<Book> findBooksByAuthor(String name);

    List<Book> findBooksByGenre(String title);

    List<Book> findAllBooks();
}
