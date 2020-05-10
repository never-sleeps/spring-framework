package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.exception.EntityCreateException;
import ru.otus.spring.exception.EntityDeleteException;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final BookDao bookDao;

    @Override
    public long countComments() {
        return commentDao.count();
    }

    @Override
    public Comment createComment(long bookId, String text) {
        Book book = bookDao.getById(bookId);
        if (book == null){
            throw new EntityCreateException(String.format("Книга с id '%d' не найдена", bookId));
        }
        Comment newComment = Comment.builder().text(text).book(book).build();
        return commentDao.create(newComment);
    }

    @Override
    public void deleteComment(long id) {
        if(getCommentById(id) == null){
            throw new EntityDeleteException(String.format("Комментарий с id '%d' не найден", id));
        }
        commentDao.delete(id);
    }

    @Override
    public void deleteCommentByBook(long bookId) {
        if (bookDao.getById(bookId) == null){
            throw new EntityDeleteException(String.format("Книга с Id '%d' не найдена", bookId));
        }
        commentDao.deleteByBook(bookId);
    }

    @Override
    public Comment getCommentById(long id) {
        return commentDao.getById(id);
    }

    @Override
    public List<Comment> getCommentsByBook(long id) {
        return commentDao.getByBook(id);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentDao.getAll();
    }
}
