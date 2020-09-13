package ru.otus.spring.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from GENRES", Integer.class);
    }

    @Override
    public Genre create(Genre genre) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValues(Map.of("title", genre.getTitle()));
        jdbc.update("insert into GENRES (TITLE) values (:title)", params, keyHolder);

        return (keyHolder.getKey() != null) ? getById(keyHolder.getKey().longValue()).get() : null;
    }

    @Override
    public Genre update(Genre genre) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValues(Map.of("name", genre.getTitle(), "id", genre.getId()));
        jdbc.update("update GENRES set TITLE = :name where ID = :id", params, keyHolder);

        return (keyHolder.getKey() != null) ? getById(keyHolder.getKey().longValue()).get() : null;
    }

    @Override
    public boolean isExist(Genre genre) {
        return jdbc.queryForObject(
                "select count(ID)>0 from GENRES where TITLE = :title",
                Map.of("title", genre.getTitle()),
                Boolean.class);
    }

    @Override
    public void delete(long id) {
        jdbc.update("delete from GENRES where ID = :id", Map.of("id", id));
    }

    @Override
    public Optional<Genre> getById(long id) {
        return jdbc.query("select * from GENRES where ID = :id",
                Map.of("id", id), new GenreMapper())
                .stream().findFirst();
    }

    @Override
    public Optional<Genre> getByTitle(String title) {
        return jdbc.query("select * from GENRES where TITLE = :title",
                Map.of("title", title), new GenreMapper())
                .stream().findFirst();
    }


    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from GENRES", new GenreMapper());
    }


    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("ID");
            String title = resultSet.getString("TITLE");
            return Genre.builder()
                    .id(id)
                    .title(title)
                    .build();
        }
    }
}