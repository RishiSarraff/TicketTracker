package com.example.ticketchecker.controllers;

import javafx.stage.Stage;

public class EmailSenderController implements SceneController{
    private EmailSenderController esc;
    private Stage currStage;

    public void initialize(){
        esc = new EmailSenderController();

    }

    public void setCurrentStage(Stage stage){
        this.currStage = stage;
    }
}
