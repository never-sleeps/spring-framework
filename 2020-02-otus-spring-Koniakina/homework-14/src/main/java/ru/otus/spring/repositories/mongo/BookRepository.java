package ru.otus.spring.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.model.mongo.AuthorMongo;
import ru.otus.spring.model.mongo.BookMongo;
import ru.otus.spring.model.mongo.GenreMongo;

import java.util.List;

public interface BookRepository extends MongoRepository<BookMongo, String> {
}
