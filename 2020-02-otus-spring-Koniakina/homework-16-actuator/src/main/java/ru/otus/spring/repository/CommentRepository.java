package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.spring.model.Comment;

import java.util.List;

@RepositoryRestResource(path = "comments")
public interface CommentRepository extends JpaRepository<Comment, Long> {

  @RestResource(path = "books", rel = "bookId")
  List<Comment> findAllByBook_Id(Long bookId);
}
