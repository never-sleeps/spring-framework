package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.spring.model.Book;

import java.util.Optional;

@RepositoryRestResource(path = "books")
public interface BookRepository extends JpaRepository<Book, Long> {
    @RestResource(path = "titles", rel = "title")
    Optional<Book> findByTitle(String title);

    @RestResource(path = "likeTitles", rel = "value")
    Optional<Book> findByTitleIsLike(String value);
}
