package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.Author;
import org.programacion.avanzada.bookstoreapp.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Iterable<Author> listar() {
        return authorRepository.findAll();
    }

    public Author guardar(Author author) {
        return authorRepository.save(author);
    }

    public Optional<Author> buscarPorId(Integer id) {
        return authorRepository.findById(id);
    }

    public void eliminar(Integer id) {
        authorRepository.deleteById(id);
    }
}
