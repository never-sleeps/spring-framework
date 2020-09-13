package ru.otus.spring.model.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class BookMongo {
    @Id
    private String id;
    @Field
    private String title;
    @DBRef
    private List<AuthorMongo> authorsMongo;
    @DBRef
    private List<GenreMongo> genresMongo;
    @DBRef
    private List<CommentMongo> commentsMongo;

    public BookMongo(String title, List<AuthorMongo> authorsMongo, List<GenreMongo> genresMongo) {
        this.title = title;
        this.authorsMongo = authorsMongo;
        this.genresMongo = genresMongo;
        this.commentsMongo = List.of();
    }

    public BookMongo(String id, String title, List<AuthorMongo> authorsMongo, List<GenreMongo> genresMongo) {
        this.id = id;
        this.title = title;
        this.authorsMongo = authorsMongo;
        this.genresMongo = genresMongo;
        this.commentsMongo = List.of();
    }

    @Override
    public String toString() {
        return "Книга{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                "\n\tавторы=" + authorsMongo +
                "\n\tжанры=" + genresMongo +
                "\n\tкомментарии=" + commentsMongo +
                '}';
    }
}
