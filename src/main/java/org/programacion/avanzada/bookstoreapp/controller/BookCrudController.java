package org.programacion.avanzada.bookstoreapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.programacion.avanzada.bookstoreapp.model.Book;
import org.programacion.avanzada.bookstoreapp.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BookCrudController {

    private final BookService bookService;
    private final ApplicationContext context;

    public BookCrudController(BookService bookService, ApplicationContext context) {
        this.bookService = bookService;
        this.context = context;
    }

    @FXML private TableView<Book> tableBooks;
    @FXML private TableColumn<Book,String> isbnColumn;
    @FXML private TableColumn<Book,String> titleColumn;
    @FXML private TableColumn<Book,Double> priceColumn;

    @FXML private TextField isbnField, titleField, priceField;
    @FXML
    private VBox rootVBox;

    @FXML
    private void initialize() {
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        refreshTable();

        tableBooks.getSelectionModel().selectedItemProperty().addListener((o,old,sel) -> {
            if (sel!=null) {
                isbnField.setText(sel.getIsbn());
                isbnField.setDisable(true);
                titleField.setText(sel.getTitle());
                priceField.setText(String.valueOf(sel.getPrice()));
            }
        });
        rootVBox.setPadding(new Insets(20, 20, 20, 20));
    }

    @FXML private void onNewBook() {
        clearFields();
        tableBooks.getSelectionModel().clearSelection();
        isbnField.setDisable(false);
    }

    @FXML private void onSaveBook() {
        try {
            String isbn = isbnField.getText().trim();
            String title = titleField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());

            if (isbn.isEmpty() || title.isEmpty()) {
                // Muestra alerta
                showError("Campos vacíos", "El ISBN y el título son obligatorios.");
                return;
            }

            Book libro = new Book(isbn, title, price, null);

            if (bookService.buscarLibro(isbn).isPresent()) {
                showError("ISBN existente", "Ya existe un libro con ese ISBN.");
                return;
            }

            bookService.guardarLibro(libro);
            refreshTable();
            clearFields();
        } catch (NumberFormatException e) {
            showError("Formato inválido", "El precio debe ser un número válido.");
        }
    }

    @FXML private void onUpdateBook() {
        Book sel = tableBooks.getSelectionModel().getSelectedItem();
        if (sel != null) {
            try {
                String title = titleField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());

                if (title.isEmpty()) {
                    showError("Campo vacío", "El título no puede estar vacío.");
                    return;
                }

                sel.setTitle(title);
                sel.setPrice(price);
                bookService.guardarLibro(sel);

                refreshTable();
            } catch (NumberFormatException e) {
                showError("Formato inválido", "El precio debe ser un número válido.");
            }
        }
    }

    @FXML private void onDeleteBook() {
        Book sel = tableBooks.getSelectionModel().getSelectedItem();
        if (sel!=null) {
            bookService.eliminarLibro(sel.getIsbn());
            refreshTable();
            clearFields();
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

    private void refreshTable() {
        tableBooks.getItems().setAll(bookService.listarLibros());
    }

    private void clearFields() {
        isbnField.clear();
        titleField.clear();
        priceField.clear();
    }

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
