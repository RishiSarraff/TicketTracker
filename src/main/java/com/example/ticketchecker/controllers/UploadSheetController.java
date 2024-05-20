package com.example.ticketchecker.controllers;

import javafx.stage.Stage;

public class UploadSheetController implements SceneController{

    private UploadSheetController usc;
    private Stage currStage;

    public void initialize(){
        usc = new UploadSheetController();

    }

    public void setCurrentStage(Stage stage){
        this.currStage = stage;
    }
}
