package com.example.ticketchecker.controllers;

import com.example.ticketchecker.model.CloseProgram;
import com.example.ticketchecker.model.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuScreenController implements SceneController {

    private Stage currStage;


    @FXML
    private Button closeButton;

    @FXML
    private Button verificationPageButton;

    @FXML
    private Button uploadFilesButton;

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

    @FXML
    void moveToUploadPage(ActionEvent event){
        if(event.getSource() == uploadFilesButton){
            // we go to the upload files page
        }
    }

    @FXML
    void moveToVerificationPage(ActionEvent event){
        if(event.getSource() == verificationPageButton){
            // we move to the sending emails page.
        }
    }
}
