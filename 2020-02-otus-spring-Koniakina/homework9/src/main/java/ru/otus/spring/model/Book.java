package ru.otus.spring.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@Document(collection = "book")
public class Book {
    @Id
    private String id;

    @Field
    @NotBlank
    private String title;

    @DBRef
    @NotNull
    private Author author;

    @DBRef
    @NotNull
    private Genre genre;

    @Field
    private Comment comment;

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String title, Author author, Genre genre, Comment comment) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comment = comment;
    }

    public Book(String id, String title, Author author, Genre genre, Comment comment) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Книга{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", авторы=" + author +
                ", жанры=" + genre +
                '}';
    }
}
