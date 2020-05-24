package ru.otus.spring.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@Document(collection = "comment")
public class Comment {
    @Id
    private String id;

    @Field
    @NotBlank
    private String text;

    @DBRef
    @NotNull
    @ToString.Exclude
    private Book book;

    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }

    public Comment(String id, String text, Book book) {
        this.id = id;
        this.text = text;
        this.book = book;
    }

    @Override
    public String toString() {
        return "Комментарий {" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", книга='" + book.getTitle() + '\'' +
                '}';
    }
}
