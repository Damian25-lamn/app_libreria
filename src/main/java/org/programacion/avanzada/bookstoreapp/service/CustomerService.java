package org.programacion.avanzada.bookstoreapp.service;

import org.programacion.avanzada.bookstoreapp.model.Customer;
import org.programacion.avanzada.bookstoreapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public Customer registrarCliente(Customer customer) {
        return repo.save(customer);
    }

    public Optional<Customer> obtenerClientePorId(Long id) {
        return repo.findById(id);
    }

    public Optional<Customer> obtenerClientePorEmail(String email) {
        return repo.findByEmail(email);
    }

    public Iterable<Customer> listarTodos() {
        return repo.findAll();
    }

    public void eliminarCliente(Long id) {
        repo.deleteById(id);
    }
}