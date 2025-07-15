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
import org.programacion.avanzada.bookstoreapp.model.Author;
import org.programacion.avanzada.bookstoreapp.service.AuthorService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthorCrudController {

    private final AuthorService authorService;
    private final ApplicationContext context;

    public AuthorCrudController(AuthorService authorService, ApplicationContext context) {
        this.authorService = authorService;
        this.context = context;
    }

    @FXML private TableView<Author> tableAuthors;
    @FXML private TableColumn<Author, Integer> idColumn;
    @FXML private TableColumn<Author, String> nameColumn;

    @FXML private TextField nameField;
    @FXML
    private VBox rootVBox; // Asegúrate de agregar fx:id="rootVBox" al VBox

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        refreshTable();
        tableAuthors.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                nameField.setText(newSel.getName());
            }
        });
        rootVBox.setPadding(new Insets(20, 20, 20, 20));
    }

    @FXML
    private void onNewAuthor() {
        clearFields();
        tableAuthors.getSelectionModel().clearSelection();
    }

    @FXML
    private void onSaveAuthor() {
        Author a = new Author(
                null,
                nameField.getText(),
                null
        );
        authorService.guardarAutor(a);
        refreshTable();
        clearFields();
    }

    @FXML
    private void onUpdateAuthor() {
        Author sel = tableAuthors.getSelectionModel().getSelectedItem();
        if (sel != null) {
            sel.setName(nameField.getText());
            authorService.guardarAutor(sel);
            refreshTable();
        }
    }

    @FXML
    private void onDeleteAuthor() {
        Author sel = tableAuthors.getSelectionModel().getSelectedItem();
        if (sel != null) {
            authorService.eliminarAutor(sel.getId());
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
        tableAuthors.getItems().setAll(authorService.listarAutores());
    }

    private void clearFields() {
        nameField.clear();
    }
}
