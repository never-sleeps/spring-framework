package ru.otus.spring.service;

import ru.otus.spring.model.mongo.BookMongo;

import java.util.List;

public interface BookService {

    long countBooks();

    List<BookMongo> findAllBooks();
}
