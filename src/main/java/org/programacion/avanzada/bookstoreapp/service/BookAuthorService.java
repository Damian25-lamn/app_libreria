package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.Author;
import org.programacion.avanzada.bookstoreapp.model.Book;
import org.programacion.avanzada.bookstoreapp.model.bookAuthor.BookAuthor;
import org.programacion.avanzada.bookstoreapp.model.bookAuthor.BookAuthorId;
import org.programacion.avanzada.bookstoreapp.repository.AuthorRepository;
import org.programacion.avanzada.bookstoreapp.repository.BookAuthorRepository;
import org.programacion.avanzada.bookstoreapp.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BookAuthorService {

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;
    private final BookAuthorRepository bookAuthorRepo;

    public BookAuthorService(BookRepository bookRepo, AuthorRepository authorRepo, BookAuthorRepository bookAuthorRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.bookAuthorRepo = bookAuthorRepo;
    }

    @Transactional
    public void guardarRelacionLibroAutor(String isbn, Integer authorId) {
        BookAuthorId id = new BookAuthorId(isbn, authorId);

        if (!bookAuthorRepo.existsByCompositeId(id)) {
            bookAuthorRepo.save(new BookAuthor(id));
        }
    }

    @Transactional
    public void eliminarRelacionesPorIsbn(String isbn) {
        bookAuthorRepo.deleteByBookIsbn(isbn);
    }

    @Transactional
    public void eliminarRelacionesPorAuthor(Integer authorId) {
        bookAuthorRepo.deleteByAuthorId(authorId);
    }

    @Transactional(readOnly = true)
    public List<Author> listarAutoresDeLibro(String isbn) {
        return bookAuthorRepo.findByBooksIsbn(isbn).stream()
                .map(rel -> authorRepo.findById(rel.getAuthorsId()).orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Book> listarLibrosDeAutor(Integer authorId) {
        return bookAuthorRepo.findByAuthorsId(authorId).stream()
                .map(rel -> bookRepo.findById(rel.getBooksIsbn()).orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }


}
