package com.example.ticketchecker.model;

import com.example.ticketchecker.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneSwitch {

    public SceneSwitch(Stage currStage, String title, String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
        Pane rootPane = fxmlLoader.load();
        Scene newScene = new Scene(rootPane, 1280, 720);
        currStage.setTitle(title);
        currStage.setScene(newScene);
        currStage.show();

    }


}
