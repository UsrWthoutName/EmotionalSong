package com.example.emotionalsongserver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The HelloApplication class represents a JavaFX application which uses a GUI, loading the "ServerStart.fxml" file, using FXMLLoader and setting up the GUI scene on the stage.
 **/
public class HelloApplication extends Application {
    @Override
     /**
     * The start method  loads the "ServerStart.fxml" file, creates and sets the scene on the stage, and displays it.
     *
     * @param stage is the primary stage for the application.
     * @throws IOException if an I/O error occurs while loading the FXML file.
     **/
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ServerStart.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 651, 605);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
