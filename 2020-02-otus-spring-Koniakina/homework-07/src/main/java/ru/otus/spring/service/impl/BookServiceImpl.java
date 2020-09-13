package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.exception.EntityDeleteException;
import ru.otus.spring.exception.EntityUpdateException;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;


    @Override
    public long countBooks() {
        return bookRepository.count();
    }

    @Override
    public Book createBook(String bookTitle, String[] authorNames, String[] genreTitles) {
        Book newBook = Book.builder()
                .title(bookTitle)
                .authors(getRegistredAuthors(authorNames))
                .genres(getRegistredGenres(genreTitles))
                .comments(List.of())
                .build();
        return bookRepository.save(newBook);
    }

    @Override
    public Book updateBook(long id, String bookTitle, String[] authorNames, String[] genreTitles) {
        Optional<Book> optionalBook = findBookById(id);
        if (optionalBook.isEmpty()) {
            throw new EntityUpdateException(String.format("Книга с id '%s' не найдена", id));
        }
        Book book = optionalBook.get();
        if(!bookTitle.isEmpty())
            book.setTitle(bookTitle);
        if(!genreTitles[0].isEmpty())
            book.setAuthors(getRegistredAuthors(authorNames));
        if(!genreTitles[0].isEmpty())
            book.setGenres(getRegistredGenres(genreTitles));
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(long id) {
        Optional<Book> optionalBook = findBookById(id);
        if(optionalBook.isEmpty()){
            throw new EntityDeleteException(String.format("Книга с id '%d' не найдена", id));
        }
        bookRepository.delete(optionalBook.get());
    }

    @Override
    public Optional<Book> findBookById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> findBooksByAuthor(String name) {
        Author author = authorService.findAuthorByName(name);
        return bookRepository.findAllByAuthorsContains(author);
    }

    @Override
    public List<Book> findBooksByGenre(String title) {
        Genre genre = genreService.findGenreByTitle(title);
        return bookRepository.findAllByGenresContains(genre);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }


    private List<Author> getRegistredAuthors(String[] authorNames){
        List<Author> authors = new ArrayList<>();
        for (String authorName: authorNames) {
            authors.add(authorService.getRegisteredAuthor(authorName));
        }
        return authors;
    }

    private List<Genre> getRegistredGenres(String[] genreTitles){
        List<Genre> genres = new ArrayList<>();
        for (String genreTitle: genreTitles) {
            genres.add(genreService.getRegisteredGenre(genreTitle));
        }
        return genres;
    }

}