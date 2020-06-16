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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        book.setAuthor(getRegistredAuthor(book));
        book.setGenre(getRegistredGenre(book));
        book.setComment(getRegistredComment(book));
        return bookRepository.insert(book)
                .map(BookDto::of);
    }

    @PutMapping("/api/book")
    public Mono<BookDto> updateBook(@RequestBody Book book) {
        log.info("Сохранение книги с id: " + book.getId());
        if (bookRepository.findById(book.getId()).blockOptional().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Книга " + book.getId() + " не найдена");
        }
        book.setAuthor(getRegistredAuthor(book));
        book.setGenre(getRegistredGenre(book));
        book.setComment(getRegistredComment(book));
        return bookRepository.save(book)
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


    private Genre getRegistredGenre(Book bookInput) {
        String genreTitle = bookInput.getGenre().getTitle();
        Optional<Genre> optionalGenre = genreRepository.findByTitle(genreTitle).blockOptional();
        if(optionalGenre.isPresent()){
            return optionalGenre.get();
        }else {
            Genre genre = Genre.builder().title(genreTitle).build();
            return genreRepository.insert(genre).block();
        }
    }

    private Author getRegistredAuthor(Book bookInput) {
        String authorName = bookInput.getAuthor().getFullName();
        Optional<Author> optionalAuthor = authorRepository.findByFullName(authorName).blockOptional();
        if(optionalAuthor.isPresent()){
            return optionalAuthor.get();
        }else {
            Author author = Author.builder().fullName(authorName).build();
            return authorRepository.insert(author).block();
        }
    }
    private Comment getRegistredComment(Book bookInput){
        if(bookInput.getComment() != null && bookInput.getComment().getText().length() > 0){
            Comment comment = Comment.builder().text(bookInput.getComment().getText()).build();
            return commentRepository.insert(comment).block();
        }
        return null;
    }
}