package ru.otus.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    Book findByTitle(String title);

    List<Book> findAllByAuthorsContains(Author author);

    List<Book> findAllByGenresContains(Genre genre);

    void deleteAllByAuthorsContains(Author author);

    void deleteAllByGenresContains(Genre genre);

}
