package com.example.ticketchecker.controllers;

import com.example.ticketchecker.model.sheets.SheetDetailsValidator;
import com.example.ticketchecker.model.smallFeatures.CloseProgram;
import com.example.ticketchecker.model.smallFeatures.DialogUtils;
import com.example.ticketchecker.model.smallFeatures.SceneSwitch;
import com.google.api.services.sheets.v4.model.Sheet;
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
            String spreadsheetID = SheetDetailsValidator.spreadsheetIDGetter(url);


            if(url == null || url.isEmpty()){
                DialogUtils.dialogPopUp("URL is empty please, enter a nonempty URL", "Empty URL", currStage);
            }

            else if(spreadsheetID == null){
                DialogUtils.dialogPopUp("This is an invalid url re-enter the url", "Invalid URL", currStage);
                googleSheetsTextField.setText("");
            }

            else if(!SheetDetailsValidator.sheetNameValidator(sheetName, fileName)){
                // if the sheet name is not similar to the filename
                DialogUtils.dialogPopUp("Sheet name and file name do not match or the names are empty, check your inputs again", "Retype sheet information", currStage);
            }

            else if(!SheetDetailsValidator.cellRangeValidator(cellRange)){
                // if the cell range is not a validate input then that is not allowed
                DialogUtils.dialogPopUp("The cell range format is off, it should follow exactly something along the lines of A2:J100", "Cell Range Input Error", currStage);
            }

            else{
                // all inputs are valid, so we pass them into the sheets interpreter which returns
                // interprets them properly and returns a error pop up through sheets interpreter if not allowdd,
                SheetDetailsValidator.sendToSheetsInterpreter(fileName, spreadsheetID, sheetName, cellRange);
            }
        }
    }

}
