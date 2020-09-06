package ru.otus.spring.model.mongo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Builder
@NoArgsConstructor
@Document(collection = "comment")
public class CommentMongo {
    @Id
    private String id;
    @Field
    private String text;
    @DBRef
    private BookMongo bookMongo;

    public CommentMongo(String text, BookMongo bookMongo) {
        this.text = text;
        this.bookMongo = bookMongo;
    }

    public CommentMongo(String id, String text, BookMongo bookMongo) {
        this.id = id;
        this.text = text;
        this.bookMongo = bookMongo;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
