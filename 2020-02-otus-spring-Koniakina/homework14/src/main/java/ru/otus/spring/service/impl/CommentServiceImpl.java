package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.model.mongo.CommentMongo;
import ru.otus.spring.repositories.mongo.CommentRepository;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookService bookService;

    @Override
    public long countComments() {
        return commentRepository.count();
    }

    @Override
    public List<CommentMongo> findAllComments() {
        return commentRepository.findAll();
    }
}
