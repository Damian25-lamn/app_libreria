package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.Author;
import org.programacion.avanzada.bookstoreapp.model.BookAuthor;
import org.programacion.avanzada.bookstoreapp.repository.AuthorRepository;
import org.programacion.avanzada.bookstoreapp.repository.BookAuthorRepository;
import org.programacion.avanzada.bookstoreapp.repository.BookRepository;
import org.springframework.stereotype.Service;

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

    public List<Author> getAuthorsByBook(String isbn) {
        return bookAuthorRepo.findByBooksIsbn(isbn).stream()
                .map(rel -> authorRepo.findById(rel.getAuthorsId()).orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    public void linkBookToAuthor(String isbn, Integer authorId) {
        bookAuthorRepo.save(new BookAuthor(isbn, authorId));
    }
}
