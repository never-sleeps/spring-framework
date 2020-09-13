package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.exception.EntityCreateException;
import ru.otus.spring.model.Book;
import ru.otus.spring.service.BookService;

import java.util.Optional;

@ShellComponent("shellBooks")
@RequiredArgsConstructor
public class ShellBooks {

    private final BookService bookService;
    private final ShellOptions options;

    @ShellMethod(value = "Create book", key = {"create book", "cb"})
    public String createBook() {
        Book book = bookService.createBook(
                options.readBookTitle(),
                options.readAuthorNames(),
                options.readGenreTitles());
        if (book == null) throw new EntityCreateException("Ошибка создания книги");
        return String.format("Успешно добавлена %s\n",book.toString());
    }

    @ShellMethod(value = "Update book", key = {"update book", "ub"})
    public String updateBook(@ShellOption int id) {
        Book book = bookService.updateBook(
                id,
                options.readBookTitle(),
                options.readAuthorNames(),
                options.readGenreTitles()
        );
        return String.format("Данные книги с ID %d успешно обновлены: %s\n", id, book.toString());
    }

    @ShellMethod(value = "Count books", key = {"count books", "count b"})
    public String countBooks() {
        return String.format("Количество книг в базе: %d\n", bookService.countBooks());
    }

    @ShellMethod(value = "Delete book", key = {"delete book", "db"})
    public String deleteBook(@ShellOption int id) {
        bookService.deleteBook(id);
        return String.format("Книга с id '%d' была удалена\n", id);
    }

    @ShellMethod(value = "Information about book by ID", key = {"book by id", "b by id"})
    public String getBookById(@ShellOption int id) {
        Optional <Book> book = bookService.findBookById(id);
        return (book.isPresent()) ? String.format("Книга с id '%d': %s\n", id, book.toString())
                : String.format("В библиотеке нет книги  с id '%d'\n", id);
    }

    @ShellMethod(value = "Information about book by title", key = {"book by title", "b by title"})
    public String getBookByTitle() {
        String title = options.readBookTitle();
        Book book = bookService.findBookByTitle(title);
        return (book != null) ? String.format("Книга '%s': %s\n", title, book.toString())
                : String.format("В библиотеке нет книги '%s'\n", title);
    }

    @ShellMethod(value = "Information about books by author", key = {"books by author", "b by author"})
    public String getBooksByAuthor() {
        String authorName = options.readAuthorName();
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book: bookService.findBooksByAuthor(authorName)) {
            stringBuilder.append("\n").append(book.toString());
        }
        return (stringBuilder.toString().isEmpty()) ? String.format("В бибилиотеке нет книг автора %s\n", authorName)
                : String.format("Книги автора '%s': %s\n", authorName, stringBuilder.toString());
    }

    @ShellMethod(value = "Information about books by Genre", key = {"books by genre", "b by genre"})
    public String getBooksByGenre() {
        String title = options.readGenreTitle();
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book: bookService.findBooksByGenre(title)) {
            stringBuilder.append("\n").append(book.toString());
        }
        return (stringBuilder.toString().isEmpty()) ? String.format("В бибилиотеке нет книг жанра %s\n", title)
                : String.format("Книги жанра '%s': %s\n", title, stringBuilder.toString());
    }

    @ShellMethod(value = "List of books", key = {"all books", "all b"})
    public String getAllBooks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book: bookService.findAllBooks()) {
            stringBuilder.append("\n").append(book.toString());
        }
        return String.format("Список книг: %s\n", stringBuilder.toString());
    }

}
