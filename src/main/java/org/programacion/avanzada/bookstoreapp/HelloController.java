package org.programacion.avanzada.bookstoreapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HelloController {

    private final ApplicationContext context;

    public HelloController(ApplicationContext context) {
        this.context = context;
    }

    @FXML
    protected void onAccederClick(ActionEvent event) throws IOException {
        loadView(event, "/org/programacion/avanzada/bookstoreapp/menu-view.fxml", "Menú Principal");
    }

    @FXML
    protected void onOpenAuthorsCrud(ActionEvent event) throws IOException {
        loadView(event, "/org/programacion/avanzada/bookstoreapp/author-crud-view.fxml", "Gestión de Autores");
    }

    @FXML
    protected void onOpenBooksCrud(ActionEvent event) throws IOException {
        loadView(event, "/org/programacion/avanzada/bookstoreapp/book-crud-view.fxml", "Gestión de Libros");
    }

    @FXML
    protected void onOpenBooksAuthorsCrud(ActionEvent event) throws IOException {
        loadView(event, "/org/programacion/avanzada/bookstoreapp/bookAuthor-crud-view.fxml", "Relaciones de Libros y Autores");
    }

    @FXML
    protected void onOpenCustomersCrud(ActionEvent event) throws IOException {
        loadView(event, "/org/programacion/avanzada/bookstoreapp/customer-crud-view.fxml", "Gestión de Clientes");
    }

    @FXML
    protected void onOpenPurchaseOrdersCrud(ActionEvent event) throws IOException {
        loadView(event, "/org/programacion/avanzada/bookstoreapp/purchaseOrder-crud-view.fxml", "Gestión de Orden de Compra");
    }

    @FXML
    protected void onOpenLineItemsCrud(ActionEvent event) throws IOException {
        loadView(event, "/org/programacion/avanzada/bookstoreapp/lineItem-crud-view.fxml", "Gestión de Artículos en Línea");
    }

    @FXML
    protected void onOpenInventoryCrud(ActionEvent event) throws IOException {
        loadView(event, "/org/programacion/avanzada/bookstoreapp/inventory-crud-view.fxml", "Gestión de Inventario");
    }

    private void loadView(ActionEvent event, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(context::getBean);
        Scene scene = new Scene(loader.load(), 800, 600);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                .getScene()
                .getWindow();
        stage.setScene(scene);
        stage.setTitle(title);
    }
}
