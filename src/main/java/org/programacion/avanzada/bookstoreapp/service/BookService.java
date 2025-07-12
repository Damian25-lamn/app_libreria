package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.Book;
import org.programacion.avanzada.bookstoreapp.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public void guardarLibro(Book book) {
        bookRepo.save(book);
    }

    public Optional<Book> buscarLibro(String isbn) {
        return bookRepo.findById(isbn);
    }

    public void eliminarLibro(String isbn) {
        bookRepo.deleteById(isbn);
    }

    public List<Book> listarLibros() {
        List<Book> lista = new ArrayList<>();
        bookRepo.findAll().forEach(lista::add);
        return lista;
    }
}
