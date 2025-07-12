package org.programacion.avanzada.bookstoreapp.repository;

import org.programacion.avanzada.bookstoreapp.model.BookAuthor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookAuthorRepository extends CrudRepository<BookAuthor, Void> {
    List<BookAuthor> findByBooksIsbn(String isbn);
    List<BookAuthor> findByAuthorsId(Integer authorId);
}