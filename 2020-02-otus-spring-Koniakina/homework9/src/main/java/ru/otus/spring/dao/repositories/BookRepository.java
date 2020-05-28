package ru.otus.spring.dao.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    Book findByTitle(String title);

    List<Book> findAllByAuthor(Author author);

    List<Book> findAllByGenre(Genre genre);

    void deleteAllByAuthor(Author author);

    void deleteAllByGenre(Genre genre);

}
