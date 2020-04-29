package ru.otus.spring.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from AUTHORS", Integer.class);
    }

    @Override
    public Author create(Author author) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValues(Map.of("name", author.getFullName()));
        jdbc.update("insert into AUTHORS (FULLNAME) values (:name)", params, keyHolder);

        return (keyHolder.getKey() != null) ? getById(keyHolder.getKey().longValue()).get() : null;
    }

    @Override
    public boolean isExist(Author author) {
        return jdbc.queryForObject(
                "select count(ID)>0 from AUTHORS where FULLNAME = :fullName",
                Map.of("fullName", author.getFullName()),
                Boolean.class);
    }

    @Override
    public Author update(Author author) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValues(Map.of("id", author.getId(), "name", author.getFullName()));
        jdbc.update("update AUTHORS set FULLNAME = :name where ID = :id", params, keyHolder);

        return (keyHolder.getKey() != null) ? getById(keyHolder.getKey().longValue()).get() : null;
    }

    @Override
    public void delete(long id) {
        jdbc.update("delete from AUTHORS where ID = :id", Map.of("id", id));
    }

    @Override
    public Optional<Author> getById(long id) {
        return jdbc.query("select ID, FULLNAME from AUTHORS where ID = :id",
                Map.of("id", id), new AuthorMapper())
                .stream().findFirst();
    }

    @Override
    public Optional<Author> getByName(String fullName) {
        return jdbc.query("select ID, FULLNAME from AUTHORS where FULLNAME = :fullName",
                Map.of("fullName", fullName), new AuthorMapper())
                .stream().findFirst();
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select ID, FULLNAME from AUTHORS", new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("ID");
            String fullname = resultSet.getString("FULLNAME");
            return Author.builder()
                    .id(id)
                    .fullName(fullname)
                    .build();
        }
    }
}
