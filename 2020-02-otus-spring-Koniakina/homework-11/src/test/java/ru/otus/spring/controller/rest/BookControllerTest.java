package ru.otus.spring.controller.rest;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
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

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("rest контроллер")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebFluxTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private CommentRepository commentRepository;

    private static final String SERVICE_URI = "/api/book";
    private final String BOOK_ID_1 = "1";
    private final String BOOK_ID_2 = "2";
    private final String NOT_EXIST_BOOK_ID = "3";

    private final String BOOK_INPUT = "{\"id\":\"1\",\"title\":\"White Fang\",\"author\":{\"id\":\"1\",\"fullName\":\"Jack London\"},\"genre\":{\"id\":\"1\",\"title\":\"novel\"},\"comment\":{\"id\":\"1\",\"text\":\"test\"}}";

    private Book book1;
    private Book book2;
    private Author author;
    private Genre genre;
    private Comment comment;

    @BeforeEach
    void setUp() {
        String AUTHOR_ID = "1";
        author = Author.builder().id(AUTHOR_ID).fullName("Jack London").build();
        String GENRE_ID = "1";
        genre = Genre.builder().id(GENRE_ID).title("novel").build();
        String COMMENT_ID = "1";
        comment = Comment.builder().id(COMMENT_ID).text("test").build();
        book1 = Book.builder()
                .id(BOOK_ID_1)
                .title("White Fang")
                .author(author)
                .genre(genre)
                .comment(comment)
                .build();
        book2 = Book.builder()
                .id(BOOK_ID_2)
                .title("Sea wolf")
                .author(author)
                .genre(genre)
                .comment(comment)
                .build();
    }


    @DisplayName(" должен возвращать данные конкретной книги")
    @Order(1)
    @Test
    void shouldCheckGetBook() {
        when(bookRepository.findById(BOOK_ID_1)).thenReturn(Mono.just(book1));

        EntityExchangeResult<byte[]> result = webClient.get().uri(SERVICE_URI + "/" + BOOK_ID_1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody().returnResult();
        Assert.assertNotNull(result.getResponseBody());
        String content = new String(result.getResponseBody(), StandardCharsets.UTF_8);
        System.out.println(content);
        assertThat(content).isNotNull()
                .contains("\"id\":", book1.getId())
                .contains("\"title\":", book1.getTitle())
                .contains("\"author\":", book1.getAuthor().getId(), book1.getAuthor().getFullName())
                .contains("\"genre\":", book1.getGenre().getId(), book1.getGenre().getTitle())
                .contains("\"comment\":", book1.getComment().getId(), book1.getComment().getText());
    }


    @DisplayName(" должен возвращать 404 при попытке получения несуществующей книги")
    @Order(2)
    @Test
    void shouldCheckGetBookFor404() {
        when(bookRepository.findById(NOT_EXIST_BOOK_ID)).thenReturn(Mono.empty());
        webClient.get().uri(SERVICE_URI + "/" + NOT_EXIST_BOOK_ID)
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @DisplayName(" должен удалять книгу по id")
    @Order(3)
    @Test
    void shouldCheckDeleteBook() {
        when(bookRepository.findById(any(String.class))).thenReturn(Mono.just(book1));
        when(bookRepository.delete(any(Book.class))).thenReturn(Mono.empty());
        webClient.delete().uri(SERVICE_URI + "/" + BOOK_ID_1)
                .exchange()
                .expectStatus().isOk();
    }

    @DisplayName(" должен возвращать 404 при попытке удаления несуществующей книги")
    @Order(3)
    @Test
    void shouldCheckDeleteBookFor404() {
        when(bookRepository.findById(any(String.class))).thenReturn(Mono.empty());
        webClient.delete().uri(SERVICE_URI + "/" + NOT_EXIST_BOOK_ID)
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @DisplayName(" должен возвращать список книг")
    @Order(5)
    @Test
    void shouldCheckGetBooks() {
        when(bookRepository.findAll()).thenReturn(Flux.fromIterable(Arrays.asList(book1, book2)));
        EntityExchangeResult<byte[]> result = webClient.get().uri(SERVICE_URI)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody().returnResult();
        Assert.assertNotNull(result.getResponseBody());
        String content = new String(result.getResponseBody(), StandardCharsets.UTF_8);
        assertThat(content).isNotNull()
                .startsWith("[{").endsWith("}]")
                .contains("\"id\":", book1.getId())
                .contains("\"title\":", book1.getTitle())
                .contains("\"author\":", book1.getAuthor().getId(), book1.getAuthor().getFullName())
                .contains("\"genre\":", book1.getGenre().getId(), book1.getGenre().getTitle())
                .contains("\"comment\":", book1.getComment().getId(), book1.getComment().getText())
                .contains("\"id\":", book2.getId())
                .contains("\"title\":", book2.getTitle())
                .contains("\"author\":", book2.getAuthor().getId(), book2.getAuthor().getFullName())
                .contains("\"genre\":", book2.getGenre().getId(), book2.getGenre().getTitle())
                .contains("\"comment\":", book2.getComment().getId(), book2.getComment().getText());
    }
}
