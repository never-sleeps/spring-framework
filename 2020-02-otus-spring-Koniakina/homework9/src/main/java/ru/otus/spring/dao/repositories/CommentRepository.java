package ru.otus.spring.dao.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

}
