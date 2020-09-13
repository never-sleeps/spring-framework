package ru.otus.spring.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dao.repositories.AuthorRepository;
import ru.otus.spring.dao.repositories.BookRepository;
import ru.otus.spring.dao.repositories.CommentRepository;
import ru.otus.spring.dao.repositories.GenreRepository;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.model.Genre;
import ru.otus.spring.model.dto.BookDto;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/api/book")
    public Flux<BookDto> getAll() {
        log.info("Получение списка книг");
        return bookRepository.findAll()
                .map(BookDto::of);
    }

    @GetMapping("/api/book/{id}")
    public Mono<BookDto> get(@PathVariable("id") String id) {
        log.info("Получение книги с id: " + id);
        return bookRepository.findById(id).switchIfEmpty(Mono.error(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Книга " + id + " не найдена")))
                .map(BookDto::of);
    }

    @PostMapping("/api/book")
    public Mono<BookDto> create(@RequestBody Book book) {
        log.info("Сохранение книги: " + book.toString());
        return getObjectMono(book).flatMap(b -> bookRepository.insert(book))
                .map(BookDto::of);
    }

    @PutMapping("/api/book")
    public Mono<BookDto> updateBook(@RequestBody Book book) {
        log.info("Сохранение книги с id: " + book.getId());
        bookRepository.findById(book.getId()).switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Книга " + book.getId() + " не найдена")));
        return getObjectMono(book).flatMap(b -> bookRepository.save(book))
                .map(BookDto::of);
    }

    @DeleteMapping("/api/book/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        log.info("Удаление книги с id: " + id);
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Книга " + id + " не найдена")))
                .flatMap(bookRepository::delete);
    }


    private Mono<Object> getObjectMono(Book book) {
        return Mono.zip(
                getRegistredAuthor(book),
                getRegistredGenre(book),
                getRegistredComment(book)
        ).map(objects -> {
            book.setAuthor(objects.getT1());
            book.setGenre(objects.getT2());
            book.setComment(objects.getT3());
            return book;
        });
    }

    private Mono<Genre> getRegistredGenre(Book bookInput) {
        String genreTitle = bookInput.getGenre().getTitle();
        return genreRepository.findByTitle(genreTitle)
                .switchIfEmpty(
                        genreRepository.insert(Genre.builder().title(genreTitle).build())
                );
    }

    private Mono<Author> getRegistredAuthor(Book bookInput) {
        String fullName = bookInput.getAuthor().getFullName();
        return authorRepository.findByFullName(fullName)
                .switchIfEmpty(
                        authorRepository.insert(Author.builder().fullName(fullName).build())
                );
    }

    private Mono<Comment> getRegistredComment(Book bookInput){
        if(bookInput.getComment() != null && bookInput.getComment().getText().length() > 0){
            Comment comment = Comment.builder().text(bookInput.getComment().getText()).build();
            return commentRepository.insert(comment);
        }
        return Mono.empty();
    }
}