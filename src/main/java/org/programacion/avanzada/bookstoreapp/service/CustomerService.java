package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.Customer;
import org.programacion.avanzada.bookstoreapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepo;
    private final PurchaseOrderService purchaseOrderService;

    public CustomerService(CustomerRepository customerRepo, PurchaseOrderService purchaseOrderService) {
        this.customerRepo = customerRepo;
        this.purchaseOrderService = purchaseOrderService;
    }

    @Transactional
    public void guardarCliente(Customer customer) {
        customerRepo.save(customer);
    }

    public Optional<Customer> buscarClientePorId(Integer id) {
        return customerRepo.findById(id);
    }

    public Optional<Customer> buscarClientePorEmail(String email) {
        return customerRepo.findByEmail(email);
    }

    @Transactional
    public void eliminarCliente(Integer id) {
        purchaseOrderService.eliminarPedidosPorCliente(id);
        customerRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Customer> listarClientes() {
        List<Customer> lista = new ArrayList<>();
        customerRepo.findAll().forEach(lista::add);
        return lista;
    }
}