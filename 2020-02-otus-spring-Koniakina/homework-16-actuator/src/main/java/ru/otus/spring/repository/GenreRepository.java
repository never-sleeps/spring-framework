package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.spring.model.Genre;

import java.util.Optional;

@RepositoryRestResource(path = "genres")
public interface GenreRepository extends JpaRepository<Genre, Long> {

  @RestResource(path = "titles", rel = "title")
  Optional<Genre> findByTitle(String title);
}
