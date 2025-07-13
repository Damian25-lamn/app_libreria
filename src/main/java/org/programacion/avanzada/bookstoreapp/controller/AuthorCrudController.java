package org.programacion.avanzada.bookstoreapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.programacion.avanzada.bookstoreapp.model.Author;
import org.programacion.avanzada.bookstoreapp.service.AuthorService;
import org.springframework.stereotype.Component;

@Component
public class AuthorCrudController {

    private final AuthorService authorService;

    public AuthorCrudController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @FXML private TableView<Author> tableAuthors;
    @FXML private TableColumn<Author, Integer> idColumn;
    @FXML private TableColumn<Author, String>  nameColumn;

    @FXML private TextField idField;
    @FXML private TextField nameField;

    @FXML
    private void initialize() {
        // 1) configura columnas
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // 2) refresca datos
        refreshTable();

        // 3) al hacer clic en una fila, cargar sus datos en el formulario
        tableAuthors.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                idField.setText(String.valueOf(newSel.getId()));
                nameField.setText(newSel.getName());
            }
        });
    }

    @FXML
    private void onNewAuthor() {
        clearFields();
        tableAuthors.getSelectionModel().clearSelection();
    }

    @FXML
    private void onSaveAuthor() {
        // crea nuevo si no existía
        Author a = new Author(
                Integer.parseInt(idField.getText()),
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

    private void refreshTable() {
        tableAuthors.getItems().setAll(authorService.listarAutores());
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
    }
}
