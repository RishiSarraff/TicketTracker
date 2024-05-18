package com.example.ticketchecker.model;

import com.example.ticketchecker.database.DatabaseDriver;
import javafx.stage.Stage;

import java.sql.SQLException;

public class logInValidator {

    static DatabaseDriver db = DatabaseDriver.getInstance("TickCheckDB");

    static final int realPin = 115647;

    public static boolean validateUser(int newOrOldUser, String username, String password, Stage currStage){
        try{
            if(!db.isConnected) {
                db.connect();
            }
            db.createUserTable();

            if(!validatePassword(password, currStage)){
                return false;
            }

            if(!db.findUser(username, password)){
                if(newOrOldUser == 0) {
                    db.insertUser(username, password);
                    db.commit();
                    DialogUtils.dialogPopUp("Created account successfully", "Please proceed", currStage);
                    return true;
                }
                else{
                    DialogUtils.dialogPopUp("Username or password is wrong, please retry", "Retry information entry", currStage);
                    return false;
                }
            }
            else if(db.findUser(username, password) && newOrOldUser == 0){
                DialogUtils.dialogPopUp("User already exists, please press on old user", "Success", currStage);
                return true;
            }
            else if(db.findUser(username, password) && newOrOldUser == 1){
                DialogUtils.dialogPopUp("User input is valid, you will be now redirected", "Success", currStage);
                return true;
            }
            else{
                DialogUtils.dialogPopUp("User could not be found", "Please retry", currStage);
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean validatePIN(String pin, Stage currStage){
        pin = pin.trim();
        if(pin.isEmpty()){
            DialogUtils.dialogPopUp("You did not enter a PIN, please enter one", "No input provided", currStage);
            return false;
        }
        for(int i = 0; i<pin.length();i++){
            Character currChar = pin.charAt(i);
            if(!Character.isDigit(currChar)){
                DialogUtils.dialogPopUp("Invalid PIN, you must type in only digits", "Retry PIN entry", currStage);
                return false;
            }
        }

        int userInput = Integer.parseInt(pin);
        if(userInput == realPin){
            DialogUtils.dialogPopUp("Correct Pin was entered, you may proceed", "Success", currStage);
            return true;
        }

        DialogUtils.dialogPopUp("Wrong Pin number, try again", "Invalid number entry", currStage);
        return false;
    }

    public static boolean validatePassword(String password, Stage currStage){
        if(password.length() < 8){
            DialogUtils.dialogPopUp("Password is not allowed, must be at least 8 characters long", "Try again", currStage);
            return false;
        }
        int countOfDigits = 0;

        for(int i = 0; i<password.length(); i++){
            Character currChar = password.charAt(i);
            if(Character.isDigit(currChar)){
                countOfDigits++;
            }
            if(!Character.isDigit(currChar) && !Character.isLetter(currChar)){
                DialogUtils.dialogPopUp("Password can only be letters and digits", "Try again", currStage);
                return false;
            }
        }

        if(countOfDigits <=0){
            DialogUtils.dialogPopUp("Password must contain at least 1 digit", "Try again", currStage);
            return false;
        }

        return true;
    }



}
