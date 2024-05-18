package com.example.ticketchecker.model;

import com.example.ticketchecker.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneSwitch {

    public SceneSwitch(Stage currStage, String title, String fxml) throws IOException {
        AnchorPane newAnchorPane = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(fxml)));
        Scene newScene = new Scene(newAnchorPane);
        currStage.setTitle(title);
        currStage.setScene(newScene);
        currStage.show();

    }


}
