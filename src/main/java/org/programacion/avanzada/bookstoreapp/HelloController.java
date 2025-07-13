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
        loadView(event, "/org/programacion/avanzada/bookstoreapp/author-crud-view.fxml", "CRUD Autores");
    }

    @FXML
    protected void onOpenBooksCrud(ActionEvent event) throws IOException {
        loadView(event, "/org/programacion/avanzada/bookstoreapp/book-crud-view.fxml", "CRUD Libros");
    }

    @FXML
    protected void onOpenCustomersCrud(ActionEvent event) throws IOException {
        loadView(event, "/org/programacion/avanzada/bookstoreapp/customer-crud-view.fxml", "CRUD Clientes");
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
