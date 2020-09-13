package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByFullName(String fullName);

}
