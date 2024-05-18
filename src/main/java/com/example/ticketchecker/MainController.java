package com.example.ticketchecker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class MainController {


        @FXML
        private Button closeButton;

        @FXML
        private BorderPane rootPane;

        @FXML
        private Label titleLabel;

        @FXML
        private StackPane validationPane;

        @FXML
        private StackPane validPane;

        @FXML
        private TextField validationNumberField;

        @FXML
        private Button howToUseButton;

        @FXML
        private TextField passwordField;

        @FXML
        private TextField usernameField;

        @FXML
        private Button submitButton;

        @FXML
        private Button pinSubmit;

        @FXML
        private StackPane userIdentityPane;

        @FXML
        private Button newUserButton;

        @FXML
        private Button oldUserButton;

        private static int newOrOld;

        private Stage currStage;

        public void setCurrentStage(Stage stage){
                currStage = stage;
        }

        @FXML
        public void initialize(){
                validPane.setVisible(false);
                userIdentityPane.setVisible(false);
                validationPane.setVisible(true);
                rootPane.widthProperty().addListener((observable, oldValue, newValue) -> {
                        int newFontSize = Math.max(12, (int) newValue.doubleValue() / 12); // Adjust scaling factor as needed
                        titleLabel.setStyle("-fx-font-size: " + newFontSize + "px; -fx-font-family: Phosphate");
                });

                rootPane.heightProperty().addListener((observable, oldValue, newValue) -> {
                        int newFontSize = Math.max(12, (int) newValue.doubleValue() / 12); // Adjust scaling factor as needed
                        titleLabel.setStyle("-fx-font-size: " + newFontSize + "px; -fx-font-family: Phosphate");
                });


        }

        @FXML
        void closeProgram(ActionEvent event) {
                Scene scene = closeButton.getScene();
                        Stage stage = (Stage) scene.getWindow();
                        // Close the stage
                        stage.close();

        }

        @FXML
        void goToAppManual(ActionEvent event) {
                if(event.getSource() == howToUseButton){
                        // scene transition into appmanual
                }

        }

        @FXML
        void validateUser(ActionEvent event){
                if(event.getSource() == submitButton) {
                        String username = usernameField.getText();
                        String password = passwordField.getText();
                        if(logInValidator.validateUser(newOrOld, username, password, currStage)){
                                if(newOrOld == 0){
                                        userIdentityPane.setVisible(true);
                                        validPane.setVisible(false);
                                }
                                else if(newOrOld == 1){
                                        // go onto the next page

                                }
                        }
                        else{
                              usernameField.clear();
                              passwordField.clear();
                        }
                }
                else if(event.getSource() == pinSubmit){
                        String pin = validationNumberField.getText();
                        if(logInValidator.validatePIN(pin, currStage)){
                                userIdentityPane.setVisible(true);
                                validationPane.setVisible(false);
                        }
                        validationNumberField.clear();
                }
        }

        @FXML
        void typeOfUser(ActionEvent event){
                if(event.getSource() == newUserButton) {
                        newOrOld = 0;
                }
                else if(event.getSource() == oldUserButton){
                        newOrOld = 1;

                }
                userIdentityPane.setVisible(false);
                validPane.setVisible(true);
        }



}
