module org.programacion.avanzada.bookstoreapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires spring.context;
    requires spring.data.jdbc;
    requires spring.jdbc;
    requires java.sql;
    requires spring.data.commons;
    requires static lombok;
    requires com.zaxxer.hikari;
    requires spring.data.relational;
    requires spring.tx;
    requires spring.core;
    requires spring.beans;

    // abrir paquetes para reflexión de Spring
    opens org.programacion.avanzada.bookstoreapp to javafx.fxml, spring.core, spring.beans;

// Añadir paquetes para reflexión de Spring
    opens org.programacion.avanzada.bookstoreapp.config to spring.core, spring.beans;
    opens org.programacion.avanzada.bookstoreapp.model to spring.core, spring.beans;
    opens org.programacion.avanzada.bookstoreapp.model.lineitem to spring.core, spring.beans;
    opens org.programacion.avanzada.bookstoreapp.service to spring.core, spring.beans;

    exports org.programacion.avanzada.bookstoreapp;
}
