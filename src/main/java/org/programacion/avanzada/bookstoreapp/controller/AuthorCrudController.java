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

    @FXML
    private TableView<Author> tableAuthors;
    @FXML
    private TableColumn<Author, Integer> idColumn;
    @FXML
    private TableColumn<Author, String> nameColumn;

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private VBox rootVBox;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        refreshTable();

        // Listener para selección de la tabla
        tableAuthors.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                idField.setText(String.valueOf(newSel.getId()));
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
        try {
            Integer id = null;
            String idText = idField.getText().trim();
            if (!idText.isEmpty()) {
                id = Integer.parseInt(idText); // permites que el usuario ingrese ID manualmente
            }
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                Author a = new Author(id, name, null);
                authorService.guardarAutor(a);
                refreshTable();
                clearFields();
            } else {
                showAlert("El nombre no puede estar vacío.");
            }
        } catch (NumberFormatException e) {
            showAlert("El ID debe ser un número válido.");
        }
    }



    @FXML
    private void onUpdateAuthor() {
        Author sel = tableAuthors.getSelectionModel().getSelectedItem();
        String name = nameField.getText().trim();
        if (sel != null && !name.isEmpty()) {
            sel.setName(name);
            authorService.guardarAutor(sel);
            refreshTable();
        } else {
            showAlert("Selecciona un autor y asegúrate de ingresar el nombre.");
        }
    }

    @FXML
    private void onDeleteAuthor() {
        Author sel = tableAuthors.getSelectionModel().getSelectedItem();
        if (sel != null) {
            authorService.eliminarAutor(sel.getId());
            refreshTable();
            clearFields();
        } else {
            showAlert("Selecciona un autor para eliminar.");
        }
    }

    @FXML
    private void onSearchAuthor() {
        String idText = idField.getText().trim();
        String nameText = nameField.getText().trim().toLowerCase();

        var filtered = authorService.listarAutores().stream()
                .filter(a -> {
                    boolean matchesId = true;
                    boolean matchesName = true;

                    if (!idText.isEmpty()) {
                        try {
                            int id = Integer.parseInt(idText);
                            matchesId = a.getId() == id;
                        } catch (NumberFormatException e) {
                            matchesId = false;
                        }
                    }
                    if (!nameText.isEmpty()) {
                        matchesName = a.getName().toLowerCase().contains(nameText);
                    }
                    return matchesId && matchesName;
                })
                .toList();

        tableAuthors.getItems().setAll(filtered);
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
        idField.clear();
        nameField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
