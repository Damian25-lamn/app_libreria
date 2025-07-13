package org.programacion.avanzada.bookstoreapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.programacion.avanzada.bookstoreapp.model.Customer;
import org.programacion.avanzada.bookstoreapp.service.CustomerService;
import org.springframework.stereotype.Component;

@Component
public class CustomerCrudController {

    private final CustomerService customerService;

    public CustomerCrudController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @FXML private TableView<Customer> tableCustomers;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;

    @FXML
    private void initialize() {
        refreshTable();
    }

    @FXML
    private void onSaveCustomer() {
        Customer customer = Customer.builder()
                .id(Integer.parseInt(idField.getText()))
                .name(nameField.getText())
                .email(emailField.getText())
                .build();
        customerService.guardarCliente(customer);
        refreshTable();
        clearFields();
    }

    @FXML
    private void onDeleteCustomer() {
        Customer selected = tableCustomers.getSelectionModel().getSelectedItem();
        if (selected != null) {
            customerService.eliminarCliente(selected.getId());
            refreshTable();
        }
    }

    private void refreshTable() {
        tableCustomers.getItems().setAll(customerService.listarClientes());
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        emailField.clear();
    }
}
