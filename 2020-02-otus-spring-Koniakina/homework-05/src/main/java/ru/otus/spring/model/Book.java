package ru.otus.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Book {
    private long id;
    private String title;
    private Author author;
    private Genre genre;

    @Override
    public String toString() {
        return "Книга {" +
                "id: " + id +
                ", '" + title + '\'' +
                ", автор: " + author.getFullName() +
                ", жанр: " + genre.getTitle() +
                '}';
    }
}
