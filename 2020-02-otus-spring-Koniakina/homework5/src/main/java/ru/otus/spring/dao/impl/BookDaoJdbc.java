package ru.otus.spring.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final static String SELECT_FROM_BOOKS =
            "select * from BOOKS " +
                    "join AUTHORS on BOOKS.AUTHOR_ID = AUTHORS.ID " +
                    "join GENRES on BOOKS.GENRE_ID = GENRES.ID";

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from BOOKS", Integer.class);
    }

    @Override
    public Book create(Book book) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValues(Map.of("title", book.getTitle(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId()));
        jdbc.update( "insert into BOOKS (TITLE, AUTHOR_ID, GENRE_ID) " +
                        "values (:title, :author_id, :genre_id)", params, keyHolder);
        return (keyHolder.getKey() != null) ? getById(keyHolder.getKey().longValue()).get() : null;
    }

    @Override
    public Book update(Book book) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValues(Map.of("title", book.getTitle(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId(),
                "id", book.getId()));
        jdbc.update("update books b " +
                        "set TITLE = :title, AUTHOR_ID = :author_id, GENRE_ID = :genre_id " +
                        "where ID = :id", params, keyHolder);
        return (keyHolder.getKey() != null) ? getById(keyHolder.getKey().longValue()).get() : null;
    }

    @Override
    public boolean isExist(Book book) {
        return jdbc.queryForObject(
                "select count(BOOKS.ID)>0 from BOOKS " +
                        "join AUTHORS on BOOKS.AUTHOR_ID = AUTHORS.id " +
                        "join GENRES on BOOKS.GENRE_ID = GENRES.id " +
                        "where BOOKS.TITLE = :bookTitle " +
                            "and AUTHORS.FULLNAME = :authorName " +
                            "and GENRES.TITLE = :genreTitle",
                Map.of("bookTitle", book.getTitle(),
                        "authorName", book.getAuthor().getFullName(),
                        "genreTitle", book.getGenre().getTitle()),
                Boolean.class);
    }

    @Override
    public void delete(long id) {
        jdbc.update("delete from BOOKS where ID = :id", Map.of("id", id));
    }

    @Override
    public Optional<Book> getById(long id){
        return jdbc.query(SELECT_FROM_BOOKS + " where BOOKS.ID = :id",
                Map.of("id", id), new BookMapper()).stream().findFirst();
    }

    @Override
    public Optional<Book> getByTitle(String title) {
        return jdbc.query(SELECT_FROM_BOOKS + " where BOOKS.TITLE = :title",
                Map.of("title", title), new BookMapper()).stream().findFirst();
    }

    @Override
    public List<Book> getByAuthor(String fullName) {
        return jdbc.query(SELECT_FROM_BOOKS + " where AUTHORS.fullname = :fullName",
                Map.of("fullName", fullName), new BookMapper());
    }

    @Override
    public List<Book> getByGenre(String title) {
        return jdbc.query(SELECT_FROM_BOOKS + " where GENRES.TITLE = :title",
                Map.of("title", title), new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(SELECT_FROM_BOOKS, new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("BOOKS.ID");
            String title = resultSet.getString("BOOKS.TITLE");
            long authorId = resultSet.getLong("BOOKS.AUTHOR_ID");
            String fullName = resultSet.getString("AUTHORS.FULLNAME");
            long genreId = resultSet.getLong("BOOKS.GENRE_ID");
            String genreTitle = resultSet.getString("GENRES.TITLE");
            return Book.builder()
                    .id(id)
                    .title(title)
                    .author(Author.builder()
                            .id(authorId)
                            .fullName(fullName)
                            .build())
                    .genre(Genre.builder()
                            .id(genreId)
                            .title(genreTitle)
                            .build())
                    .build();
        }
    }
}
