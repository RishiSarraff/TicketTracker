package com.example.ticketchecker.model;

import com.example.ticketchecker.database.DatabaseDriver;
import com.example.ticketchecker.model.smallFeatures.DialogUtils;
import com.google.api.client.util.Data;
import javafx.stage.Stage;

import java.sql.SQLException;

public class User {

    private static User instance;
    private String firstName;
    private String username;

    private String lastName;
    private String email;

    private Stage currStage;

    static DatabaseDriver db = DatabaseDriver.getInstance("TickCheckDB");

    public User(){}
    public User(String firstName, String lastName, String email){
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    public User getUserInstance(){
        if(instance == null){
            instance = new User();
        }
        return instance;
    }

    public void setUserDetails(String username, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
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

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
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
