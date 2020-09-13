package ru.otus.spring.dao.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.model.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

}
