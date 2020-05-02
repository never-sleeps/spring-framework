package ru.otus.spring.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public class CommentDaoJpa implements CommentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT (c) FROM Comment c", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Comment create(Comment comment) {
        if (comment.getId() <= 0) {
            entityManager.persist(comment);
        } else {
            entityManager.merge(comment);
        }
        return comment;
    }

    @Override
    public void delete(long id) {
        Query query = entityManager.createQuery("DELETE FROM Comment c WHERE c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteByBook(long id) {
        Query query = entityManager.createQuery("DELETE FROM Comment c WHERE c.book.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Comment getById(long id) {
        return entityManager.find(Comment.class, id);
    }

    @Override
    public List<Comment> getByBook(long id) {
        TypedQuery<Comment> query = entityManager.createQuery(
                "SELECT c FROM Comment c " +
                        "WHERE c.book.id = :id", Comment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Comment> getAll() {
        TypedQuery<Comment> query = entityManager.createQuery("SELECT c FROM Comment c", Comment.class);
        return query.getResultList();
    }
}
