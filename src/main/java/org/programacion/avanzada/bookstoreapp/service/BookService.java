package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.Book;
import org.programacion.avanzada.bookstoreapp.repository.BookRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepo;
    private final BookAuthorService bookAuthorService;
    private final InventoryService inventoryService;
    private final LineItemService lineItemService;

    public BookService(BookRepository bookRepo, BookAuthorService bookAuthorService, InventoryService inventoryService, LineItemService lineItemService) {
        this.bookRepo = bookRepo;
        this.bookAuthorService = bookAuthorService;
        this.inventoryService = inventoryService;
        this.lineItemService = lineItemService;
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

    @Transactional
    public void eliminarLibro(String isbn) {
        inventoryService.eliminarDelInventario(isbn);
        bookAuthorService.eliminarRelacionesPorIsbn(isbn);
        lineItemService.eliminarItemsPorIsbn(isbn);
        bookRepo.deleteById(isbn);
    }

    @Transactional(readOnly = true)
    public List<Book> listarLibros() {
        List<Book> lista = new ArrayList<>();
        bookRepo.findAll().forEach(lista::add);
        return lista;
    }
}
