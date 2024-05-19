package com.example.ticketchecker.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

public class MenuScreenController {

    @FXML
    private HBox navigationHBox;

    @FXML
    private StackPane navigationPane;

    @FXML
    void initialize(){

        HBox.setHgrow(navigationHBox, Priority.ALWAYS);

    }
}
