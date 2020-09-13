package ru.otus.spring.shell.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.model.jpa.BookJpa;
import ru.otus.spring.repositories.jpa.BookJpaRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent("shellBooksJpa")
@RequiredArgsConstructor
public class ShellBooksJpa {

    private final BookJpaRepository bookJpaRepository;

    @ShellMethod(value = "List of books from relational DB", key = {"all books jpa", "all b jpa"})
    @Transactional
    public String getAllBooksFromJpa () {
        List<BookJpa> books = bookJpaRepository.findAll();
        return "Список книг из реляционной БД:\n\n" +
                books.stream()
                        .sorted(Comparator.comparing(BookJpa::getTitle))
                        .map(BookJpa::toString)
                        .collect(Collectors.joining("\n\n"))
                ;
    }

    @ShellMethod(value = "Count books form relational DB", key = {"count books from jpa", "count b jpa"})
    public String countBooksFromJpa() {
        return String.format("Количество книг в реляционной базе: %d\n", bookJpaRepository.count());
    }
}
