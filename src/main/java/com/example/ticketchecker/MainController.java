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
        private Button howToUseButton;

        @FXML
        private TextField passwordField;

        @FXML
        private TextField usernameField;

        @FXML
        private Button submitButton;

        @FXML
        public void initialize(){
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
                        System.out.println("hello");
                        String username = usernameField.getText();
                        String password = passwordField.getText();
                        logInValidator.validate(username, password);
                }
        }

}
