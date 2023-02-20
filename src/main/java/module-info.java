module com.example.lab23a {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens com.example.lab23a to javafx.fxml;
    exports com.example.lab23a;
    exports com.example.lab23a.model;
    exports com.example.lab23a.PDF;
}