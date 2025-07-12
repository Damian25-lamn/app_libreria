package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.PurchaseOrder;
import org.programacion.avanzada.bookstoreapp.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository repo;

    public PurchaseOrderService(PurchaseOrderRepository repo) {
        this.repo = repo;
    }

    public List<PurchaseOrder> obtenerPedidosPorCliente(Long customerId) {
        return repo.findByCustomerId(customerId);
    }

    public Optional<PurchaseOrder> obtenerPedidoPorId(Long id) {
        return repo.findById(id);
    }

    public PurchaseOrder crearPedido(PurchaseOrder pedido) {
        return repo.save(pedido);
    }

    public void eliminarPedido(Long id) {
        repo.deleteById(id);
    }

    public List<PurchaseOrder> listarTodos() {
        return (List<PurchaseOrder>) repo.findAll();
    }
}
