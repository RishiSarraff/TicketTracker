module com.example.ticketchecker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ticketchecker to javafx.fxml;
    exports com.example.ticketchecker;
}