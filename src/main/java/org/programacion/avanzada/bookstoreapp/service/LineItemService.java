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
    public void guardarItem(LineItem item) {
        lineItemRepo.save(item);
    }



    @Transactional(readOnly = true)
    public List<LineItem> listarItems() {
        List<LineItem> lista = new ArrayList<>();
        lineItemRepo.findAll().forEach(lista::add);
        return lista;
    }

    @Transactional
    public void eliminarItemPorId(Integer id) {
        lineItemRepo.deleteById(id);
    }

    @Transactional
    public void eliminarItemsPorIsbn(String isbn) {
        lineItemRepo.deleteByBookIsbn(isbn);
    }

    @Transactional
    public void eliminarItemsPorOrden(Integer orderId) {
        lineItemRepo.deleteByOrderId(orderId);
    }

}
