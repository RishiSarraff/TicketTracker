module com.example.ticketchecker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ticketchecker to javafx.fxml;
    exports com.example.ticketchecker;
    exports com.example.ticketchecker.controllers;
    opens com.example.ticketchecker.controllers to javafx.fxml;
    exports com.example.ticketchecker.database;
    opens com.example.ticketchecker.database to javafx.fxml;
    exports com.example.ticketchecker.model;
    opens com.example.ticketchecker.model to javafx.fxml;
}