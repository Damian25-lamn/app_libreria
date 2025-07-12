package org.programacion.avanzada.bookstoreapp.repository;

import org.programacion.avanzada.bookstoreapp.model.LineItem;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface LineItemRepository extends CrudRepository<LineItem, Integer> {
    List<LineItem> findByOrderId(Integer orderId);
    List<LineItem> findByBookIsbn(String bookIsbn);
    void deleteByOrderId(Integer orderId);
}
