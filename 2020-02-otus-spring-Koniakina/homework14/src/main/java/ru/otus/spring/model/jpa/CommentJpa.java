package ru.otus.spring.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.model.mongo.CommentMongo;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COMMENTS")
public class CommentJpa {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "text", nullable = false, unique = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookJpa bookJpa;

    public CommentJpa(CommentMongo commentMongo) {
        this.id = commentMongo.getId();
        this.text = commentMongo.getText();
        this.bookJpa = new BookJpa(commentMongo.getBookMongo());
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
