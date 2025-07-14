package org.programacion.avanzada.bookstoreapp.repository;

import org.programacion.avanzada.bookstoreapp.model.bookAuthor.BookAuthor;
import org.programacion.avanzada.bookstoreapp.model.bookAuthor.BookAuthorId;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookAuthorRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookAuthorRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookAuthor> findByBooksIsbn(String isbn) {
        String sql = "SELECT books_isbn, authors_id FROM books_authors WHERE books_isbn = :isbn";
        Map<String, Object> params = Map.of("isbn", isbn);
        return jdbcTemplate.query(sql, params, mapRow());
    }

    public List<BookAuthor> findByAuthorsId(Integer authorId) {
        String sql = "SELECT books_isbn, authors_id FROM books_authors WHERE authors_id = :authorId";
        Map<String, Object> params = Map.of("authorId", authorId);
        return jdbcTemplate.query(sql, params, mapRow());
    }

    public boolean existsByCompositeId(BookAuthorId id) {
        String sql = "SELECT COUNT(*) FROM books_authors WHERE books_isbn = :isbn AND authors_id = :authorId";
        Map<String, Object> params = Map.of(
                "isbn", id.getBooksIsbn(),
                "authorId", id.getAuthorsId()
        );
        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

    public void save(BookAuthor relation) {
        String sql = "INSERT INTO books_authors (books_isbn, authors_id) VALUES (:isbn, :authorId)";
        Map<String, Object> params = Map.of(
                "isbn", relation.getBooksIsbn(),
                "authorId", relation.getAuthorsId()
        );
        jdbcTemplate.update(sql, params);
    }

    private RowMapper<BookAuthor> mapRow() {
        return (rs, rowNum) -> new BookAuthor(
                new BookAuthorId(
                        rs.getString("books_isbn"),
                        rs.getInt("authors_id")
                )
        );
    }

    public void deleteByBookIsbn(String isbn) {
        String sql = "DELETE FROM books_authors WHERE books_isbn = :isbn";
        Map<String, Object> params = Map.of("isbn", isbn);
        jdbcTemplate.update(sql, params);
    }

    public void deleteByAuthorId(Integer authorId) {
        String sql = "DELETE FROM books_authors WHERE authors_id = :authorId";
        Map<String, Object> params = Map.of("authorId", authorId);
        jdbcTemplate.update(sql, params);
    }
}