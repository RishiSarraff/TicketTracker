package com.example.ticketchecker.controllers;

import com.example.ticketchecker.model.ForgotPassword;
import com.example.ticketchecker.model.User;
import com.example.ticketchecker.model.jsonParsers.BoardParser;
import com.example.ticketchecker.model.smallFeatures.CloseProgram;
import com.example.ticketchecker.model.smallFeatures.SceneSwitch;
import com.example.ticketchecker.model.validators.LogInValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController implements SceneController {


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
        private Button backButton2;

        @FXML
        private Button newUserButton;

        @FXML
        private Button oldUserButton;

        @FXML
        private TextField userFirstNameField;

        @FXML
        private TextField userLastNameField;

        @FXML
        private Button backButton;

        @FXML
        private Hyperlink forgotPasswordLink;

        private static int newOrOld;

        private Stage currStage;

        private MainController mc;




        public static User currUser;


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


                mc = new MainController();

        }

        @FXML
        void closeProgram() {
                CloseProgram.closeProgram(closeButton);
        }

        @FXML
        void goToAppManual(ActionEvent event) throws IOException {
                if(event.getSource() == howToUseButton){
                       new SceneSwitch(currStage, "App Manual", "AppManual.fxml", mc);
                }
        }

        @FXML
        void validateUser(ActionEvent event) throws IOException {
                if(event.getSource() == submitButton) {
                        String username = usernameField.getText();
                        String password = passwordField.getText();
                        String userFirstName = userFirstNameField.getText();
                        String userLastName = userLastNameField.getText();

                        if(LogInValidator.validateUser(newOrOld, username, password, currStage, currUser.getFirstName(), currUser.getLastName())){
                                String userEmail = BoardParser.emailRetriever(userFirstName, userLastName);
                                userEmail = userEmail.trim();
                                User currentUser = currUser.getUserInstance();
                                currentUser.setFirstName(userFirstName);
                                currentUser.setLastName(userLastName);
                                currentUser.setEmail(userEmail);
                                currentUser.setUsername(username);
                                if(newOrOld == 0){
                                        userIdentityPane.setVisible(true);
                                        validPane.setVisible(false);
                                }
                                else if(newOrOld == 1){
                                      new SceneSwitch(currStage, "Menu Screen", "MenuScreen.fxml", mc);
                                }
                                System.out.println(userEmail);
                        }
                        else{
                              usernameField.clear();
                              passwordField.clear();
                        }
                }
                else if(event.getSource() == pinSubmit){
                        String pin = validationNumberField.getText();
                        String userFirstName = userFirstNameField.getText().trim();
                        String userLastName = userLastNameField.getText().trim();

                        if(LogInValidator.validatePIN(pin, currStage)){
                                userIdentityPane.setVisible(true);
                                validationPane.setVisible(false);
                        }

                        String userEmail = BoardParser.emailRetriever(userFirstName, userLastName);
                        userEmail = userEmail.trim();

                        String username = ForgotPassword.getUsername(userFirstName, userLastName);

                        currUser = new User(userFirstName, userLastName, userEmail);
                        currUser.setUsername(username);

                        currUser.setFirstName(userFirstName);
                        currUser.setLastName(userLastName);
                        currUser.setEmail(userEmail);

                        currUser.setCurrStage(currStage);

                        userFirstNameField.clear();
                        userLastNameField.clear();
                        validationNumberField.clear();
                        System.out.println(userEmail);
                }
        }

        @FXML
        void typeOfUser(ActionEvent event){
                if(event.getSource() == newUserButton) {
                        newOrOld = 0;
                        forgotPasswordLink.setVisible(false);
                }
                else if(event.getSource() == oldUserButton){
                        newOrOld = 1;
                        forgotPasswordLink.setVisible(true);
                }
                userIdentityPane.setVisible(false);
                validPane.setVisible(true);
        }

        @FXML
        void goBackToUserType(ActionEvent event){
                if(event.getSource() == backButton){
                        userIdentityPane.setVisible(true);
                        validPane.setVisible(false);
                }
        }

        @FXML
        void goBackToLogInPane(ActionEvent event){
                if(event.getSource() == backButton2){
                        userIdentityPane.setVisible(false);
                        validationPane.setVisible(true);
                }
        }

        @FXML
        void sendVerificationEmail(ActionEvent event){
                if(event.getSource() == forgotPasswordLink){
                        if(ForgotPassword.manageForgotPasswordInteraction(currUser.getFirstName(), currUser.getLastName(), currStage)) {
                                ForgotPassword.passwordGenerator(currUser.getUsername(), currUser.getFirstName(), currUser.getLastName(), currUser.getEmail());
                        }
                }
        }



}
