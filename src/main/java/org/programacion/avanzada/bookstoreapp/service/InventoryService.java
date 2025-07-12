package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.Inventory;
import org.programacion.avanzada.bookstoreapp.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepo;

    public InventoryService(InventoryRepository inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    public void guardarEnInventario(Inventory inventory) {
        inventoryRepo.save(inventory);
    }

    public Optional<Inventory> buscarDelInventario(String isbn) {
        return inventoryRepo.findById(isbn);
    }

    public void eliminarDelInventario(String isbn) {
        inventoryRepo.deleteById(isbn);
    }

    public List<Inventory> listarInventario() {
        List<Inventory> lista = new ArrayList<>();
        inventoryRepo.findAll().forEach(lista::add);
        return lista;
    }
}
