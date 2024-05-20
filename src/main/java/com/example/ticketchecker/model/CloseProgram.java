package com.example.ticketchecker.model;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CloseProgram {

    public static void closeProgram(Button currButton){
        Scene scene = currButton.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }
}
