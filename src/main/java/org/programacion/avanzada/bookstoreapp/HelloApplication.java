package org.programacion.avanzada.bookstoreapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.programacion.avanzada.bookstoreapp.config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.URL;

public class HelloApplication extends Application {
    private static AnnotationConfigApplicationContext context;

    @Override
    public void start(Stage stage) throws Exception {
        context = new AnnotationConfigApplicationContext(Config.class);

        URL fxmlUrl = HelloApplication.class.getResource("/org/programacion/avanzada/bookstoreapp/hello-view.fxml");
        System.out.println("FXML URL: " + fxmlUrl); // Esto debe imprimir algo distinto a null

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
        fxmlLoader.setControllerFactory(context::getBean);

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Librería!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        if (context != null) {
            context.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}