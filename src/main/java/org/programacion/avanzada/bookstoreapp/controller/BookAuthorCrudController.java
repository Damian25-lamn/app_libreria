package org.programacion.avanzada.bookstoreapp.controller;

import org.springframework.stereotype.Component;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.programacion.avanzada.bookstoreapp.model.Author;
import org.programacion.avanzada.bookstoreapp.model.Book;
import org.programacion.avanzada.bookstoreapp.service.BookAuthorService;

import java.util.List;

@Component
public class BookAuthorCrudController {

    private final BookAuthorService bookAuthorService;

    public BookAuthorCrudController(BookAuthorService bookAuthorService) {
        this.bookAuthorService = bookAuthorService;
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
            booksListView.setItems(FXCollections.observableArrayList(books));
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
            authorsListView.setItems(FXCollections.observableArrayList(authors));
        } catch (Exception e) {
            showAlert("Error", "No se pudieron listar los autores: " + e.getMessage(), Alert.AlertType.ERROR);
        }
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
