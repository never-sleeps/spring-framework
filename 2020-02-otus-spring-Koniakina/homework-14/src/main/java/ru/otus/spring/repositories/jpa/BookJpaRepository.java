package ru.otus.spring.repositories.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.model.jpa.BookJpa;

import java.util.UUID;

public interface BookJpaRepository extends JpaRepository<BookJpa, String> {
}
