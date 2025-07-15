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
import java.util.List;

@Component
public class InventoryCrudController {

    private final InventoryService inventoryService;
    private final ApplicationContext context;

    public InventoryCrudController(InventoryService inventoryService,
                                   ApplicationContext context) {
        this.inventoryService = inventoryService;
        this.context = context;
    }

    @FXML
    private TableView<Inventory> tableInventory;
    @FXML
    private TableColumn<Inventory, String> isbnColumn;
    @FXML
    private TableColumn<Inventory, Integer> soldColumn;
    @FXML
    private TableColumn<Inventory, Integer> suppliedColumn;

    @FXML
    private TextField isbnField;
    @FXML
    private TextField soldField;
    @FXML
    private TextField suppliedField;

    @FXML
    private VBox rootVBox;

    @FXML
    private void initialize() {
        // 1) Configuro las columnas
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("bookIsbn"));
        soldColumn.setCellValueFactory(new PropertyValueFactory<>("sold"));
        suppliedColumn.setCellValueFactory(new PropertyValueFactory<>("supplied"));

        // 2) Cargo datos iniciales
        refreshTable();

        // 3) Al seleccionar fila, cargo en el formulario
        tableInventory.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, sel) -> {
            if (sel != null) {
                isbnField.setText(sel.getBookIsbn());
                isbnField.setDisable(true);   // no cambiar ISBN al actualizar
                soldField.setText(String.valueOf(sel.getSold()));
                suppliedField.setText(String.valueOf(sel.getSupplied()));
            }
        });

        // 4) Padding
        rootVBox.setPadding(new Insets(20));
    }

    @FXML
    private void onNewInventory() {
        clearFields();
        isbnField.setDisable(false);
        tableInventory.getSelectionModel().clearSelection();
    }

    /**
     * Guarda nuevo registro
     */
    @FXML
    private void onSaveInventory() {
        String isbnTxt = isbnField.getText().trim();
        String soldTxt = soldField.getText().trim();
        String supTxt = suppliedField.getText().trim();

        if (isbnTxt.isEmpty() || soldTxt.isEmpty() || supTxt.isEmpty()) {
            showAlert("Campos vacíos", "Debes completar ISBN, Vendidos y Suministrados.");
            return;
        }

        try {
            int sold = Integer.parseInt(soldTxt);
            int supplied = Integer.parseInt(supTxt);
            inventoryService.guardarEnInventario(new Inventory(isbnTxt, sold, supplied));
            refreshTable();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Formato inválido", "Vendidos y Suministrados deben ser números enteros.");
        }
    }

    @FXML
    private void onUpdateInventory() {
        Inventory sel = tableInventory.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("Selecciona un registro", "Primero selecciona una fila.");
            return;
        }
        try {
            int sold = Integer.parseInt(soldField.getText().trim());
            int supplied = Integer.parseInt(suppliedField.getText().trim());
            sel.setSold(sold);
            sel.setSupplied(supplied);
            inventoryService.guardarEnInventario(sel);
            refreshTable();
        } catch (NumberFormatException e) {
            showAlert("Formato inválido", "Vendidos y Suministrados deben ser números enteros.");
        }
    }

    @FXML
    private void onDeleteInventory() {
        Inventory sel = tableInventory.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("Selecciona un registro", "Primero selecciona una fila.");
            return;
        }
        inventoryService.eliminarDelInventario(sel.getBookIsbn());
        refreshTable();
        clearFields();
    }


    @FXML
    private void onSearchInventory() {
        String isbnText = isbnField.getText().trim().toLowerCase();
        String soldText = soldField.getText().trim();
        String supText = suppliedField.getText().trim();

        List<Inventory> filtered = inventoryService.listarInventario().stream()
                .filter(inv -> {
                    boolean matchesIsbn = true;
                    boolean matchesSold = true;
                    boolean matchesSupplied = true;

                    // ISBN parcial
                    if (!isbnText.isEmpty()) {
                        String invIsbn = inv.getBookIsbn() == null ? "" : inv.getBookIsbn().toLowerCase();
                        matchesIsbn = invIsbn.contains(isbnText);
                    }
                    // Sold exacto
                    if (!soldText.isEmpty()) {
                        try {
                            int soldQuery = Integer.parseInt(soldText);
                            matchesSold = inv.getSold() == soldQuery;
                        } catch (NumberFormatException e) {
                            matchesSold = false;
                        }
                    }
                    // Supplied exacto
                    if (!supText.isEmpty()) {
                        try {
                            int supQuery = Integer.parseInt(supText);
                            matchesSupplied = inv.getSupplied() == supQuery;
                        } catch (NumberFormatException e) {
                            matchesSupplied = false;
                        }
                    }

                    // Solo incluyo los que pasan TODOS los filtros no-vacíos
                    return matchesIsbn && matchesSold && matchesSupplied;
                })
                .toList();

        tableInventory.getItems().setAll(filtered);
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
        tableInventory.getItems().setAll(inventoryService.listarInventario());
    }

    private void clearFields() {
        isbnField.clear();
        soldField.clear();
        suppliedField.clear();
        isbnField.setDisable(false);
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
