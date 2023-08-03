package com.example.serverstartemotionalsong;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.OutputStream;
import java.io.PrintStream;

public class ConsoleToLabelJavaFX extends Application {
    private Label logLabel;

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
    private class CustomOutputStream extends OutputStream {
        @Override
        public void write(int b) {
            // Aggiorna il testo della Label esistente
            String text = logLabel.getText() + String.valueOf((char) b);
            logLabel.setText(text);
        }
    }
}
