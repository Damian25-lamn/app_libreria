package org.programacion.avanzada.bookstoreapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.programacion.avanzada.bookstoreapp.model.Author;
import org.programacion.avanzada.bookstoreapp.model.Book;
import org.programacion.avanzada.bookstoreapp.service.BookAuthorService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class BookAuthorCrudController {

    private final BookAuthorService bookAuthorService;
    private final ApplicationContext context;

    public BookAuthorCrudController(BookAuthorService bookAuthorService, ApplicationContext context) {
        this.bookAuthorService = bookAuthorService;
        this.context = context;
    }

    @FXML private TextField isbnField;
    @FXML private TextField authorIdField;
    @FXML private ListView<Book> booksListView;
    @FXML private ListView<Author> authorsListView;

    @FXML
    private void initialize() {
        booksListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getIsbn() + " - " + item.getTitle());
                }
            }
        });

        authorsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Author item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getId() + " - " + item.getName());
                }
            }
        });
    }

    @FXML
    private void onAddRelation() {
        try {
            String isbn = isbnField.getText();
            Integer authorId = Integer.parseInt(authorIdField.getText());

            bookAuthorService.guardarRelacionLibroAutor(isbn, authorId);

            showAlert("Éxito", "Relación añadida correctamente", Alert.AlertType.INFORMATION);
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Error", "El ID del autor debe ser un número", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "No se pudo añadir la relación: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onListBooksByAuthor() {
        try {
            Integer authorId = Integer.parseInt(authorIdField.getText());
            List<Book> books = bookAuthorService.listarLibrosDeAutor(authorId);
            booksListView.getItems().setAll(books);
        } catch (NumberFormatException e) {
            showAlert("Error", "El ID del autor debe ser un número", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "No se pudieron listar los libros: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onListAuthorsByBook() {
        try {
            String isbn = isbnField.getText();
            List<Author> authors = bookAuthorService.listarAutoresDeLibro(isbn);
            authorsListView.getItems().setAll(authors);
        } catch (Exception e) {
            showAlert("Error", "No se pudieron listar los autores: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onBackToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/programacion/avanzada/bookstoreapp/menu-view.fxml"));
        loader.setControllerFactory(context::getBean);
        Scene scene = new Scene(loader.load(), 800, 600);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Menú Principal");
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        isbnField.clear();
        authorIdField.clear();
    }
}
