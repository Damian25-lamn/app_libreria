package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.LineItem;
import org.programacion.avanzada.bookstoreapp.repository.LineItemRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class LineItemService {

    private final LineItemRepository lineItemRepo;

    public LineItemService(LineItemRepository lineItemRepo) {
        this.lineItemRepo = lineItemRepo;
    }

    public void guardarItem(LineItem item) {
        lineItemRepo.save(item);
    }

    public List<LineItem> listarItemsPorOrderId(Integer orderId) {
        return lineItemRepo.findByOrderId(orderId);
    }

    public List<LineItem> listarItemsPorBookId(String isbn) {
        return lineItemRepo.findByBookIsbn(isbn);
    }

    public void eliminarItemsPorOrden(Integer orderId) {
        lineItemRepo.deleteByOrderId(orderId);
    }


}
