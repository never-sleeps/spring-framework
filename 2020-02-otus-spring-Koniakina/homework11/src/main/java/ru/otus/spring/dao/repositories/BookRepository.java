package ru.otus.spring.dao.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Mono<Book> findByTitle(String title);

    Flux<Book> findAllByAuthor(Author author);

    Flux<Book> findAllByGenre(Genre genre);

    void deleteAllByAuthor(Author author);

    void deleteAllByGenre(Genre genre);

}
