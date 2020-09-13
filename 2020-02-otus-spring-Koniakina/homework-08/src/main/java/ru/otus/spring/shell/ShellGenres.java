package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.model.Genre;
import ru.otus.spring.service.GenreService;

import java.util.Optional;

@ShellComponent("shellGenres")
@RequiredArgsConstructor
public class ShellGenres {

    private final GenreService genreService;
    private final ShellOptions options;

    @ShellMethod(value = "Create genre", key = {"create genre", "cg"})
    public String createGenre() {
        Genre genre = genreService.createGenre(options.readGenreTitle());
        return String.format("Жанр: %s успешно добавлен\n", genre.toString());
    }

    @ShellMethod(value = "Update genre", key = {"update genre", "ug"})
    public String updateGenre(@ShellOption String id) {
        Genre genre = genreService.updateGenre(id, options.readGenreTitle());
        return (genre == null) ? String.format("Жанр с ID %s отсутствует в библиотеке\n", id)
                : String.format("Данные жанра с ID %s успешно обновлены: %s\n", id, genre.toString());
    }

    @ShellMethod(value = "Count genres", key = {"count genres", "count g"})
    public String countGenres() {
        return String.format("Количество жанров в библиотеке: %d\n", genreService.countGenres());
    }

    @ShellMethod(value = "Delete genre", key = {"delete genre", "dg"})
    public String deleteGenre(@ShellOption String id) {
        genreService.deleteGenre(id);
        return String.format("Жанр с id '%s' был удален\n", id);
    }

    @ShellMethod(value = "Information about genre by ID", key = {"genre by id", "g by id"})
    public String getGenreById(@ShellOption String id) {
        Optional <Genre> genre= genreService.findGenreById(id);
        return (genre.isPresent()) ? String.format("Жанр с id '%s': %s\n", id, genre.toString())
                : String.format("В библиотеке нет жанра  с id '%s'\n", id);
    }

    @ShellMethod(value = "Information about genre by title", key = {"genre by title", "g by title"})
    public String getGenreByTitle() {
        String title = options.readGenreTitle();
        Optional <Genre> genre = genreService.findGenreByTitle(title);
        return (genre.isPresent()) ? String.format("Жанр '%s': %s\n", title, genre.toString())
                : String.format("В библиотеке нет жанра '%s'\n", title);
    }

    @ShellMethod(value = "List of genres", key = {"all genres", "all g"})
    public String getAllGenres() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Genre genre: genreService.findAllGenres()) {
            stringBuilder.append("\n").append(genre.toString());
        }
        return String.format("Список жанров: %s\n", stringBuilder.toString());
    }
}
