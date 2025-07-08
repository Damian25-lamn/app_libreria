module org.programacion.avanzada.bookstoreapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.programacion.avanzada.bookstoreapp to javafx.fxml;
    exports org.programacion.avanzada.bookstoreapp;
}