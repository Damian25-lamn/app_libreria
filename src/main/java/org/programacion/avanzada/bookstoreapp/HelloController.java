package org.programacion.avanzada.bookstoreapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    public void onHelloButtonClick() {
        welcomeText.setText("¡Hola! Has presionado el botón.");
    }
}