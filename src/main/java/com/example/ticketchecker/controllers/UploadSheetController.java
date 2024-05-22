package com.example.ticketchecker.controllers;

import com.example.ticketchecker.model.smallFeatures.CloseProgram;
import com.example.ticketchecker.model.smallFeatures.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UploadSheetController implements SceneController{

    private UploadSheetController usc;
    private Stage currStage;

    @FXML
    private TextField cellRangeTextField;

    @FXML
    private Button closeButton;

    @FXML
    private TextField fileNameTextField;

    @FXML
    private StackPane fillOutFormPane;

    @FXML
    private TextField googleSheetsTextField;

    @FXML
    private ImageView isaImageView;

    @FXML
    private Button logOutButton;

    @FXML
    private StackPane mainStackPane;

    @FXML
    private TextField sheetNameTextField;

    @FXML
    private StackPane spreadSheetPane;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button submitButton;

    @FXML
    private Button goBackButton;

    public void initialize(){
        usc = new UploadSheetController();
        fillOutFormPane.setVisible(true);
        spreadSheetPane.setVisible(false);
        welcomeLabel.setText("Welcome " + MainController.currUser.getFirstName());


    }

    public void setCurrentStage(Stage stage){
        this.currStage = stage;
    }

    @FXML
    void closeProgram(ActionEvent event) {
        if(event.getSource() == closeButton) {
            CloseProgram.closeProgram(closeButton);
        }
    }

    @FXML
    void moveToLogInScreen(ActionEvent event) throws IOException {
        if(event.getSource() == logOutButton){
            new SceneSwitch(currStage, "Welcome!", "TicketCheckWelcomePage.fxml", usc);
        }
    }

    @FXML
    void moveToMenuScreen(ActionEvent event) throws IOException{
        if(event.getSource() == goBackButton){
            new SceneSwitch(currStage, "Menu Screen", "MenuScreen.fxml", usc);
        }
    }

    @FXML
    void validateSheetInformation(ActionEvent event){
        if(event.getSource() == submitButton){
            String url = googleSheetsTextField.getText();
            String fileName = fileNameTextField.getText();
            String sheetName = sheetNameTextField.getText();
            String cellRange = cellRangeTextField.getText();

            // we will now validate the textfield infomation

        }
    }

}
