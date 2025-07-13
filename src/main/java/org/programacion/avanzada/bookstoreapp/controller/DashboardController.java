package org.programacion.avanzada.bookstoreapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DashboardController {

    private final ApplicationContext context;

    public DashboardController(ApplicationContext context) {
        this.context = context;
    }

    @FXML
    private void onOpenBooksCrud(ActionEvent event) throws IOException {
        openCrudView(event, "/bookstoreapp/book-crud-view.fxml", "CRUD Libros");
    }

    @FXML
    private void onOpenAuthorsCrud(ActionEvent event) throws IOException {
        openCrudView(event, "/bookstoreapp/author-crud-view.fxml", "CRUD Autores");
    }

    @FXML
    private void onOpenCustomersCrud(ActionEvent event) throws IOException {
        openCrudView(event, "/bookstoreapp/customer-crud-view.fxml", "CRUD Clientes");
    }

    // etc... agrega más para Inventory, Orders, etc.

    private void openCrudView(ActionEvent event, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(context::getBean);
        Scene scene = new Scene(loader.load(), 800, 600);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(title);
    }
}
