package ru.otus.spring.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.model.Book;
import ru.otus.spring.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/book")
    public List<Book> getAll() {
        log.info("Получение списка книг");
        return bookService.findAllBooks();
    }

    @GetMapping("/api/book/{id}")
    public Book get(@PathVariable("id") String id) {
        log.info("Получение книги с id: " + id);
        return bookService.findBookById(id).get();
    }

    @PostMapping("/api/book")
    public Book create(@RequestBody Book book) {
        log.info("Сохранение книги: " + book);
        return bookService.createBook(book);
    }

    @PutMapping("/api/book")
    public Book update(@RequestBody Book book) {
        log.info("Сохранение книги с id: " + book.getId());
        return bookService.updateBook(book);
    }

    @GetMapping("/api/book/{id}/delete")
    public void delete(@PathVariable("id") String id) {
        log.info("Удаление книги с id: " + id);
        bookService.deleteBook(id);
    }
}