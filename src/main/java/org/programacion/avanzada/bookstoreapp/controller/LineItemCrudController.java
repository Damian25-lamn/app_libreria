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
import org.programacion.avanzada.bookstoreapp.model.LineItem;
import org.programacion.avanzada.bookstoreapp.service.LineItemService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class LineItemCrudController {

    private final LineItemService lineItemService;
    private final ApplicationContext context;

    public LineItemCrudController(LineItemService lineItemService,
                                  ApplicationContext context) {
        this.lineItemService = lineItemService;
        this.context = context;
    }

    @FXML
    private TableView<LineItem> tableItems;
    @FXML
    private TableColumn<LineItem, Integer> idColumn;
    @FXML
    private TableColumn<LineItem, Integer> orderIdColumn;
    @FXML
    private TableColumn<LineItem, Integer> quantityColumn;
    @FXML
    private TableColumn<LineItem, String> bookIsbnColumn;

    // Estos mismos campos servirán para insertar, editar y buscar:
    @FXML
    private TextField orderIdField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField bookIsbnField;

    @FXML
    private VBox rootVBox;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        bookIsbnColumn.setCellValueFactory(new PropertyValueFactory<>("bookIsbn"));

        refreshTable();

        tableItems.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSel, sel) -> {
                    if (sel != null) {
                        orderIdField.setText(String.valueOf(sel.getOrderId()));
                        orderIdField.setDisable(true);
                        quantityField.setText(String.valueOf(sel.getQuantity()));
                        bookIsbnField.setText(sel.getBookIsbn());
                    }
                });

        rootVBox.setPadding(new Insets(20));
    }

    @FXML
    private void onNewItem() {
        clearFields();
        tableItems.getSelectionModel().clearSelection();
        orderIdField.setDisable(false);
        refreshTable();
    }

    @FXML
    private void onSaveItem() {
        try {
            int orderId = Integer.parseInt(orderIdField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());
            String isbn = bookIsbnField.getText().trim();

            if (isbn.isEmpty()) {
                showWarning("ISBN requerido", "Ingresa el ISBN del libro.");
                return;
            }

            LineItem item = LineItem.builder()
                    .id(null)
                    .orderId(orderId)
                    .quantity(quantity)
                    .bookIsbn(isbn)
                    .build();
            lineItemService.guardarItem(item);

            onNewItem(); // limpia y recarga
        } catch (NumberFormatException e) {
            showWarning("Formato inválido", "Order ID y Cantidad deben ser enteros.");
        }
    }

    @FXML
    private void onUpdateItem() {
        LineItem sel = tableItems.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showWarning("Selección requerida", "Selecciona un artículo de la tabla.");
            return;
        }
        try {
            int quantity = Integer.parseInt(quantityField.getText().trim());
            String isbn = bookIsbnField.getText().trim();

            if (isbn.isEmpty()) {
                showWarning("ISBN requerido", "Ingresa el ISBN del libro.");
                return;
            }
            sel.setQuantity(quantity);
            sel.setBookIsbn(isbn);
            lineItemService.guardarItem(sel);

            refreshTable();
        } catch (NumberFormatException e) {
            showWarning("Formato inválido", "Cantidad debe ser un entero.");
        }
    }

    @FXML
    private void onDeleteItem() {
        LineItem sel = tableItems.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showWarning("Selección requerida", "Selecciona un artículo de la tabla.");
            return;
        }
        // Usamos el ID real para eliminar el registro
        lineItemService.eliminarItemsPorOrden(sel.getOrderId());
        onNewItem();
    }


    @FXML
    private void onSearchInventory() {
        String orderIdTxt = orderIdField.getText().trim();
        String quantityTxt = quantityField.getText().trim();
        String isbnTxt = bookIsbnField.getText().trim().toLowerCase();

        List<LineItem> filtered = lineItemService.listarItemsPorOrderId(0) // necesitarás ajustar para traer todos
                .stream()
                .filter(item -> {
                    boolean matchOrder = true;
                    boolean matchQty = true;
                    boolean matchIsbn = true;

                    if (!orderIdTxt.isEmpty()) {
                        try {
                            int q = Integer.parseInt(orderIdTxt);
                            matchOrder = item.getOrderId() == q;
                        } catch (NumberFormatException e) {
                            matchOrder = false;
                        }
                    }
                    if (!quantityTxt.isEmpty()) {
                        try {
                            int q = Integer.parseInt(quantityTxt);
                            matchQty = item.getQuantity() == q;
                        } catch (NumberFormatException e) {
                            matchQty = false;
                        }
                    }
                    if (!isbnTxt.isEmpty()) {
                        matchIsbn = item.getBookIsbn() != null
                                && item.getBookIsbn().toLowerCase().contains(isbnTxt);
                    }
                    return matchOrder && matchQty && matchIsbn;
                })
                .toList();

        tableItems.getItems().setAll(filtered);
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

    // — Helpers —
    private void refreshTable() {
        // Si tu servicio tiene un método para listar todos, úsalo directamente.
        // Aquí asumo listarItemsPorOrderId(0) devuelve todos.
        List<LineItem> all = lineItemService.listarItemsPorOrderId(0);
        tableItems.getItems().setAll(all);
    }

    private void clearFields() {
        orderIdField.clear();
        quantityField.clear();
        bookIsbnField.clear();
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
