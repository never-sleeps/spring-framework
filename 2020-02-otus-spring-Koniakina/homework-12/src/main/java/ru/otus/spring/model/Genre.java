package ru.otus.spring.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@Document(collection = "genre")
public class Genre {

    @Id
    private String id;

    @Field
    @NotBlank
    private String title;

    public Genre(String title) {
        this.title = title;
    }

    public Genre(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Жанр{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
