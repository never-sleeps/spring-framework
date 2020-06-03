package ru.otus.spring.controller.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
//@RestController
@Slf4j
public class LibraryController {
//    @Autowired
//    private final BookService bookService;
//    @Autowired
//    public MessageService messageService;
//
//    @GetMapping({"/api/book"})
//    public List<Book> getBooks() {
//        log.info("Получение списка книг");
//        return bookService.findAllBooks();
//    }
//
//    @GetMapping("/api/book/{bookId}")
//    public Book getBook(@PathVariable("bookId") String bookId) {
//        log.info("Получение книги с id: " + bookId);
//        return bookService.findBookById(bookId).get();
//    }
//
//    @PostMapping("/api/book/add")
//    public Book createBook(@RequestBody Book book) {
//        log.info("Сохранение книги с id: " + book.getId());
//        return bookService.createBook(book);
//    }
//
//    @PutMapping({"/api/book"})
//    public Book updateBook(@RequestBody Book book) {
//        log.info("Сохранение книги с id: " + book.getId());
//        return bookService.updateBook(book);
//    }
//
//    @GetMapping("/api/book/{bookId}/delete")
//    public void deleteBook(@PathVariable("bookId") String bookId) {
//        log.info("Удаление книги с id: " + bookId);
//        bookService.deleteBook(bookId);
//    }
}