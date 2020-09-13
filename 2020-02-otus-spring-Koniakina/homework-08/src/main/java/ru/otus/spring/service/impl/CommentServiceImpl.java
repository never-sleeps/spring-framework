package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.exception.EntityCreateException;
import ru.otus.spring.exception.EntityDeleteException;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.repositories.CommentRepository;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.List;
import java.util.Optional;

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
    public Comment createComment(String bookId, String text) {
        Optional <Book> optionalBook = bookService.findBookById(bookId);
        if (optionalBook.isEmpty()){
            throw new EntityCreateException(String.format("Книга с id '%s' не найдена", bookId));
        }
        Comment newComment = Comment.builder().text(text).book(optionalBook.get()).build();
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
    public void deleteCommentsByBook(String bookId) {
        Optional<Book> optionalBook = bookService.findBookById(bookId);
        if (optionalBook.isEmpty()){
            throw new EntityDeleteException(String.format("Книга с Id '%s' не найдена", bookId));
        }
        commentRepository.deleteAllByBook(optionalBook.get());
    }

    @Override
    public Optional <Comment> findCommentById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findCommentsByBook(String id) {
        Optional<Book> optionalBook = bookService.findBookById(id);
        if(optionalBook.isEmpty()){
            return List.of();
        }
        return commentRepository.findAllByBook(optionalBook.get());
    }

    @Override
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }
}
