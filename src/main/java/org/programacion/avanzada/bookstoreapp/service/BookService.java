package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.Book;
import org.programacion.avanzada.bookstoreapp.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Transactional
    public void guardarLibro(Book libro) {
        if (bookRepo.existsById(libro.getIsbn())) {
            bookRepo.save(libro);
        } else {
            bookRepo.save(libro);
        }
    }

    public Optional<Book> buscarLibro(String isbn) {
        return bookRepo.findById(isbn);
    }

    public void eliminarLibro(String isbn) {
        bookRepo.deleteById(isbn);
    }

    @Transactional(readOnly = true)
    public List<Book> listarLibros() {
        List<Book> lista = new ArrayList<>();
        bookRepo.findAll().forEach(lista::add);
        return lista;
    }
}
