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
@Document(collection = "genre")
public class GenreMongo {

    @Id
    private String id;
    @Field
    private String title;

    public GenreMongo(String title) {
        this.title = title;
    }

    public GenreMongo(String id, String title) {
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
