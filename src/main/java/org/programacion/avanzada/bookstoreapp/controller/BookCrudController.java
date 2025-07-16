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
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookCrudController {

    private final BookService    bookService;
    private final ApplicationContext context;

    public BookCrudController(BookService bookService,
                              ApplicationContext context) {
        this.bookService = bookService;
        this.context     = context;
    }

    @FXML private TableView<Book>    tableBooks;
    @FXML private TableColumn<Book, String> isbnColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, Double> priceColumn;

    @FXML private TextField isbnField;
    @FXML private TextField titleField;
    @FXML private TextField priceField;

    @FXML private VBox rootVBox;

    @FXML
    private void initialize() {

        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        refreshTable();

        tableBooks.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSel, sel) -> {
                    if (sel != null) {
                        isbnField.setText(sel.getIsbn());
                        isbnField.setDisable(true);
                        titleField.setText(sel.getTitle());
                        priceField.setText(String.valueOf(sel.getPrice()));
                    }
                });

        // Padding
        rootVBox.setPadding(new Insets(20));
    }

    @FXML
    private void onNewBook() {
        clearFields();
        isbnField.setDisable(false);
        tableBooks.getSelectionModel().clearSelection();
    }

    @FXML
    private void onSaveBook() {
        String isbnTxt  = isbnField.getText().trim();
        String titleTxt = titleField.getText().trim();
        String priceTxt = priceField.getText().trim();

        if (isbnTxt.isEmpty() || titleTxt.isEmpty() || priceTxt.isEmpty()) {
            showError("Campos vacíos", "ISBN, Título y Precio son obligatorios.");
            return;
        }
        double price;
        try {
            price = Double.parseDouble(priceTxt);
        } catch (NumberFormatException e) {
            showError("Precio inválido", "Ingresa un número válido para el precio.");
            return;
        }

        if (bookService.buscarLibro(isbnTxt).isPresent()) {
            showError("ISBN existente", "Ya existe un libro con ese ISBN.");
            return;
        }

        bookService.guardarLibro(new Book(isbnTxt, titleTxt, price, null));
        refreshTable();
        clearFields();
    }

    @FXML
    private void onUpdateBook() {
        Book sel = tableBooks.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showError("Selecciona un libro", "Primero selecciona un libro en la tabla.");
            return;
        }
        String titleTxt = titleField.getText().trim();
        String priceTxt = priceField.getText().trim();
        if (titleTxt.isEmpty() || priceTxt.isEmpty()) {
            showError("Campos vacíos", "Título y Precio son obligatorios.");
            return;
        }
        double price;
        try {
            price = Double.parseDouble(priceTxt);
        } catch (NumberFormatException e) {
            showError("Precio inválido", "Ingresa un número válido para el precio.");
            return;
        }

        sel.setTitle(titleTxt);
        sel.setPrice(price);
        bookService.guardarLibro(sel);
        refreshTable();
    }

    @FXML
    private void onDeleteBook() {
        Book sel = tableBooks.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showError("Selecciona un libro", "Primero selecciona un libro en la tabla.");
            return;
        }
        bookService.eliminarLibro(sel.getIsbn());
        refreshTable();
        clearFields();
    }



    @FXML
    private void onSearchBook() {
        String isbnText  = isbnField.getText().trim().toLowerCase();
        String titleText = titleField.getText().trim().toLowerCase();
        String priceText = priceField.getText().trim();

        List<Book> filtered = bookService.listarLibros().stream()
                .filter(b -> {
                    boolean matchesIsbn = true;
                    boolean matchesTitle = true;
                    boolean matchesPrice = true;

                    if (!isbnText.isEmpty()) {
                        matchesIsbn = b.getIsbn() != null && b.getIsbn().toLowerCase().contains(isbnText);
                    }
                    if (!titleText.isEmpty()) {
                        matchesTitle = b.getTitle() != null && b.getTitle().toLowerCase().contains(titleText);
                    }
                    if (!priceText.isEmpty()) {
                        try {
                            double priceQuery = Double.parseDouble(priceText);
                            matchesPrice = Double.compare(b.getPrice(), priceQuery) == 0;
                        } catch (NumberFormatException e) {
                            matchesPrice = false;
                        }
                    }
                    return matchesIsbn && matchesTitle && matchesPrice;
                })
                .toList();

        tableBooks.getItems().setAll(filtered);
    }


    @FXML
    private void onBackToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/programacion/avanzada/bookstoreapp/menu-view.fxml")
        );
        loader.setControllerFactory(context::getBean);
        Scene scene = new Scene(loader.load(), 800, 600);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Menú Principal");
    }

    // — Helpers —
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
