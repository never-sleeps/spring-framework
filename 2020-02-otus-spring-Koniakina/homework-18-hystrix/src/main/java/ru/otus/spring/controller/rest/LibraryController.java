package ru.otus.spring.controller.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.model.Book;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.MessageService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Controller
@Slf4j
public class LibraryController {
    @Autowired
    private final BookService bookService;
    @Autowired
    public MessageService messageService;


    @GetMapping({"/", "/book"})
    public String getBooks(Model model) {
        log.info("Получение списка книг");
        List<Book> books = bookService.findAllBooks();
        log.info("Получено книг: " + books.size());
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/book/{bookId}")
    public String getBook(@PathVariable("bookId") String bookId, Model model) {
        log.info("Получение книги с id: " + bookId);
        Book book = bookService.findBookById(bookId).get();
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/book/add")
    public String addBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("add", true);
        return "edit";
    }

    @PostMapping("/book/add")
    public String saveBook(@ModelAttribute("book") @Valid Book book,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            log.info("Ошибка при попытке сохранения книги: " + book);
            model.addAttribute("add", true);
            model.addAttribute("book", book);
            return "edit";
        }
        log.info("Сохранение книги: " + book);
        Book exceptedBook = bookService.createBook(book);
        model.addAttribute("book", exceptedBook);
        return "book";
    }

    @GetMapping("/book/{bookId}/edit")
    public String editBook(@PathVariable("bookId") String bookId, Model model) {
        log.info("Получение книги с id: " + bookId);
        Book book = bookService.findBookById(bookId).get();
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping("/book/{bookId}/update")
    public String updateBook(@PathVariable String bookId,
                             @ModelAttribute("book") @Valid Book book,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            log.info("Ошибка при попытке сохранения книги: " + book);
            model.addAttribute("add", false);
            model.addAttribute("book", book);
            return "edit";
        }
        log.info("Сохранение книги с id: " + bookId);
        book.setId(bookId);
        Book bookResult = bookService.updateBook(book);
        model.addAttribute("book", bookResult);
        return "book";
    }

    @GetMapping("/book/{bookId}/delete")
    public String deleteBook(@PathVariable("bookId") String bookId) {
        log.info("Удаление книги с id: " + bookId);
        bookService.deleteBook(bookId);
        return "redirect:/book";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}