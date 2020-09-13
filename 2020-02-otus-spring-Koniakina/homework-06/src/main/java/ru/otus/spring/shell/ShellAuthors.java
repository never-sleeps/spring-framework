package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.model.Author;
import ru.otus.spring.service.AuthorService;

@ShellComponent("shellAuthors")
@RequiredArgsConstructor
public class ShellAuthors {

    private final AuthorService authorService;
    private final ShellOptions options;

    @ShellMethod(value = "Count authors", key = {"count authors", "count a"})
    public String countAuthors() {
        return String.format("Количество авторов в библиотеке: %d\n", authorService.countAuthors());
    }

    @ShellMethod(value = "Create author", key = {"create author", "ca"})
    public String createAuthor() {
        Author author = authorService.createAuthor(options.readAuthorName());
        return String.format("%s успешно добавлен\n", author.toString());
    }

    @ShellMethod(value = "Update author", key = {"update author", "ua"})
    public String updateAuthor(@ShellOption int id) {
        Author author = authorService.updateAuthor(id, options.readAuthorName());
        return (author == null) ? String.format("Автор с ID %d отсутствует в библиотеке\n", id)
                : String.format("Данные автора с ID %d успешно обновлены: %s\n", id, author.toString());
    }

    @ShellMethod(value = "Delete author", key = {"delete author", "da"})
    public String deleteAuthor(@ShellOption int id) {
        authorService.deleteAuthor(id);
        return String.format("Автор с id '%d' был удален\n", id);
    }

    @ShellMethod(value = "Information about author by ID", key = {"author by id", "a by id"})
    public String getAuthorById(@ShellOption int id) {
        Author author = authorService.getAuthorById(id);
        return (author != null) ? author.toString()+"\n" : String.format("В библиотеке нет автора  с id '%d'\n", id);
    }

    @ShellMethod(value = "Information about author by name", key = {"author by name", "a by name"})
    public String getAuthorByTitle() {
        String fullName = options.readAuthorName();
        Author author = authorService.getAuthorByName(fullName);
        return (author != null) ? author.toString()+"\n" : String.format("В библиотеке нет автора '%s'\n", fullName);
    }

    @ShellMethod(value = "List of authors", key = {"all authors", "all a"})
    public String getAllAuthors() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Author author: authorService.getAllAuthors()) {
            stringBuilder.append("\n").append(author.toString());
        }
        return String.format("Список авторов: %s\n", stringBuilder.toString());
    }
}
