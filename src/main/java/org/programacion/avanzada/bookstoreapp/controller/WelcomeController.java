package org.programacion.avanzada.bookstoreapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.programacion.avanzada.bookstoreapp.HelloApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class WelcomeController {
    private static ConfigurableApplicationContext context;

    @FXML
    private Button accederButton;

    @FXML
    public void initialize() {
        accederButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/programacion/avanzada/bookstoreapp/hello-view.fxml"));

                // Usa el contexto de Spring para inyectar el controlador
                loader.setControllerFactory(HelloApplication.getContext()::getBean);

                Parent root = loader.load();
                Scene scene = new Scene(root);

                // Obtener la ventana actual y reemplazar la escena
                Stage stage = (Stage) accederButton.getScene().getWindow();
                stage.setTitle("Hello View");
                stage.setScene(scene);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
