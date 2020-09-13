package ru.otus.spring.controller.rest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;
import ru.otus.spring.security.UserDetailsServiceImp;
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
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LibraryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @MockBean
    private MessageService messageService;
    @MockBean
    private UserDetailsServiceImp userDetailsService;

    private final String ADMIN = "ADMIN";
    private final String USER = "USER";
    private final String ANONYMOUS = "ANONYMOUS";

    private final int ACCESS_IS_DENIED = 403;
    private final int AUTHENTIFICATION_REQUIRED = 302;

    private final String LOGIN_PAGE = "http://localhost/login";



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


    @DisplayName("[для ANONYMOUS]  должен возвращать список книг")
    @Test
    @Order(1)
    void shouldCheckGetBooksForAnonymous() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken(
                ANONYMOUS, ANONYMOUS, AuthorityUtils.createAuthorityList(ANONYMOUS)));

        given(bookService.findAllBooks()).willReturn(List.of(book));
        mockMvc.perform(get("/book"))
                .andDo(print())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("books", List.of(book)))
                .andExpect(status().isOk());
    }

    @DisplayName("[для USER] должен возвращать список книг")
    @Test
    @Order(2)
    @WithMockUser(username = "user", roles = { USER })
    void shouldCheckGetBooksForUser() throws Exception {
        given(bookService.findAllBooks()).willReturn(List.of(book));
        mockMvc.perform(get("/book"))
                .andDo(print())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("books", List.of(book)))
                .andExpect(status().isOk());
    }

    @DisplayName("[для ANONYMOUS] не должен переходить на страницу создания книги")
    @Test
    @Order(3)
    void shouldCheckSaveBookForAnonymous() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken(
                ANONYMOUS, ANONYMOUS, AuthorityUtils.createAuthorityList(ANONYMOUS)));

        given(bookService.createBook(book)).willReturn(book);
        mockMvc.perform(post("/book/add", book.getId()))
                .andDo(print())
                .andExpect(redirectedUrl(LOGIN_PAGE))
                .andExpect(status().is(AUTHENTIFICATION_REQUIRED));
    }

    @DisplayName("[для USER] должен сохранять новую книгу")
    @Test
    @Order(4)
    @WithMockUser(username = "user", roles = { USER })
    void shouldCheckSaveBookForUser() throws Exception {
        given(bookService.createBook(book)).willReturn(book);
        mockMvc.perform(post("/book/add", book.getId()))
                .andDo(print())
                .andExpect(view().name("edit"))
                .andExpect(status().isOk());
    }

    @DisplayName("[для ANONYMOUS] не должен возвращать данные конкретной книги")
    @Test
    @Order(5)
    void shouldCheckGetBookForAnonymous() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken(
                ANONYMOUS, ANONYMOUS, AuthorityUtils.createAuthorityList(ANONYMOUS)));

        given(bookService.findBookById(book.getId())).willReturn(Optional.of(book));
        mockMvc.perform(get(String.format("/book/%s/edit", book.getId())))
                .andDo(print())
                .andExpect(redirectedUrl(LOGIN_PAGE))
                .andExpect(status().is(AUTHENTIFICATION_REQUIRED));
    }

    @DisplayName("[для USER] должен возвращать данные конкретной книги")
    @Test
    @Order(6)
    @WithMockUser(username = "user", roles = { USER })
    void shouldCheckGetBookForUser() throws Exception {
        given(bookService.findBookById(book.getId())).willReturn(Optional.of(book));
        mockMvc.perform(get(String.format("/book/%s/edit", book.getId())))
                .andDo(print())
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("book", book))
                .andExpect(status().isOk());
    }

    @DisplayName("[для USER] должен обновлять данные конкретной книги")
    @Test
    @Order(7)
    @WithMockUser(username = "user", roles = { USER })
    void shouldCheckUpdateBookForUser() throws Exception {
        given(bookService.updateBook(book)).willReturn(book);
        mockMvc.perform(post(String.format("/book/%s/update", book.getId())))
                .andDo(print())
                .andExpect(view().name("edit"))
                .andExpect(status().isOk());
    }

    @DisplayName("[для ANONYMOUS] не должен удалять книгу по id")
    @Test
    @Order(8)
    void shouldCheckDeleteBookForUserRole() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken(
                ANONYMOUS, ANONYMOUS, AuthorityUtils.createAuthorityList(ANONYMOUS)));
        doNothing().when(bookService).deleteBook(book.getId());
        mockMvc.perform(get(String.format("/book/%s/delete", book.getId())))
                .andDo(print())
                .andExpect(redirectedUrl(LOGIN_PAGE))
                .andExpect(status().is(AUTHENTIFICATION_REQUIRED));
    }

    @DisplayName("[для USER] не должен удалять книгу по id")
    @Test
    @Order(9)
    @WithMockUser(username = "iser", roles = { USER })
    void shouldCheckDeleteBookForAdminRole() throws Exception {
        doNothing().when(bookService).deleteBook(book.getId());
        mockMvc.perform(get(String.format("/book/%s/delete", book.getId())))
                .andDo(print())
                .andExpect(forwardedUrl("/access-denied"))
                .andExpect(status().is(ACCESS_IS_DENIED));
    }

    @DisplayName("[для ADMIN] должен удалять книгу по id")
    @Test
    @Order(10)
    @WithMockUser(username = "admin", roles = { ADMIN })
    void shouldCheckDeleteBookForUser() throws Exception {
        doNothing().when(bookService).deleteBook(book.getId());
        mockMvc.perform(get(String.format("/book/%s/delete", book.getId())))
                .andDo(print())
                .andExpect(redirectedUrl("/book"))
                .andExpect(status().is3xxRedirection());
    }
}