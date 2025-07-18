package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.Author;
import org.programacion.avanzada.bookstoreapp.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AuthorService {

    private final AuthorRepository authorRepo;
    private final BookAuthorService bookAuthorService;

    public AuthorService(AuthorRepository authorRepository, BookAuthorService bookAuthorService) {
        this.authorRepo = authorRepository;
        this.bookAuthorService = bookAuthorService;
    }

    @Transactional
    public void guardarAutor(Author author) {
        authorRepo.save(author);
    }

    public Optional<Author> buscarAutor(Integer id) {
        return authorRepo.findById(id);
    }

    @Transactional
    public void eliminarAutor(Integer id) {
        bookAuthorService.eliminarRelacionesPorAuthor(id);
        authorRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Author> listarAutores() {
        List<Author> lista = new ArrayList<>();
        authorRepo.findAll().forEach(lista::add);
        return lista;
    }
    public List<Author> buscarPorNombre(String nombre) {
        return listarAutores().stream()
                .filter(a -> a.getName() != null && a.getName().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
    }

}
