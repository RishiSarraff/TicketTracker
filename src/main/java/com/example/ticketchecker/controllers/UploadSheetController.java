package com.example.ticketchecker.controllers;

import com.example.ticketchecker.model.TicketSubmission;
import com.example.ticketchecker.model.sheets.SheetDetailsValidator;
import com.example.ticketchecker.model.sheets.SheetsInterpreter;
import com.example.ticketchecker.model.smallFeatures.CloseProgram;
import com.example.ticketchecker.model.smallFeatures.DialogUtils;
import com.example.ticketchecker.model.smallFeatures.SceneSwitch;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

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
    private TextField firstNameSearch;

    @FXML
    private TextField lastNameSearch;

    @FXML
    private Button getPeopleButton;

    @FXML
    private StackPane fillOutFormPane;

    @FXML
    private TextField googleSheetsTextField;

    @FXML
    private ImageView isaImageView;

    @FXML
    private ListView<HBox> mainSheetsListView;

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

    private SheetsInterpreter si;

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
    void validateSheetInformation(ActionEvent event) throws GeneralSecurityException, IOException {
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
                DialogUtils.dialogPopUp("The cell range format is off, it should start ONLY from column B and follow this format B2:J100", "Cell Range Input Error", currStage);
            }

            else{
                    si = new SheetsInterpreter();
                    si.setApplicationName(fileName);
                    si.setCellRange(cellRange);
                    si.setSheetID(spreadsheetID);
                    si.setSheetName(sheetName);
                    List<TicketSubmission> fetchedDataRows = SheetDetailsValidator.sendToSheetsInterpreter(fileName, spreadsheetID, sheetName, cellRange);
                    ObservableList<TicketSubmission> obsList = FXCollections.observableList(fetchedDataRows);
                    createHBoxes(obsList);


                // insert this list into the listview
                fillOutFormPane.setVisible(false);
                spreadSheetPane.setVisible(true);
            }
        }
    }

    private void createHBoxes(ObservableList<TicketSubmission> obsList) {
        mainSheetsListView.getItems().clear();
        for(TicketSubmission tick : obsList){
            Label currLabel = new Label();
            HBox ticketBox = new HBox();
            labelSetter(currLabel, tick);
            currLabel.setPadding(new Insets(5, 0, 5, 20));
            ticketBox.setStyle("-fx-background-color: #c7e8fb; -fx-background-radius: 30");
            ticketBox.setPadding(new Insets(20, 0, 20, 20));



            ComboBox<String> dropdown = new ComboBox<>();
            dropdown.setStyle("-fx-background-color: white; -fx-background-radius: 30");
            dropdown.getEditor().setFont(Font.font("Phosphate", 18));

            dropdown.getItems().addAll("Paid", "Email Sent", "Reset");
            dropdown.setOnAction( e -> {
                    String typeOfHighlight = dropdown.getValue();

                    handleColorChange(typeOfHighlight, tick);
            });

            ticketBox.getChildren().addAll(dropdown, currLabel);
            mainSheetsListView.getItems().add(ticketBox);
        }
    }

    private void labelSetter(Label currLabel, TicketSubmission tick) {
        StringBuilder str = new StringBuilder();
        if(tick.getEmailAddress()!=null) {
            str.append("Email " + tick.getEmailAddress() + "\n");
        }

        if(tick.getFirstName()!=null && tick.getLastName() == null) {
            str.append("Name: " + tick.getFirstName() + "\n");
        }
        else if(tick.getFirstName()==null && tick.getLastName() != null) {
            str.append("Name: " + tick.getLastName() +"\n");
        }
        else if(tick.getFirstName()!=null && tick.getLastName() != null) {
            str.append("Name: " + tick.getFirstName() + " " + tick.getLastName()+ "\n" );
        }

        if(tick.getYear() != 0){
            str.append("Year: " + tick.getYear() + "\n");
        }
        if(tick.getMemberStatus() != null){
            str.append("Member Status: " + tick.getMemberStatus()+ "\n");
        }
        if(tick.getPaymentOption() != null){
            str.append("Payment Option: " + tick.getPaymentOption()+"\n");
        }
        if(tick.getPhoneNumber() != 0){
            str.append("Phone Number: " + tick.getPhoneNumber());
        }

        currLabel.setText(str.toString());
        currLabel.setTextFill(Paint.valueOf("#6082B6"));
        currLabel.setFont(Font.font("Phosphate", 14));
    }

    private void handleColorChange(String typeOfHighlight, TicketSubmission tick) {
    }

    @FXML
    private void filterListViewSettings(ActionEvent event) throws GeneralSecurityException, IOException {

        if(event.getSource() == getPeopleButton) {
            String fName = firstNameSearch.getText();
            String lName = lastNameSearch.getText();

            List<TicketSubmission> fetchedData = SheetDetailsValidator.sendToSheetsInterpreter(si.getApplicationName(), si.getSheetID(), si.getSheetName(), si.getCellRange());
            List<TicketSubmission> filteredData = new ArrayList<>();

            for (TicketSubmission tick : fetchedData) {
                if (fName.equals("") && lName.equals("")) {
                    filteredData.add(tick);
                } else if (fName.equals("") && !lName.equals("")) {
                    String processedDataLastName = lName.toLowerCase().trim();
                    String processedLName = tick.getLastName().toLowerCase();

                    if (processedDataLastName.equals(processedLName)) {
                        filteredData.add(tick);
                    }
                } else if (!fName.equals("") && lName.equals("")) {
                    String processedDataFirstName = fName.toLowerCase().trim();
                    String processedFName = tick.getFirstName().toLowerCase();

                    if (processedDataFirstName.equals(processedFName)) {
                        filteredData.add(tick);
                    }
                } else {
                    String processedDataFirstName = fName.toLowerCase().trim();
                    String processedFName = tick.getFirstName().toLowerCase();
                    String processedDataLastName = lName.toLowerCase().trim();
                    String processedLName = tick.getLastName().toLowerCase();

                    if (processedDataFirstName.equals(processedFName) && processedDataLastName.equals(processedLName)) {
                        filteredData.add(tick);
                    }
                }
            }
            ObservableList<TicketSubmission> obsList = FXCollections.observableList(filteredData);
            createHBoxes(obsList);
        }

    }
}
