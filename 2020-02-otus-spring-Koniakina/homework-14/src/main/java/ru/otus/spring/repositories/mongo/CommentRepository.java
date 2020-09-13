package ru.otus.spring.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.model.mongo.BookMongo;
import ru.otus.spring.model.mongo.CommentMongo;

import java.util.List;

public interface CommentRepository extends MongoRepository<CommentMongo, String> {
}
