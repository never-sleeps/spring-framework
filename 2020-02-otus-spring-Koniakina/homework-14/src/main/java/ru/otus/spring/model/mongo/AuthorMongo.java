package ru.otus.spring.model.mongo;

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
public class AuthorMongo {
    @Id
    private String id;
    @Field
    private String fullName;

    public AuthorMongo(String fullName) {
        this.fullName = fullName;
    }

    public AuthorMongo(String id, String fullName) {
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