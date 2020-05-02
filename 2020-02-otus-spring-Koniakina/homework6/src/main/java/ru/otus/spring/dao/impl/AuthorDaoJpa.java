package ru.otus.spring.dao.impl;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT (a) FROM Author a", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Author create(Author author) {
        if (author.getId() <= 0) {
            entityManager.persist(author);
        } else {
            entityManager.merge(author);
        }
        return author;
    }

    @Override
    public Author update(Author author) {
        entityManager.merge(author);
        return author;
    }

    @Override
    public void delete(long id) {
        Query query = entityManager.createQuery("DELETE FROM Author a WHERE a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Author getById(long id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public Author getByName(String fullName) {
        TypedQuery<Author> query = entityManager.createQuery(
                "SELECT a FROM Author a WHERE a.fullName = :fullName", Author.class);
        query.setParameter("fullName", fullName);
        query.setMaxResults(1);
        return DataAccessUtils.singleResult(query.getResultList());
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = entityManager.createQuery("SELECT a FROM Author a", Author.class);
        return query.getResultList();
    }
}
