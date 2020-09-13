package ru.otus.spring.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.model.mongo.GenreMongo;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GENRES")
public class GenreJpa {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    public GenreJpa(GenreMongo genreMongo) {
        this.id = genreMongo.getId();
        this.title = genreMongo.getTitle();
    }


    @Override
    public String toString() {
        return "Жанр{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
