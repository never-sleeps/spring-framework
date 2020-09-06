package ru.otus.spring.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.model.mongo.AuthorMongo;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTHORS")
public class AuthorJpa {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "full_name", nullable = false, unique = true)
    private String fullName;

    public AuthorJpa(AuthorMongo authorMongo) {
        this.id = authorMongo.getId();
        this.fullName = authorMongo.getFullName();
    }

    @Override
    public String toString() {
        return "Автор{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}