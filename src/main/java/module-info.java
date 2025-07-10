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

    opens org.programacion.avanzada.bookstoreapp to javafx.fxml;
    exports org.programacion.avanzada.bookstoreapp;
}