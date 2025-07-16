package org.programacion.avanzada.bookstoreapp;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    private VBox rootVBox;

    @FXML
    private Button acceder;

    @FXML
    private ImageView logoImageView;

    @FXML
    public void initialize() {
        playEntryAnimation();
        playLogoAnimation();

        acceder.setOnMouseEntered(e -> playButtonHoverAnimation());
        acceder.setOnMouseExited(e -> resetButtonScale());

        cargarLogo();
    }

    private void cargarLogo() {
        try {
            Image logo = new Image(getClass().getResource("/org/programacion/avanzada/bookstoreapp/images/logo-u.png").toExternalForm());
            logoImageView.setImage(logo);
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen del logo: " + e.getMessage());
        }
    }

    @FXML
    public void onAccederClick() {
        System.out.println("Botón Acceder pulsado");
    }

    private void playEntryAnimation() {
        // Fade in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), rootVBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        // Slide in desde arriba
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(800), rootVBox);
        slideIn.setFromY(-50);
        slideIn.setToY(0);

        fadeIn.play();
        slideIn.play();
    }

    private void playButtonHoverAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), acceder);
        st.setToX(1.08);
        st.setToY(1.08);
        st.play();
    }

    private void resetButtonScale() {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), acceder);
        st.setToX(1.0);
        st.setToY(1.0);
        st.play();
    }

    private void playLogoAnimation() {
        FadeTransition fade = new FadeTransition(Duration.millis(1000), logoImageView);
        fade.setFromValue(0);
        fade.setToValue(0.8);

        ScaleTransition scale = new ScaleTransition(Duration.millis(1000), logoImageView);
        scale.setFromX(0.5);
        scale.setFromY(0.5);
        scale.setToX(1.0);
        scale.setToY(1.0);

        fade.play();
        scale.play();
    }

    @FXML
    private BorderPane mainPane;

    @FXML
    protected void onAccederClick(ActionEvent event) throws IOException {
        loadView(event, "/org/programacion/avanzada/bookstoreapp/menu-view.fxml", "Menú Principal");
    }

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


    private void loadContentInCenter(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(context::getBean);
            Node content = loader.load();
            mainPane.setCenter(content);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error al cargar la vista", fxmlPath + "\n\n" + e.getMessage());
        }
    }

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