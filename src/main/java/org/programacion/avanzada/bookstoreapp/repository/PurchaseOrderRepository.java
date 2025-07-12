package org.programacion.avanzada.bookstoreapp.repository;

import org.programacion.avanzada.bookstoreapp.model.PurchaseOrder;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, Long> {
    List<PurchaseOrder> findByCustomerId(Long customerId);
}
