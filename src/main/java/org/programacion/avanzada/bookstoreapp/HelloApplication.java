package org.programacion.avanzada.bookstoreapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

        // Carga la escena desde el FXML
        Parent root = loader.load();

        // Ajuste del tamaño de la escena (más vertical, según tu diseño)
        Scene scene = new Scene(root, 500, 700); // Puedes ajustar si necesitas más espacio

        stage.setTitle("Bienvenido a Librería");
        stage.setScene(scene);

        // ❗ Tamaño mínimo para evitar que se recorte
        stage.setMinWidth(400);
        stage.setMinHeight(600);
        // ❗ Centrar la ventana en la pantalla
        stage.centerOnScreen();

        // ❗ Evita redimensionamiento si lo deseas
        //stage.setResizable(false); // Descomenta si quieres bloquear redimensionamiento

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
