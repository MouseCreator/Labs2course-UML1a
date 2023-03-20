module com.example.lab23a {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires java.desktop;

    opens com.example.lab23a to javafx.fxml;
    exports com.example.lab23a;
    exports com.example.lab23a.model;
    exports com.example.lab23a.PDF;
    exports com.example.lab23a.Server;
    exports com.example.lab23a.model.builder;
}