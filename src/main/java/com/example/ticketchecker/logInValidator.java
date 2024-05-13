package com.example.ticketchecker;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

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
                    DialogUtils.dialogPopUp("Created account successfully", "Please proceed");
                    return true;
                }
                else{
                    DialogUtils.dialogPopUp("Username or password is wrong, please retry", "Retry information entry");
                    return false;
                }
            }
            else if(db.findUser(username, password)){
                DialogUtils.dialogPopUp("User exists and can now log in", "Success");
                return true;
            }
            else{
                DialogUtils.dialogPopUp("User could not be found", "Please retry");
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean validatePIN(String pin){
        pin = pin.trim();
        if(pin.isEmpty()){
            DialogUtils.dialogPopUp("You did not enter a PIN, please enter one", "No input provided");
            return false;
        }
        for(int i = 0; i<pin.length();i++){
            Character currChar = pin.charAt(i);
            if(!Character.isDigit(currChar)){
                DialogUtils.dialogPopUp("Invalid PIN, you must type in only digits", "Retry PIN entry");
                return false;
            }
        }

        int userInput = Integer.parseInt(pin);
        if(userInput == realPin){
            DialogUtils.dialogPopUp("Correct Pin was entered, you may proceed", "Success");
            return true;
        }

        DialogUtils.dialogPopUp("Wrong Pin number, try again", "Invalid number entry");
        return false;
    }

    public static boolean validatePassword(String password){
        if(password.length() < 8){
            DialogUtils.dialogPopUp("Password is not allowed, must be at least 8 characters long", "Try again");
            return false;
        }
        int countOfDigits = 0;

        for(int i = 0; i<password.length(); i++){
            Character currChar = password.charAt(i);
            if(Character.isDigit(currChar)){
                countOfDigits++;
            }
            if(!Character.isDigit(currChar) && !Character.isLetter(currChar)){
                DialogUtils.dialogPopUp("Password can only be letters and digits", "Try again");
                return false;
            }
        }

        if(countOfDigits <=0){
            DialogUtils.dialogPopUp("Password must contain at least 1 digit", "Try again");
            return false;
        }

        return true;
    }



}
