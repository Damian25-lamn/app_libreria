package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.PurchaseOrder;
import org.programacion.avanzada.bookstoreapp.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void guardarPedido(PurchaseOrder pedido) {
        orderRepo.save(pedido);
    }

    public Optional<PurchaseOrder> buscarPedido(Integer id) {
        return orderRepo.findById(id);
    }

    @Transactional
    public void eliminarPedido(Integer id) {
        lineItemService.eliminarItemsPorOrden(id);
        orderRepo.deleteById(id);
    }

    @Transactional
    public void eliminarPedidosPorCliente(Integer customerId) {
        List<PurchaseOrder> pedidos = orderRepo.findByCustomerId(customerId);
        for (PurchaseOrder pedido : pedidos) {
            lineItemService.eliminarItemsPorOrden(pedido.getId());
        }
        orderRepo.deleteByCustomerId(customerId);
    }

    @Transactional(readOnly = true)
    public List<PurchaseOrder> listarPedidos() {
        List<PurchaseOrder> lista = new ArrayList<>();
        orderRepo.findAll().forEach(lista::add);
        return lista;
    }

    @Transactional(readOnly = true)
    public List<PurchaseOrder> listarPedidosPorCliente(Integer customerId) {
        return orderRepo.findByCustomerId(customerId);
    }
}
