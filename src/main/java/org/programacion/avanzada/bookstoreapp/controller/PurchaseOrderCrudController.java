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
import org.programacion.avanzada.bookstoreapp.model.PurchaseOrder;
import org.programacion.avanzada.bookstoreapp.service.PurchaseOrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class PurchaseOrderCrudController {

    private final PurchaseOrderService orderService;
    private final ApplicationContext context;

    public PurchaseOrderCrudController(PurchaseOrderService orderService, ApplicationContext context) {
        this.orderService = orderService;
        this.context = context;
    }

    @FXML private TableView<PurchaseOrder> tableOrders;
    @FXML private TableColumn<PurchaseOrder, Integer> idColumn;
    @FXML private TableColumn<PurchaseOrder, Integer> customerIdColumn;
    @FXML private TableColumn<PurchaseOrder, Double> totalColumn;
    @FXML private TableColumn<PurchaseOrder, String> statusColumn;
    @FXML private TableColumn<PurchaseOrder, LocalDateTime> placedOnColumn;
    @FXML private TableColumn<PurchaseOrder, LocalDateTime> deliveredOnColumn;

    @FXML private TextField customerIdField;
    @FXML private TextField totalField;
    @FXML private TextField statusField;
    @FXML private DatePicker placedOnPicker;
    @FXML private DatePicker deliveredOnPicker;

    @FXML
    private VBox rootVBox;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        placedOnColumn.setCellValueFactory(new PropertyValueFactory<>("placedOn"));
        deliveredOnColumn.setCellValueFactory(new PropertyValueFactory<>("deliveredOn"));

        refreshTable();

        tableOrders.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                customerIdField.setText(String.valueOf(newSel.getCustomerId()));
                totalField.setText(String.valueOf(newSel.getTotal()));
                statusField.setText(String.valueOf(newSel.getStatus()));
                if (newSel.getPlacedOn() != null) {
                    placedOnPicker.setValue(newSel.getPlacedOn().toLocalDate());
                }
                if (newSel.getDeliveredOn() != null) {
                    deliveredOnPicker.setValue(newSel.getDeliveredOn().toLocalDate());
                }
            }
        });
        rootVBox.setPadding(new Insets(20, 20, 20, 20));
    }

    @FXML
    private void onNewOrder() {
        clearFields();
        tableOrders.getSelectionModel().clearSelection();
    }

    @FXML
    private void onSaveOrder() {
        PurchaseOrder order = PurchaseOrder.builder()
                .id(null)
                .customerId(Integer.parseInt(customerIdField.getText()))
                .total(Double.parseDouble(totalField.getText()))
                .status(Integer.parseInt(statusField.getText()))
                .placedOn(placedOnPicker.getValue() != null ? placedOnPicker.getValue().atStartOfDay() : null)
                .deliveredOn(deliveredOnPicker.getValue() != null ? deliveredOnPicker.getValue().atStartOfDay() : null)
                .build();

        orderService.guardarPedido(order);
        refreshTable();
        clearFields();
    }

    @FXML
    private void onUpdateOrder() {
        PurchaseOrder sel = tableOrders.getSelectionModel().getSelectedItem();
        if (sel != null) {
            sel.setCustomerId(Integer.parseInt(customerIdField.getText()));
            sel.setTotal(Double.parseDouble(totalField.getText()));
            sel.setStatus(Integer.parseInt(statusField.getText()));
            sel.setPlacedOn(placedOnPicker.getValue() != null ? placedOnPicker.getValue().atStartOfDay() : null);
            sel.setDeliveredOn(deliveredOnPicker.getValue() != null ? deliveredOnPicker.getValue().atStartOfDay() : null);

            orderService.guardarPedido(sel);
            refreshTable();
        }
    }

    @FXML
    private void onDeleteOrder() {
        PurchaseOrder sel = tableOrders.getSelectionModel().getSelectedItem();
        if (sel != null) {
            orderService.eliminarPedido(sel.getId());
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
        tableOrders.getItems().setAll(orderService.listarPedidos());
    }

    private void clearFields() {
        customerIdField.clear();
        totalField.clear();
        statusField.clear();
        placedOnPicker.setValue(null);
        deliveredOnPicker.setValue(null);
    }
}