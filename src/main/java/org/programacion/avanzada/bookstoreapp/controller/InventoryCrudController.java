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
import org.programacion.avanzada.bookstoreapp.model.Inventory;
import org.programacion.avanzada.bookstoreapp.service.InventoryService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InventoryCrudController {

    private final InventoryService inventoryService;
    private final ApplicationContext context;

    public InventoryCrudController(InventoryService inventoryService, ApplicationContext context) {
        this.inventoryService = inventoryService;
        this.context = context;
    }

    @FXML private TableView<Inventory> tableInventory;
    @FXML private TableColumn<Inventory, String> isbnColumn;
    @FXML private TableColumn<Inventory, Integer> soldColumn;
    @FXML private TableColumn<Inventory, Integer> suppliedColumn;

    @FXML private TextField isbnField;
    @FXML private TextField soldField;
    @FXML private TextField suppliedField;

    @FXML
    private VBox rootVBox;

    @FXML
    private void initialize() {
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("bookIsbn"));
        soldColumn.setCellValueFactory(new PropertyValueFactory<>("sold"));
        suppliedColumn.setCellValueFactory(new PropertyValueFactory<>("supplied"));

        refreshTable();

        tableInventory.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                isbnField.setText(newSel.getBookIsbn());
                isbnField.setDisable(true);
                soldField.setText(String.valueOf(newSel.getSold()));
                suppliedField.setText(String.valueOf(newSel.getSupplied()));
            }
        });
        rootVBox.setPadding(new Insets(20, 20, 20, 20));
    }

    @FXML
    private void onNewInventory() {
        clearFields();
        tableInventory.getSelectionModel().clearSelection();
        isbnField.setDisable(false);
    }

    @FXML
    private void onSaveInventory() {
        try {
            String isbn = isbnField.getText().trim();
            int sold = Integer.parseInt(soldField.getText().trim());
            int supplied = Integer.parseInt(suppliedField.getText().trim());

            if (isbn.isEmpty()) {
                showAlert("ISBN requerido", "Por favor ingrese un ISBN.");
                return;
            }

            Inventory inv = new Inventory(isbn, sold, supplied);
            inventoryService.guardarEnInventario(inv);
            refreshTable();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Error de formato", "Los valores de sold y supplied deben ser enteros.");
        }
    }

    @FXML
    private void onUpdateInventory() {
        Inventory sel = tableInventory.getSelectionModel().getSelectedItem();
        if (sel != null) {
            try {
                sel.setSold(Integer.parseInt(soldField.getText().trim()));
                sel.setSupplied(Integer.parseInt(suppliedField.getText().trim()));
                inventoryService.guardarEnInventario(sel);
                refreshTable();
            } catch (NumberFormatException e) {
                showAlert("Error de formato", "Los valores deben ser enteros.");
            }
        }
    }

    @FXML
    private void onDeleteInventory() {
        Inventory sel = tableInventory.getSelectionModel().getSelectedItem();
        if (sel != null) {
            inventoryService.eliminarDelInventario(sel.getBookIsbn());
            refreshTable();
            clearFields();
        }
    }

    @FXML
    private void onBackToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/programacion/avanzada/bookstoreapp/menu-view.fxml"));
        loader.setControllerFactory(context::getBean);
        Scene scene = new Scene(loader.load(), 800, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Menú Principal");
    }

    private void refreshTable() {
        tableInventory.getItems().setAll(inventoryService.listarInventario());
    }

    private void clearFields() {
        soldField.clear();
        suppliedField.clear();
    }

    private void showAlert(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
