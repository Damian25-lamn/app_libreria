package org.programacion.avanzada.bookstoreapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.programacion.avanzada.bookstoreapp.model.Customer;
import org.programacion.avanzada.bookstoreapp.service.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;

    @FXML
    private VBox rootVBox;

    @FXML
    private void initialize() {
        // 1) Configurar columnas
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // 2) Cargar datos
        refreshTable();

        // 3) Cuando seleccionas una fila, la cargas en el formulario
        tableCustomers.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        idField.setText(String.valueOf(newSel.getId()));
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
        Customer c = new Customer(
                Integer.parseInt(idField.getText()),
                nameField.getText(),
                emailField.getText(),
                null
        );
        customerService.guardarCliente(c);
        refreshTable();
        clearFields();
    }

    @FXML
    private void onUpdateCustomer() {
        Customer sel = tableCustomers.getSelectionModel().getSelectedItem();
        if (sel != null) {
            sel.setName(nameField.getText());
            sel.setEmail(emailField.getText());
            customerService.guardarCliente(sel);
            refreshTable();
        }
    }

    @FXML
    private void onDeleteCustomer() {
        Customer sel = tableCustomers.getSelectionModel().getSelectedItem();
        if (sel != null) {
            customerService.eliminarCliente(sel.getId());
            refreshTable();
            clearFields();
        }
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
        idField.clear();
        nameField.clear();
        emailField.clear();
    }
}
