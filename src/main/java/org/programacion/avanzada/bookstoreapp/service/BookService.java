package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.Book;
import org.programacion.avanzada.bookstoreapp.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public Iterable<Book> listar() {
        return repo.findAll();
    }

}
