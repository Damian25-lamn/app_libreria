package org.programacion.avanzada.bookstoreapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.programacion.avanzada.bookstoreapp.config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloApplication extends Application {

    private static AnnotationConfigApplicationContext context;

    @Override
    public void start(Stage stage) throws Exception {
        // Inicializa el contexto Spring
        context = new AnnotationConfigApplicationContext(Config.class);

        // Carga el primer FXML (bienvenida)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/programacion/avanzada/bookstoreapp/hello-view.fxml"));
        loader.setControllerFactory(context::getBean); // para que use Spring

        Scene scene = new Scene(loader.load(), 600, 400);
        stage.setTitle("Bienvenido a Librería");
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
