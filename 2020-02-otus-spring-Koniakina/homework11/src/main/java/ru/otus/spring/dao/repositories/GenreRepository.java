package ru.otus.spring.dao.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.model.Genre;

import java.util.Optional;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

    Mono<Genre> findByTitle(String title);

}
