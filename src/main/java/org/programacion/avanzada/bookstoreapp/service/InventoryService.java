package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.Inventory;
import org.programacion.avanzada.bookstoreapp.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository repo;

    public InventoryService(InventoryRepository repo) {
        this.repo = repo;
    }

    public Optional<Inventory> obtenerInventarioPorIsbn(String isbn) {
        return repo.findByBookIsbn(isbn);
    }

    public Inventory actualizarInventario(Inventory inventario) {
        return repo.save(inventario);
    }

    public void eliminarInventario(String isbn) {
        repo.deleteById(isbn);
    }
}
