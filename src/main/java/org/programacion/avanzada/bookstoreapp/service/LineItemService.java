package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.lineitem.LineItem;
import org.programacion.avanzada.bookstoreapp.model.lineitem.LineItemBook;
import org.programacion.avanzada.bookstoreapp.repository.BookRepository;
import org.programacion.avanzada.bookstoreapp.repository.LineItemRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LineItemService {

    private final LineItemRepository lineItemRepo;
    private final BookRepository bookRepo;

    public LineItemService(LineItemRepository lineItemRepo, BookRepository bookRepo) {
        this.lineItemRepo = lineItemRepo;
        this.bookRepo = bookRepo;
    }

    public List<LineItem> getItemsByOrderId(Long orderId) {
        return lineItemRepo.findByOrderId(orderId);
    }

    public List<LineItemBook> getItemsWithBooksByOrderId(Long orderId) {
        List<LineItem> items = lineItemRepo.findByOrderId(orderId);
        return items.stream()
                .map(item -> new LineItemBook(item,
                        bookRepo.findById(item.getBookIsbn()).orElse(null)))
                .collect(Collectors.toList());
    }

    public LineItem agregarItem(LineItem item) {
        return lineItemRepo.save(item);
    }

    public void eliminarItemsPorOrden(Long orderId) {
        List<LineItem> items = lineItemRepo.findByOrderId(orderId);
        items.forEach(lineItemRepo::delete);
    }
}
