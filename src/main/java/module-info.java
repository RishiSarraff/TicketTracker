module com.example.ticketchecker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.services.sheets;
    requires org.apache.commons.text;
    requires org.json;
    requires com.google.gson;
    requires jdk.httpserver;
    requires com.google.api.services.gmail;
    requires org.apache.commons.codec;
    requires jakarta.mail;

    opens com.example.ticketchecker to javafx.fxml;
    exports com.example.ticketchecker;
    exports com.example.ticketchecker.controllers;
    opens com.example.ticketchecker.controllers to javafx.fxml;
    exports com.example.ticketchecker.database;
    opens com.example.ticketchecker.database to javafx.fxml;
    exports com.example.ticketchecker.model;
    opens com.example.ticketchecker.model to javafx.fxml;
    exports com.example.ticketchecker.model.sheets;
    opens com.example.ticketchecker.model.sheets to javafx.fxml;
    exports com.example.ticketchecker.model.smallFeatures;
    opens com.example.ticketchecker.model.smallFeatures to javafx.fxml;
    exports com.example.ticketchecker.model.validators;
    opens com.example.ticketchecker.model.validators to javafx.fxml;
    exports com.example.ticketchecker.model.jsonParsers;
    opens com.example.ticketchecker.model.jsonParsers to javafx.fxml;
}