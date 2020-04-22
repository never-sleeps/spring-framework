package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;
import ru.otus.spring.view.Console;

import java.sql.SQLException;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {
    private final AuthorService authorService;
    private final BookService bookService;
    private final GenreService genreService;
    private final Console console;

    @ShellMethod(value = "Open H2 console", key = {"h2"})
    public void openBrowser() throws SQLException { org.h2.tools.Console.main(null); }

    @ShellMethod(value = "Create book", key = {"create book", "cb"})
    public String createBook() {
        Book book = bookService.createBook(readBookTitle(), readAuthorName(), readGenreTitle());
        return String.format("Книга: %s успешно добавлена",book.toString());
    }

    @ShellMethod(value = "Update book", key = {"update book", "ub"})
    public String updateBook(@ShellOption int id) {
        Book book = bookService.updateBook(id, readBookTitle(), readAuthorName(), readGenreTitle());
        return (book == null) ? String.format("Книга с ID %d отсутствует в библиотеке", id)
                : String.format("Данные книги с ID %d успешно обновлены: %s", id, book.toString());
    }

    @ShellMethod(value = "Count books", key = {"count books", "count b"})
    public String countBooks() {
        return String.format("Количество книг в базе: %d", bookService.countBooks());
    }

    @ShellMethod(value = "Delete book", key = {"delete book", "db"})
    public String deleteBook(@ShellOption int id) {
        bookService.deleteBook(id);
        return String.format("Книга с id '%d' была удалена", id);
    }

    @ShellMethod(value = "Information about book by ID", key = {"book by id", "b by id"})
    public String getBookById(@ShellOption int id) {
        Optional<Book> book = bookService.getBookById(id);
        return (book.isPresent()) ? String.format("Книга с id '%d': %s", id, book.get().toString())
                : String.format("В библиотеке нет книги  с id '%d'", id);
    }

    @ShellMethod(value = "Information about book by title", key = {"book by title", "b by title"})
    public String getBookByTitle() {
        String title = readBookTitle();
        Optional<Book> book = bookService.getBookByTitle(title);
        return (book.isPresent()) ? String.format("Книга '%s': %s", title, book.get().toString())
                : String.format("В библиотеке нет книги '%s'", title);
    }

    @ShellMethod(value = "Information about books by author", key = {"books by author", "b by author"})
    public String getBooksByAuthor() {
        String authorName = readAuthorName();
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book: bookService.getBooksByAuthor(authorName)) {
            stringBuilder.append("\n").append(book.toString());
        }
        return (stringBuilder.toString().isEmpty()) ? String.format("В бибилиотеке нет книг автора %s", authorName)
                : String.format("Книги автора '%s': %s", authorName, stringBuilder.toString());
    }

    @ShellMethod(value = "Information about books by Genre", key = {"books by genre", "b by genre"})
    public String getBooksByGenre() {
        String title = readGenreTitle();
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book: bookService.getBooksByGenre(title)) {
            stringBuilder.append("\n").append(book.toString());
        }
        return (stringBuilder.toString().isEmpty()) ? String.format("В бибилиотеке нет книг жанра %s", title)
                : String.format("Книги жанра '%s': %s", title, stringBuilder.toString());
    }

    @ShellMethod(value = "List of books", key = {"all books", "all b"})
    public String getAllBooks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book: bookService.getAllBooks()) {
            stringBuilder.append("\n").append(book.toString());
        }
        return String.format("Список книг: %s", stringBuilder.toString());
    }

    @ShellMethod(value = "Create author", key = {"create author", "ca"})
    public String createAuthor() {
        Author author = authorService.createAuthor(readAuthorName());
        return String.format("Автор: %s успешно добавлен", author.toString());
    }

    @ShellMethod(value = "Update author", key = {"update author", "ua"})
    public String updateAuthor(@ShellOption int id) {
        Author author = authorService.updateAuthor(id, readAuthorName());
        return (author == null) ? String.format("Автор с ID %d отсутствует в библиотеке", id)
                : String.format("Данные автора с ID %d успешно обновлены: %s", id, author.toString());
    }

    @ShellMethod(value = "Count authors", key = {"count authors", "count a"})
    public String countAuthors() {
        return String.format("Количество авторов в библиотеке: %d", authorService.countAuthors());
    }

    @ShellMethod(value = "Delete author", key = {"delete author", "da"})
    public String deleteAuthor(@ShellOption int id) {
        authorService.deleteAuthor(id);
        return String.format("Автор с id '%d' был удален", id);
    }

    @ShellMethod(value = "Information about author by ID", key = {"author by id", "a by id"})
    public String getAuthorById(@ShellOption int id) {
        Optional<Author> author = authorService.getAuthorById(id);
        return (author.isPresent()) ? String.format("Автор с id '%d': %s", id, author.get().toString())
                : String.format("В библиотеке нет автора  с id '%d'", id);
    }

    @ShellMethod(value = "Information about author by name", key = {"author by name", "a by name"})
    public String getAuthorByTitle() {
        String fullName = readAuthorName();
        Optional<Author> author = authorService.getAuthorByName(fullName);
        return (author.isPresent()) ? String.format("Автор '%s': %s", fullName, author.get().toString())
                : String.format("В библиотеке нет автора '%s'", fullName);
    }

    @ShellMethod(value = "List of authors", key = {"all authors", "all a"})
    public String getAllAuthors() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Author author: authorService.getAllAuthors()) {
            stringBuilder.append("\n").append(author.toString());
        }
        return String.format("Список авторов: %s", stringBuilder.toString());
    }

    @ShellMethod(value = "Create genre", key = {"create genre", "cg"})
    public String createGenre() {
        Genre genre = genreService.createGenre(readGenreTitle());
        return String.format("Жанр: %s успешно добавлен", genre.toString());
    }

    @ShellMethod(value = "Update genre", key = {"update genre", "ug"})
    public String updateGenre(@ShellOption int id) {
        Genre genre = genreService.updateGenre(id, readGenreTitle());
        return (genre == null) ? String.format("Жанр с ID %d отсутствует в библиотеке", id)
                : String.format("Данные жанра с ID %d успешно обновлены: %s", id, genre.toString());
    }

    @ShellMethod(value = "Count genres", key = {"count genres", "count g"})
    public String countGenres() {
        return String.format("Количество жанров в библиотеке: %d", genreService.countGenres());
    }

    @ShellMethod(value = "Delete genre", key = {"delete genre", "dg"})
    public String deleteGenre(@ShellOption int id) {
        genreService.deleteGenre(id);
        return String.format("Жанр с id '%d' был удален", id);
    }

    @ShellMethod(value = "Information about genre by ID", key = {"genre by id", "g by id"})
    public String getGenreById(@ShellOption int id) {
        Optional<Genre> genre = genreService.getGenreById(id);
        return (genre.isPresent()) ? String.format("Жанр с id '%d': %s", id, genre.get().toString())
                : String.format("В библиотеке нет жанра  с id '%d'", id);
    }

    @ShellMethod(value = "Information about genre by title", key = {"genre by title", "g by title"})
    public String getGenreByTitle() {
        String title = readGenreTitle();
        Optional<Genre> genre = genreService.getGenreByTitle(title);
        return (genre.isPresent()) ? String.format("Жанр '%s': %s", title, genre.get().toString())
                : String.format("В библиотеке нет жанра '%s'", title);
    }

    @ShellMethod(value = "List of genres", key = {"all genres", "all g"})
    public String getAllGenres() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Genre genre: genreService.getAllGenres()) {
            stringBuilder.append("\n").append(genre.toString());
        }
        return String.format("Список жанров: %s", stringBuilder.toString());
    }

    private String readBookTitle(){
        console.write("Введите название книги:");
        return console.read();
    }

    private String readAuthorName(){
        console.write("Введите имя автора:");
        return console.read();
    }

    private String readGenreTitle(){
        console.write("Введите название жанра:");
        return console.read();
    }
}
