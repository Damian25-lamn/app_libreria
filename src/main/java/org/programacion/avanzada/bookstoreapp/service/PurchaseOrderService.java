package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.PurchaseOrder;
import org.programacion.avanzada.bookstoreapp.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository orderRepo;

    public PurchaseOrderService(PurchaseOrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    public void guardarPedido(PurchaseOrder pedido) {
        orderRepo.save(pedido);
    }

    public Optional<PurchaseOrder> buscarPedido(Integer id) {
        return orderRepo.findById(id);
    }

    public void eliminarPedido(Integer id) {
        orderRepo.deleteById(id);
    }

    public List<PurchaseOrder> listarPedidos() {
        List<PurchaseOrder> lista = new ArrayList<>();
        orderRepo.findAll().forEach(lista::add);
        return lista;
    }

    public List<PurchaseOrder> listarPedidosPorCliente(Integer customerId) {
        return orderRepo.findByCustomerId(customerId);
    }
}
