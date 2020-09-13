package ru.otus.spring.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.model.Genre;


@Data
@NoArgsConstructor
public class BookDto {
    private String id;
    private String title;
    private Author author;
    private Genre genre;
    private Comment comment;

    public static BookDto of(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setGenre(book.getGenre());
        bookDto.setComment(book.getComment());
        return bookDto;
    }
}
