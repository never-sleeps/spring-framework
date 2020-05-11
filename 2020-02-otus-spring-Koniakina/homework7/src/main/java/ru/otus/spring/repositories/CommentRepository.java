package ru.otus.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Comment c where c.book = :book")
    void deleteAllByBook(@Param(value = "book") Book book);

    List<Comment> findAllByBook(Book book);

}
