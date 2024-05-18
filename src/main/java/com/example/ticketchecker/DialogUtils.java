package com.example.ticketchecker;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

public class DialogUtils {

    static void dialogPopUp(String alert, String typeOfAlert, Stage currStage){
        DialogPane dp = new DialogPane();
        dp.setHeaderText(null);
        dp.setContentText(alert);

        ButtonType exit = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
        dp.getButtonTypes().add(exit);

        Dialog<String> dLog = new Dialog<>();
        dLog.setDialogPane(dp);
        dLog.setTitle(typeOfAlert);

        dLog.initOwner(currStage);

        dLog.showAndWait();
    }
}
