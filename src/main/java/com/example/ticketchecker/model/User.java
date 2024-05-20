package com.example.ticketchecker.model;

import com.example.ticketchecker.database.DatabaseDriver;
import com.example.ticketchecker.model.smallFeatures.DialogUtils;
import com.google.api.client.util.Data;
import javafx.stage.Stage;

import java.sql.SQLException;

public class User {

    private String firstName;
    private String lastName;

    private Stage currStage;

    static DatabaseDriver db = DatabaseDriver.getInstance("TickCheckDB");

    public User(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCurrStage(Stage currStage){
        this.currStage = currStage;
    }

    public String getUserAccess(){
        try{
            if(!db.isConnected){
                db.connect();
            }

            String ans = db.getPermissionLevel(this.firstName, this.lastName);

            if(ans == null || ans.isEmpty()){
                DialogUtils.dialogPopUp("First or last name may be incorrect", "User doesn't have access", currStage);
                return null;
            }
            return ans;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
