package ru.otus.spring.controller.rest;

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
import ru.otus.spring.model.Genre;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.MessageService;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("Контроллер")
@AutoConfigureDataMongo
@WebMvcTest(LibraryController.class)
class LibraryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @MockBean
    private MessageService messageService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .id("1")
                .title("Золотая рыбка")
                .author(Author.builder().fullName("А.С.Пушкин").build())
                .genre(Genre.builder().title("Сазка").build())
                .build();
    }

    @DisplayName(" должен возвращать список книг")
    @Test
    void shouldCheckGetBooks() throws Exception {
        given(bookService.findAllBooks()).willReturn(List.of(book));
        mockMvc.perform(get("/book"))
                .andDo(print())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("books", List.of(book)))
                .andExpect(status().isOk());
    }

    @DisplayName(" должен сохранять новую книгу")
    @Test
    void shouldCheckSaveBook() throws Exception {
        given(bookService.createBook(book)).willReturn(book);
        mockMvc.perform(post("/book/add", book.getId()))
                .andDo(print())
                .andExpect(view().name("edit"))
                .andExpect(status().isOk());
    }

    @DisplayName(" должен возвращать данные конкретной книги")
    @Test
    void shouldCheckGetBook() throws Exception {
        given(bookService.findBookById(book.getId())).willReturn(Optional.of(book));
        mockMvc.perform(get(String.format("/book/%s/edit", book.getId())))
                .andDo(print())
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("book", book))
                .andExpect(status().isOk());
    }

    @DisplayName(" должен обновлять данные конкретной книги")
    @Test
    void shouldCheckUpdateBook() throws Exception {
        given(bookService.updateBook(book)).willReturn(book);
        mockMvc.perform(post(String.format("/book/%s/update", book.getId())))
                .andDo(print())
                .andExpect(view().name("edit"))
                .andExpect(status().isOk());
    }

    @DisplayName(" должен удалять книгу по id")
    @Test
    void shouldCheckDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(book.getId());
        mockMvc.perform(get(String.format("/book/%s/delete", book.getId())))
                .andDo(print())
                .andExpect(redirectedUrl("/book"))
                .andExpect(status().is3xxRedirection());
    }
}