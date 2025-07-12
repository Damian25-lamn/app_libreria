package org.programacion.avanzada.bookstoreapp.repository;

import org.programacion.avanzada.bookstoreapp.model.Inventory;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Inventory, String> {

}

