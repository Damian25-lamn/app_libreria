package org.programacion.avanzada.bookstoreapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private void initialize() {
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        refreshTable();

        tableBooks.getSelectionModel().selectedItemProperty().addListener((o,old,sel) -> {
            if (sel!=null) {
                isbnField.setText(sel.getIsbn());
                titleField.setText(sel.getTitle());
                priceField.setText(String.valueOf(sel.getPrice()));
            }
        });
    }

    @FXML private void onNewBook() {
        clearFields();
        tableBooks.getSelectionModel().clearSelection();
    }

    @FXML private void onSaveBook() {
        bookService.guardarLibro(new Book(
                isbnField.getText(),
                titleField.getText(),
                Double.parseDouble(priceField.getText()),
                0
        ));
        refreshTable();
        clearFields();
    }

    @FXML private void onUpdateBook() {
        Book sel = tableBooks.getSelectionModel().getSelectedItem();
        if (sel!=null) {
            sel.setTitle(titleField.getText());
            sel.setPrice(Double.parseDouble(priceField.getText()));
            bookService.guardarLibro(sel);
            refreshTable();
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
}
