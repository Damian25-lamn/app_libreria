package org.programacion.avanzada.bookstoreapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.programacion.avanzada.bookstoreapp.model.Customer;
import org.programacion.avanzada.bookstoreapp.service.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerCrudController {

    private final CustomerService customerService;
    private final ApplicationContext context;

    public CustomerCrudController(CustomerService customerService,
                                  ApplicationContext context) {
        this.customerService = customerService;
        this.context = context;
    }

    @FXML private TableView<Customer> tableCustomers;
    @FXML private TableColumn<Customer, Integer> idColumn;
    @FXML private TableColumn<Customer, String>  nameColumn;
    @FXML private TableColumn<Customer, String>  emailColumn;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private VBox rootVBox;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        refreshTable();

        tableCustomers.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        nameField.setText(newSel.getName());
                        emailField.setText(newSel.getEmail());
                    }
                });
        rootVBox.setPadding(new Insets(20, 20, 20, 20));
    }

    @FXML
    private void onNewCustomer() {
        clearFields();
        tableCustomers.getSelectionModel().clearSelection();
    }

    @FXML
    private void onSaveCustomer() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            showAlert("Nombre y Email son obligatorios.");
            return;
        }

        Customer c = new Customer(null, name, email, null);
        customerService.guardarCliente(c);
        refreshTable();
        clearFields();
    }

    @FXML
    private void onUpdateCustomer() {
        Customer sel = tableCustomers.getSelectionModel().getSelectedItem();
        if (sel != null) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            if (name.isEmpty() || email.isEmpty()) {
                showAlert("Nombre y Email son obligatorios.");
                return;
            }
            sel.setName(name);
            sel.setEmail(email);
            customerService.guardarCliente(sel);
            refreshTable();
        } else {
            showAlert("Selecciona un cliente para actualizar.");
        }
    }

    @FXML
    private void onDeleteCustomer() {
        Customer sel = tableCustomers.getSelectionModel().getSelectedItem();
        if (sel != null) {
            customerService.eliminarCliente(sel.getId());
            refreshTable();
            clearFields();
        } else {
            showAlert("Selecciona un cliente para eliminar.");
        }
    }

    @FXML
    private void onSearchCustomer() {
        String nameQuery = nameField.getText().trim().toLowerCase();
        String emailQuery = emailField.getText().trim().toLowerCase();

        List<Customer> filtrada = customerService.listarClientes().stream()
                .filter(c -> {
                    boolean byName = nameQuery.isEmpty() || c.getName().toLowerCase().contains(nameQuery);
                    boolean byEmail = emailQuery.isEmpty() || c.getEmail().toLowerCase().contains(emailQuery);
                    return byName && byEmail;
                })
                .collect(Collectors.toList());

        tableCustomers.getItems().setAll(filtrada);
    }

    @FXML
    private void onBackToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/programacion/avanzada/bookstoreapp/menu-view.fxml")
        );
        loader.setControllerFactory(context::getBean);
        Scene scene = new Scene(loader.load(), 800, 600);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Menú Principal");
    }

    private void refreshTable() {
        tableCustomers.getItems().setAll(customerService.listarClientes());
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
