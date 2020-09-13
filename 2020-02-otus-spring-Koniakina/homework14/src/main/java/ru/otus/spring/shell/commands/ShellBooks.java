package ru.otus.spring.shell.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.model.jpa.BookJpa;
import ru.otus.spring.model.mongo.BookMongo;
import ru.otus.spring.service.BookService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent("shellBooks")
@RequiredArgsConstructor
public class ShellBooks {

    private final BookService bookService;

    @ShellMethod(value = "Count books", key = {"count books from mongo", "count b mongo"})
    public String countBooksFromMongo() {
        return String.format("Количество книг в базе: %d\n", bookService.countBooks());
    }

    @ShellMethod(value = "List of books from mongo", key = {"all books mongo", "all b mongo"})
    public String getAllBooksFromMongo() {
        List<BookMongo> books = bookService.findAllBooks();
        return "Список книг из NoSQL БД:\n\n" +
                books.stream()
                        .sorted(Comparator.comparing(BookMongo::getTitle))
                        .map(BookMongo::toString)
                        .collect(Collectors.joining("\n\n"))
                ;
    }

}
