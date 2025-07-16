package org.programacion.avanzada.bookstoreapp.repository;

import org.programacion.avanzada.bookstoreapp.model.Inventory;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, String> {

    @Query("INSERT INTO inventory (isbn, sold, supplied) VALUES (:isbn, :sold, :supplied)")
    @Modifying
    void insert(@Param("isbn") String isbn, @Param("sold") int sold, @Param("supplied") int supplied);
}

