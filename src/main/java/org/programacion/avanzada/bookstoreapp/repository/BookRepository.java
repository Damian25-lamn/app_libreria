package org.programacion.avanzada.bookstoreapp.repository;

import org.programacion.avanzada.bookstoreapp.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, String> {
}
