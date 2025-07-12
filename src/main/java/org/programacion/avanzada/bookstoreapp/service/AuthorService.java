package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.Author;
import org.programacion.avanzada.bookstoreapp.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepo;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepo = authorRepository;
    }

    public void guardarAutor(Author author) {
        authorRepo.save(author);
    }

    public Optional<Author> buscarAutor(Integer id) {
        return authorRepo.findById(id);
    }

    public void eliminarAutor(Integer id) {
        authorRepo.deleteById(id);
    }

        public List<Author> listarAutores() {
        List<Author> lista = new ArrayList<>();
        authorRepo.findAll().forEach(lista::add);
        return lista;
    }
}
