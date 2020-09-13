package ru.otus.spring.dao.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.model.Author;

import java.util.Optional;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

    Mono<Author> findByFullName(String fullName);

}
