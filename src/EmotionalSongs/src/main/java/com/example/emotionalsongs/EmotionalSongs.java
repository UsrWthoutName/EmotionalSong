package com.example.emotionalsongs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;



/**
 * EmotionalSongs class contains the main method for the initialization of the software
 * @author Edoardo Rossi, matricola 749089
 * @author Mattia Sindoni, matricola 750760
 * @author Gabriele Todeschini, matricola 750767
 * @author Matteo Argiolas, matricola 75125
 * @version 17
 * **/
public class EmotionalSongs extends Application {
    /**
     * start method starts the application
     * @throws IOException generate a new IOException
     * @see IOException
     * **/
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmotionalSongs.class.getResource("StartWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 800);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    /**
     * main method contains the launcher of the application
     * **/
    public static void main(String[] args) {
        launch();
    }
}