package ru.otus.spring.service;

import ru.otus.spring.model.mongo.CommentMongo;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    long countComments();

    List<CommentMongo> findAllComments();
}
