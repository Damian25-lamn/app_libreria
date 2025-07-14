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
import java.util.ArrayList;
import java.util.List;

@Component
public class LineItemCrudController {

    private final LineItemService lineItemService;
    private final ApplicationContext context;

    public LineItemCrudController(LineItemService lineItemService, ApplicationContext context) {
        this.lineItemService = lineItemService;
        this.context = context;
    }

    @FXML private TableView<LineItem> tableItems;
    @FXML private TableColumn<LineItem, Integer> idColumn;
    @FXML private TableColumn<LineItem, Integer> orderIdColumn;
    @FXML private TableColumn<LineItem, Integer> quantityColumn;
    @FXML private TableColumn<LineItem, String> bookIsbnColumn;

    @FXML private TextField idField;
    @FXML private TextField orderIdField;
    @FXML private TextField quantityField;
    @FXML private TextField bookIsbnField;

    @FXML
    private VBox rootVBox;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        bookIsbnColumn.setCellValueFactory(new PropertyValueFactory<>("bookIsbn"));

        refreshTable();

        tableItems.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                idField.setText(String.valueOf(newSel.getId()));
                orderIdField.setText(String.valueOf(newSel.getOrderId()));
                quantityField.setText(String.valueOf(newSel.getQuantity()));
                bookIsbnField.setText(newSel.getBookIsbn());
            }
        });
        rootVBox.setPadding(new Insets(20, 20, 20, 20));
    }

    @FXML
    private void onNewItem() {
        clearFields();
        tableItems.getSelectionModel().clearSelection();
    }

    @FXML
    private void onSaveItem() {
        LineItem item = LineItem.builder()
                .id(Integer.parseInt(idField.getText()))
                .orderId(Integer.parseInt(orderIdField.getText()))
                .quantity(Integer.parseInt(quantityField.getText()))
                .bookIsbn(bookIsbnField.getText())
                .build();

        lineItemService.guardarItem(item);
        refreshTable();
        clearFields();
    }

    @FXML
    private void onUpdateItem() {
        LineItem sel = tableItems.getSelectionModel().getSelectedItem();
        if (sel != null) {
            sel.setOrderId(Integer.parseInt(orderIdField.getText()));
            sel.setQuantity(Integer.parseInt(quantityField.getText()));
            sel.setBookIsbn(bookIsbnField.getText());

            lineItemService.guardarItem(sel);
            refreshTable();
        }
    }

    @FXML
    private void onDeleteItem() {
        LineItem sel = tableItems.getSelectionModel().getSelectedItem();
        if (sel != null) {
            // eliminar por ID directamente
            lineItemService.eliminarItemsPorOrden(sel.getOrderId());
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
        List<LineItem> allItems = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            allItems.addAll(lineItemService.listarItemsPorOrderId(i));
        }
        tableItems.getItems().setAll(allItems);
    }

    private void clearFields() {
        idField.clear();
        orderIdField.clear();
        quantityField.clear();
        bookIsbnField.clear();
    }
}