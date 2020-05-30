package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.repositories.CommentRepository;
import ru.otus.spring.exception.EntityDeleteException;
import ru.otus.spring.exception.EntityUpdateException;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.model.Genre;
import ru.otus.spring.dao.repositories.BookRepository;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @Override
    public long countBooks() {
        return bookRepository.count();
    }

    @Override
    public Book createBook(String bookTitle, String authorName, String genreTitle) {
        Book newBook = Book.builder()
                .title(bookTitle)
                .author(getRegistredAuthor(authorName))
                .genre(getRegistredGenre(genreTitle))
                .build();
        return bookRepository.save(newBook);
    }

    @Override
    public Book createBook(Book book){
        Book createdBook = createBook(book.getTitle(), book.getAuthor().getFullName(), book.getGenre().getTitle());
        if(book.getComment() != null && book.getComment().getText().length() > 0){
            Comment comment = commentService.createComment(book.getComment().getText());
            createdBook.setComment(comment);
            createdBook = bookRepository.save(createdBook);
        }
        return createdBook;
    }

    @Override
    public Book updateBook(String id, String bookTitle, String authorName, String genreTitle) {
        Optional<Book> optionalBook = findBookById(id);
        if (optionalBook.isEmpty()) {
            throw new EntityUpdateException(String.format("Книга с id '%s' не найдена", id));
        }
        Book book = optionalBook.get();
        if(!bookTitle.isEmpty())
            book.setTitle(bookTitle);
        if(!authorName.isEmpty())
            book.setAuthor(getRegistredAuthor(authorName));
        if(!genreTitle.isEmpty())
            book.setGenre(getRegistredGenre(genreTitle));
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book){
        Book updatedBook = updateBook(book.getId(), book.getTitle(),
                book.getAuthor().getFullName(), book.getGenre().getTitle());
        if(book.getComment() != null && book.getComment().getText().length() > 0){
            Comment comment = commentService.createComment(book.getComment().getText());
            updatedBook.setComment(comment);
        } else{
            updatedBook.setComment(null);
        }
        return bookRepository.save(updatedBook);
    }

    @Override
    public void deleteBook(String id) {
        Optional<Book> optionalBook = findBookById(id);
        if(optionalBook.isEmpty())
            throw new EntityDeleteException(String.format("Книга с id '%s' не найдена", id));

        bookRepository.delete(optionalBook.get());
    }

    @Override
    public Optional<Book> findBookById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> findBooksByAuthor(String name) {
        Optional<Author> author = authorService.findAuthorByName(name);
        return (author.isPresent()) ? bookRepository.findAllByAuthor(author.get()) : List.of();
    }

    @Override
    public List<Book> findBooksByGenre(String title) {
        Optional<Genre> genre = genreService.findGenreByTitle(title);
        return (genre.isPresent()) ? bookRepository.findAllByGenre(genre.get()) : List.of();
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }


    private Author getRegistredAuthor(String authorName){
        return authorService.getRegisteredAuthor(authorName);
    }

    private Genre getRegistredGenre(String genreTitle){
        return genreService.getRegisteredGenre(genreTitle);
    }

}