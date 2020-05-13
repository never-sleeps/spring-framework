package ru.otus.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COMMENTS")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "text", nullable = false, unique = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Override
    public String toString() {
        return "Комментарий {" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", книга='" + book.getTitle() + '\'' +
                '}';
    }
}
