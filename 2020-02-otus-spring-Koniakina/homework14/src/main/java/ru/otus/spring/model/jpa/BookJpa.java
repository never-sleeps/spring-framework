package ru.otus.spring.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.otus.spring.model.mongo.BookMongo;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOKS")
public class BookJpa {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<AuthorJpa> authorsJpa;

    @Fetch(value = FetchMode.SUBSELECT)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "book_genre", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreJpa> genresJpa;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private List<CommentJpa> commentsJpa;

    public BookJpa(BookMongo bookMongo) {
        this.id = bookMongo.getId();
        this.title = bookMongo.getTitle();
        this.genresJpa = bookMongo.getGenresMongo()
                .stream()
                .map(GenreJpa::new)
                .collect(Collectors.toList());
        this.authorsJpa = bookMongo.getAuthorsMongo()
                .stream()
                .map(AuthorJpa::new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Книга{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                "\n\tавторы=" + authorsJpa +
                "\n\tжанры=" + genresJpa +
                "\n\tкомментарии=" + commentsJpa +
                '}';
    }
}
