package com.example.ticketchecker;

import com.example.ticketchecker.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application{


        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(com.example.ticketchecker.MainApplication.class.getResource("TicketCheckWelcomePage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            stage.setTitle("Welcome!");
            stage.setScene(scene);

            MainController controller = fxmlLoader.getController();
            controller.setCurrentStage(stage);

            stage.show();
        }

        public static void main(String[] args) {
            launch();
        }
}

