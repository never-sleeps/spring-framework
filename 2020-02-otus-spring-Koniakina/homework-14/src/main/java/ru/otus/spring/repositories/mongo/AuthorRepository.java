package ru.otus.spring.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.model.mongo.AuthorMongo;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<AuthorMongo, String> {
}
