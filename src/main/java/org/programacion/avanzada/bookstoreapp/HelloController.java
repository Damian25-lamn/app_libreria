package org.programacion.avanzada.bookstoreapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
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
    private BorderPane mainPane; // Se inyecta desde el FXML con fx:id="mainPane"

    // Acceso desde el login u otra vista
    @FXML
    protected void onAccederClick(ActionEvent event) throws IOException {
        loadView(event, "/org/programacion/avanzada/bookstoreapp/menu-view.fxml", "Menú Principal");
    }

    // Métodos de navegación lateral
    @FXML
    protected void onOpenAuthorsCrud(ActionEvent event) {
        loadContentInCenter("/org/programacion/avanzada/bookstoreapp/author-crud-view.fxml");
    }

    @FXML
    protected void onOpenBooksCrud(ActionEvent event) {
        loadContentInCenter("/org/programacion/avanzada/bookstoreapp/book-crud-view.fxml");
    }

    @FXML
    protected void onOpenBooksAuthorsCrud(ActionEvent event) {
        loadContentInCenter("/org/programacion/avanzada/bookstoreapp/bookAuthor-crud-view.fxml");
    }

    @FXML
    protected void onOpenCustomersCrud(ActionEvent event) {
        loadContentInCenter("/org/programacion/avanzada/bookstoreapp/customer-crud-view.fxml");
    }

    @FXML
    protected void onOpenPurchaseOrdersCrud(ActionEvent event) {
        loadContentInCenter("/org/programacion/avanzada/bookstoreapp/purchaseOrder-crud-view.fxml");
    }

    @FXML
    protected void onOpenLineItemsCrud(ActionEvent event) {
        loadContentInCenter("/org/programacion/avanzada/bookstoreapp/lineItem-crud-view.fxml");
    }

    @FXML
    protected void onOpenInventoryCrud(ActionEvent event) {
        loadContentInCenter("/org/programacion/avanzada/bookstoreapp/inventory-crud-view.fxml");
    }

    @FXML
    private void onLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/programacion/avanzada/bookstoreapp/hello-view.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/org/programacion/avanzada/bookstoreapp/style.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            showError("Error al cerrar sesión", e.getMessage());
        }
    }

    // Método reutilizable para cambiar el contenido del centro
    private void loadContentInCenter(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(context::getBean);
            Node content = loader.load();
            mainPane.setCenter(content);
        } catch (IOException e) {
            e.printStackTrace(); // 🔍 Muestra el error real en consola
            showError("Error al cargar la vista", fxmlPath + "\n\n" + e.getMessage());
        }
    }

    // Para cargar vistas completas como desde el login
    private void loadView(ActionEvent event, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(context::getBean);
        Scene scene = new Scene(loader.load(), 800, 600);
        scene.getStylesheets().add(getClass().getResource("/org/programacion/avanzada/bookstoreapp/style.css").toExternalForm());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}