package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.model.mongo.BookMongo;
import ru.otus.spring.repositories.mongo.BookRepository;
import ru.otus.spring.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public long countBooks() {
        return bookRepository.count();
    }

    @Override
    public List<BookMongo> findAllBooks() {
        return bookRepository.findAll();
    }
}