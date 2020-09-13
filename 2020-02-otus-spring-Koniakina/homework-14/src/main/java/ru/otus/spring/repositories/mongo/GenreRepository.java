package ru.otus.spring.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.model.mongo.GenreMongo;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<GenreMongo, String> {
}
