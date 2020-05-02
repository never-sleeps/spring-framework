package ru.otus.spring.dao.impl;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(g) FROM Genre g", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Genre create(Genre genre) {
        if (genre.getId() <= 0) {
            entityManager.persist(genre);
        } else {
            entityManager.merge(genre);
        }
        return genre;
    }

    @Override
    public Genre update(Genre genre) {
        entityManager.merge(genre);
        return genre;
    }

    @Override
    public void delete(long id) {
        Query query = entityManager.createQuery("DELETE FROM Genre g WHERE g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Genre getById(long id) {
        return entityManager.find(Genre.class, id);
    }

    @Override
    public Genre getByTitle(String title) {
        TypedQuery<Genre> query = entityManager.createQuery(
                "SELECT g FROM Genre g WHERE g.title = :title", Genre.class);
        query.setParameter("title", title);
        query.setMaxResults(1);
        return DataAccessUtils.singleResult(query.getResultList());
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT g FROM Genre g", Genre.class);
        return query.getResultList();
    }
}
