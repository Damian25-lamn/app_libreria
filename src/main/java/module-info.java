module org.programacion.avanzada.bookstoreapp {
    requires javafx.controls;
    requires javafx.fxml;

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
    requires spring.aop;
    requires spring.core;
    requires spring.beans;

    exports org.programacion.avanzada.bookstoreapp;

    opens org.programacion.avanzada.bookstoreapp to javafx.fxml, spring.core, spring.context, spring.beans, spring.aop, spring.data.commons;
    opens org.programacion.avanzada.bookstoreapp.config to spring.core, spring.context, spring.beans, spring.aop, spring.data.commons;
    opens org.programacion.avanzada.bookstoreapp.service to spring.core, spring.context, spring.beans, spring.aop, spring.data.commons;
    opens org.programacion.avanzada.bookstoreapp.repository to spring.core, spring.beans, spring.context, spring.aop, spring.data.commons;
    opens org.programacion.avanzada.bookstoreapp.model to javafx.base, spring.core, spring.context, spring.beans, spring.aop, spring.data.commons;
    opens org.programacion.avanzada.bookstoreapp.controller to javafx.fxml, spring.core, spring.beans, spring.context, spring.aop, spring.data.commons;
}
