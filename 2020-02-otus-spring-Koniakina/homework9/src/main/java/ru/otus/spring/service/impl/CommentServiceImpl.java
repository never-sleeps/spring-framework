package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.repositories.CommentRepository;
import ru.otus.spring.exception.EntityCreateException;
import ru.otus.spring.exception.EntityDeleteException;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public long countComments() {
        return commentRepository.count();
    }

    @Override
    public Comment createComment(String text) {
        Comment newComment = Comment.builder().text(text).build();
        return commentRepository.save(newComment);
    }

    @Override
    public void deleteComment(String id) {
        Optional<Comment> optionalComment = findCommentById(id);
        if(optionalComment.isEmpty()){
            throw new EntityDeleteException(String.format("Комментарий с id '%s' не найден", id));
        }
        commentRepository.delete(optionalComment.get());
    }

    @Override
    public Optional <Comment> findCommentById(String id) {
        return commentRepository.findById(id);
    }


    @Override
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }
}
