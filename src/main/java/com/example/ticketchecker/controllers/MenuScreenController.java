package com.example.ticketchecker.controllers;

import com.example.ticketchecker.model.smallFeatures.CloseProgram;
import com.example.ticketchecker.model.smallFeatures.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label welcomeLabel;

    @FXML
    private Button logOutButton;

    @FXML
            private Button tbdButton;

    MenuScreenController msc;


    public void setCurrentStage(Stage stage){
        currStage = stage;
    }


    @FXML
    public void initialize(){

        String firstName = MainController.currUser.getFirstName();

        msc = new MenuScreenController();
        welcomeLabel.setText("Welcome " + firstName);

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
    void moveToUploadPage(ActionEvent event) throws IOException {
        if(event.getSource() == uploadFilesButton){
           new SceneSwitch(currStage, "Welcome to the Ticket Validator!", "UploadSheetData.fxml", msc);
        }
    }

    @FXML
    void moveToVerificationPage(ActionEvent event) throws IOException {
        if(event.getSource() == verificationPageButton){
            new SceneSwitch(currStage, "Welcome to the Email Sender!", "EmailSender.fxml", msc);
            //TO:DO Implement the email sender pade
        }
    }

    @FXML
    void moveToTBDPage(ActionEvent event){
        if(event.getSource() == tbdButton){
            // we will moe to the tbd button
        }
    }


}
