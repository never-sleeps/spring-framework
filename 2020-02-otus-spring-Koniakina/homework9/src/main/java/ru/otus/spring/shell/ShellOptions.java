package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import ru.otus.spring.view.Console;

import java.util.Arrays;

@ShellComponent("shellOptions")
@RequiredArgsConstructor
public class ShellOptions {

    private final Console console;

    String readBookTitle(){
        console.write("Введите название книги: ");
        return console.read();
    }

    String[] readAuthorNames(){
        console.write("Введите авторов через запятую: ");
        return Arrays.stream(console.read()
                .split(","))
                .map(String::trim)
                .toArray(String[]::new);
    }

    String[] readGenreTitles(){
        console.write("Введите жанры через запятую: ");
        return Arrays.stream(console.read()
                .split(","))
                .map(String::trim)
                .toArray(String[]::new);
    }

    String readAuthorName(){
        console.write("Введите имя автора: ");
        return console.read().trim();
    }

    String readGenreTitle(){
        console.write("Введите название жанра: ");
        return console.read().trim();
    }

    String readComment(){
        console.write("Введите комментарий к книге: ");
        return console.read().trim();
    }
}
