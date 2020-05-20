package ru.otus.spring.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Builder
@NoArgsConstructor
@Document(collection = "author")
public class Author {
    @Id
    private String id;
    @Field
    private String fullName;

    public Author(String fullName) {
        this.fullName = fullName;
    }

    public Author(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Автор{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}