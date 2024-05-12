package com.example.ticketchecker;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.sql.SQLException;

public class logInValidator {

    static DatabaseDriver db = DatabaseDriver.getInstance("TickCheckDB");

    static final int realPin = 115647;

    public static boolean validateUser(int newOrOldUser, String username, String password){
        try{
            if(!db.isConnected) {
                db.connect();
            }

            db.createUserTable();

            if(!validatePassword(password)){
                return false;
            }

            if(!db.findUser(username, password)){
                if(newOrOldUser == 0) {
                    db.insertUser(username, password);
                    db.commit();
                    dialogPopUp("Created Account Successfully", "Please Proceed");
                    return true;
                }
                else{
                    dialogPopUp("Username or Password is Wrong, Please Retry", "Retry Information Entry");
                    return false;
                }
            }
            else{
                dialogPopUp("User Exists and Can Now Log In", "Success");
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean validatePIN(String pin){
        pin = pin.trim();
        for(int i = 0; i<pin.length();i++){
            Character currChar = pin.charAt(i);
            if(!Character.isDigit(currChar)){
                dialogPopUp("Invalid PIN, you must type in only digits", "Retry PIN Entry");
                return false;
            }
        }

        int userInput = Integer.parseInt(pin);
        if(userInput == realPin){
            dialogPopUp("Correct Pin Was Entered, You May Proceed", "Success");
            return true;
        }

        dialogPopUp("Wrong Pin Number, Try Again", "Invalid Number Entry");
        return false;
    }

    public static boolean validatePassword(String password){
        if(password.length() < 8){
            dialogPopUp("Password is not allowed, must be at least 8 characters long", "Retry password");
            return false;
        }
        int countOfDigits = 0;

        for(int i = 0; i<password.length(); i++){
            Character currChar = password.charAt(i);
            if(Character.isDigit(currChar)){
                countOfDigits++;
            }
        }

        if(countOfDigits <=0){
            dialogPopUp("Password must contain at least 1 digit", "Retry again");
            return false;
        }

        return true;
    }


    static void dialogPopUp(String alert, String typeOfAlert){
        DialogPane dp = new DialogPane();
        dp.setHeaderText(null);
        dp.setContentText(alert);

        ButtonType exit = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
        dp.getButtonTypes().add(exit);

        Dialog<String> dLog = new Dialog<>();
        dLog.setDialogPane(dp);
        dLog.setTitle(typeOfAlert);
        dLog.showAndWait();
    }

}
