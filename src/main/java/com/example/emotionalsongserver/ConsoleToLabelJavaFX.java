package com.example.emotionalsongserver;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.OutputStream;
/**
The class ConsoleToLabelJavaFX redirect the console Output on an existing Label
**/
public class ConsoleToLabelJavaFX extends Application {
    private Label logLabel;
    /** 
    the start method contains the main entry point for the JavaFX application

    @param primaryStage contains the first window scene where is set the application
    **/
    @Override
    public void start(Stage primaryStage) {

        AnchorPane root = new AnchorPane(logLabel);
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Console to Label in JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Redireziona l'output della console sulla Label esistente


        // Esempio: Stampa qualcosa sulla console
        System.out.println("Hello, mondo!");
        System.out.println("Questo testo apparirà nella Label.");
    }

    // Classe personalizzata che estende OutputStream
    /**
    CustomOutputStream is a custom class that extends OutputStream for redirecting the console
    output to the Label
    **/
    private class CustomOutputStream extends OutputStream {
        /** 
        Write is a method that write an int value to the output stream

        @param b is the int value which has to be written
        **/
        @Override
        public void write(int b) {
            // Aggiorna il testo della Label esistente
            String text = logLabel.getText() + String.valueOf((char) b);
            logLabel.setText(text);
        }
    }
}
