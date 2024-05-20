package com.example.ticketchecker.model;

import com.example.ticketchecker.MainApplication;
import com.example.ticketchecker.controllers.SceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitch {

    public SceneSwitch(Stage currStage, String title, String fxml, SceneController sceneController) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
        Pane rootPane = fxmlLoader.load();
        Scene newScene = new Scene(rootPane, 1280, 720);

        sceneController = fxmlLoader.getController();
        sceneController.setCurrentStage(currStage);

        currStage.setTitle(title);
        currStage.setScene(newScene);



        currStage.show();

    }


}
