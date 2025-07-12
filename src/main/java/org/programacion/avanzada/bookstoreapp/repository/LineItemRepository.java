package org.programacion.avanzada.bookstoreapp.repository;

import org.programacion.avanzada.bookstoreapp.model.lineitem.LineItem;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface LineItemRepository extends CrudRepository<LineItem, Void> {
    List<LineItem> findByOrderId(Long orderId);
    List<LineItem> findByBookIsbn(String bookIsbn);
}
