package ru.otus.spring.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class Book {
    @Id
    private String id;

    @Field
    @NotBlank
    private String title;

    @DBRef
    @NotNull
    private List<Author> authors;

    @DBRef
    @NotNull
    private List<Genre> genres;

    @DBRef(lazy = true)
    @ToString.Exclude
    private List<Comment> comments;

    public Book(String title, List<Author> authors, List<Genre> genres) {
        this.title = title;
        this.authors = authors;
        this.genres = genres;
        this.comments = List.of();
    }

    public Book(String id, String title, List<Author> authors, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.genres = genres;
        this.comments = List.of();
    }

    @Override
    public String toString() {
        return "Книга{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", авторы=" + authors +
                ", жанры=" + genres +
                '}';
    }
}
