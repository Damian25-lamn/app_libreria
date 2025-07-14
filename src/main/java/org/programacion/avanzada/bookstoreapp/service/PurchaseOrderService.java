package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.PurchaseOrder;
import org.programacion.avanzada.bookstoreapp.repository.CustomerRepository;
import org.programacion.avanzada.bookstoreapp.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository orderRepo;
    private final LineItemService lineItemService;

    public PurchaseOrderService(PurchaseOrderRepository orderRepo, LineItemService lineItemService) {
        this.orderRepo = orderRepo;
        this.lineItemService = lineItemService;
    }

    public PurchaseOrder guardarPedido(PurchaseOrder pedido) {
        return orderRepo.save(pedido);
    }

    public Optional<PurchaseOrder> buscarPedido(Integer id) {
        return orderRepo.findById(id);
    }

    public void eliminarPedido(Integer id) {
        lineItemService.eliminarItemsPorOrden(id);
        orderRepo.deleteById(id);
    }

    public void eliminarPedidosPorCliente(Integer customerId) {
        orderRepo.deleteByCustomerId(customerId);
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
