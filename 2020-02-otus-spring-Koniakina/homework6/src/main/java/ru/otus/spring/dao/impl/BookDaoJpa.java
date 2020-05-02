package ru.otus.spring.dao.impl;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery("select count(b) from Book b", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Book create(Book book) {
        if (book.getId() == 0) {
            entityManager.persist(book);
            return book;
        } else {
            return null;
        }
    }

    @Override
    public Book update(Book book) {
        entityManager.merge(book);
        return book;
    }

    @Override
    public void delete(long id) {
        Query query = entityManager.createQuery("DELETE FROM Book b WHERE b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Book getById(long id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public Book getByTitle(String title) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT a FROM Book a WHERE a.title = :title", Book.class);
        query.setParameter("title", title);
        return DataAccessUtils.singleResult(query.getResultList());
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        if(author == null) return List.of();
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b " +
                        "JOIN b.authors a " +
                        "JOIN b.genres g " +
                        "WHERE a.fullName = :authorName " +
                        "GROUP BY b ORDER BY b.title ",
                Book.class);
        query.setParameter("authorName", author.getFullName());
        return query.getResultList();
    }

    @Override
    public List<Book> getByGenre(Genre genre) {
        if(genre == null) return List.of();
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b " +
                        "JOIN b.authors a " +
                        "JOIN b.genres g " +
                        "WHERE g.title = :title " +
                        "GROUP BY b ORDER BY b.title ",
                Book.class);
        query.setParameter("title", genre.getTitle());
        return query.getResultList();
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b", Book.class);
        return query.getResultList();
    }
}
