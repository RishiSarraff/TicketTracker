package com.example.ticketchecker.controllers;

import com.example.ticketchecker.model.CloseProgram;
import com.example.ticketchecker.model.SceneController;
import com.example.ticketchecker.model.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuScreenController implements SceneController {

    private Stage currStage;


    @FXML
    private Button closeButton;

    @FXML
    private Button logOutButton;

    MenuScreenController msc;

    public void setCurrentStage(Stage stage){
        currStage = stage;
    }




    @FXML
    public void initialize(){

        msc = new MenuScreenController();

    }

    @FXML
    void closeProgram() {
        CloseProgram.closeProgram(closeButton);
    }

    @FXML
    void moveToLogIn(ActionEvent event) throws IOException {
        if (event.getSource() == logOutButton) {
            new SceneSwitch(currStage, "Welcome!", "TicketCheckWelcomePage.fxml", msc);
        }

    }
}
