package ru.otus.spring.controller.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Strings;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.model.Genre;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.MessageService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@DisplayName("rest контроллер")
@AutoConfigureDataMongo
@WebMvcTest(BookController.class)
class BookControllerTest extends AbstractControllerTest{

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @MockBean
    private MessageService messageService;

    private Book book;
    private Author author;
    private Genre genre;
    private Comment comment;

    @BeforeEach
    void setUp() {
        author = Author.builder()
                .id("1").fullName("Jack London").build();
        genre = Genre.builder()
                .id("1").title("novel").build();
        comment = Comment.builder()
                .id("1").text("test").build();
        book = Book.builder()
                .id("1")
                .title("White Fang")
                .author(author)
                .genre(genre)
                .comment(comment)
                .build();
    }

    @DisplayName(" должен возвращать список книг")
    @Test
    void shouldCheckGetBooks() throws Exception {
        given(bookService.findAllBooks()).willReturn(List.of(book));

        val result = performAndGetResult(get("/api/book"), new TypeReference<List<Book>>() {});
        assertThat(result).isNotNull()
                .allMatch(b -> book.getId() != null)
                .allMatch(b -> !Strings.isNullOrEmpty(book.getTitle()))
                .allMatch(b -> b.getAuthor().equals(book.getAuthor()))
                .allMatch(b -> b.getGenre().equals(book.getGenre()))
                .allMatch(b -> b.getComment().equals(book.getComment()));
    }

    @DisplayName(" должен возвращать данные конкретной книги")
    @Test
    void shouldCheckGetBook() throws Exception {
        given(bookService.findBookById(book.getId())).willReturn(Optional.of(book));

        val result = performAndGetResult(get("/api/book/{id}", "1"), Book.class);
        assertThat(result).isNotNull().isEqualTo(book);
    }

    @DisplayName(" должен сохранять новую книгу")
    @Test
    void shouldCheckSaveBook() throws Exception {
        given(bookService.createBook(book)).willReturn(book);

        val result = performAndGetResult(post("/api/book"), book, Book.class);
        assertThat(result).isNotNull()
                .matches(b -> b.getId().equals(book.getId()))
                .matches(b -> b.getTitle().equals(book.getTitle()))
                .matches(b -> b.getAuthor().equals(book.getAuthor()))
                .matches(b -> b.getGenre().equals(book.getGenre()))
                .matches(b -> b.getComment().equals(book.getComment()));
    }


    @DisplayName(" должен обновлять данные конкретной книги")
    @Test
    void shouldCheckUpdateBook() throws Exception {
        given(bookService.updateBook(book)).willReturn(book);
        val result = performAndGetResult(put("/api/book"), book, Book.class);
        assertThat(result).isNotNull()
                .matches(b -> b.getId().equals(book.getId()))
                .matches(b -> b.getTitle().equals(book.getTitle()))
                .matches(b -> b.getAuthor().equals(book.getAuthor()))
                .matches(b -> b.getGenre().equals(book.getGenre()))
                .matches(b -> b.getComment().equals(book.getComment()));
    }
}