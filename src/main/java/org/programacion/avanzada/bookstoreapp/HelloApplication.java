package org.programacion.avanzada.bookstoreapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.programacion.avanzada.bookstoreapp.config.Config;
import org.programacion.avanzada.bookstoreapp.model.Author;
import org.programacion.avanzada.bookstoreapp.model.Book;
import org.programacion.avanzada.bookstoreapp.service.AuthorService;
import org.programacion.avanzada.bookstoreapp.service.BookAuthorService;
import org.programacion.avanzada.bookstoreapp.service.BookService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.URL;

public class HelloApplication extends Application {

    private static AnnotationConfigApplicationContext context;

    @Override
    public void start(Stage stage) throws Exception {
        context = new AnnotationConfigApplicationContext(Config.class);

        // Obtener servicios desde el contexto de Spring
        AuthorService authorService = context.getBean(AuthorService.class);
        BookService bookService = context.getBean(BookService.class);
        BookAuthorService bookAuthorService = context.getBean(BookAuthorService.class);

        // Crear datos de ejemplo
        Author autor = authorService.guardarAutor(new Author(191, "Isabel Allende", null));
        Book libro = Book.builder()
                .isbn("111-222-312")
                .title("La casa de los espíritus")
                .price(25.00)
                .version(null)
                .build();

        bookService.guardarLibro(libro); // necesitas crear un metodo guardar en BookService si no está

        bookAuthorService.guardarRelacionLibroAutor(libro.getIsbn(), autor.getId());

        // Mostrar autores del libro
        System.out.println("Autores del libro '" + libro.getTitle() + "':");
        bookAuthorService.listarAutoresDeLibro(libro.getIsbn())
                .forEach(System.out::println);

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
