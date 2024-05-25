package com.example.ticketchecker.controllers;

import com.example.ticketchecker.model.TicketSubmission;
import com.example.ticketchecker.model.sheets.SheetDetailsValidator;
import com.example.ticketchecker.model.sheets.SheetsInterpreter;
import com.example.ticketchecker.model.smallFeatures.CloseProgram;
import com.example.ticketchecker.model.smallFeatures.DialogUtils;
import com.example.ticketchecker.model.smallFeatures.SceneSwitch;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
                    // all inputs are valid, so we pass them into the sheets interpreter which returns
                    // interprets them properly and returns a error pop up through sheets interpreter if not allowdd,
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
            currLabel.setText("Email: " + tick.getEmailAddress()+ "\t" + "\t" +
                    "Name: " + tick.getFirstName() + " " + tick.getLastName()+ "\t"+"\t" +
                    "Year: " + tick.getYear() + "\t"+"\t" +
                    "Member Status: " + tick.getMemberStatus()+ "\t"+"\t" +
                    "Payment Option: " + tick.getPaymentOption()+"\t"+"\t" +
                    "Phone Number: " + tick.getPhoneNumber());

            ComboBox<String> dropdown = new ComboBox<>();
            dropdown.getItems().addAll("Paid", "Email Sent", "Reset");
            dropdown.setOnAction( e -> {
                    String typeOfHighlight = dropdown.getValue();

                    handleColorChange(typeOfHighlight, tick);
            });

            ticketBox.getChildren().addAll(dropdown, currLabel);
            mainSheetsListView.getItems().add(ticketBox);
        }
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
