package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.LineItem;
import org.programacion.avanzada.bookstoreapp.repository.LineItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class LineItemService {

    private final LineItemRepository lineItemRepo;

    public LineItemService(LineItemRepository lineItemRepo) {
        this.lineItemRepo = lineItemRepo;
    }

    @Transactional
    public LineItem guardarItem(LineItem item) {
        return lineItemRepo.save(item);
    }

    @Transactional(readOnly = true)
    public List<LineItem> listarItemsPorOrderId(Integer orderId) {
        return lineItemRepo.findByOrderId(orderId);
    }

    @Transactional(readOnly = true)
    public List<LineItem> listarItemsPorBookId(String isbn) {
        return lineItemRepo.findByBookIsbn(isbn);
    }

    @Transactional
    public void eliminarItemsPorOrden(Integer orderId) {
        lineItemRepo.deleteByOrderId(orderId);
    }


}
