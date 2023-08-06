package com.example.emotionalsongserver;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class  DatabaseManager extends HelloController{

    public static void createTable(String url, String user, String password, String query) {
        try {
            // Connessione al database
            Connection conn = DriverManager.getConnection(url, user, password);
            // Creazione della tabella
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            statement.close();
            // Chiude la connessione al database
            conn.close();
            System.out.println("Tabella creata con successo!");
        } catch (SQLException e) {
            System.err.println("Errore durante la creazione della tabella:");
            e.printStackTrace();
        }
    }
    public static void createDatabase(String url, String user, String password, String databaseName) {
        try {
            // Registra il driver JDBC per PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Connessione al database
            Connection conn = DriverManager.getConnection(url, user, password);

            // Creazione del database
            String createDBQuery = "CREATE DATABASE " + databaseName;
            Statement statement = conn.createStatement();
            statement.executeUpdate(createDBQuery);
            statement.close();

            // Chiude la connessione al database
            conn.close();

            System.out.println("Database creato con successo!");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC non trovato. Assicurati di avere il driver PostgreSQL nel tuo classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Errore durante la creazione del database:");
            e.printStackTrace();
        }}
    public static boolean CheckDatabase(String url, String user, String password, String dbName ) {
        try {
            // Connessione al database
            Connection connection = DriverManager.getConnection(url, user, password);
            // Query per verificare se il database esiste già
            String checkDbQuery = "SELECT datname FROM pg_catalog.pg_database WHERE datname = '" + dbName + "';";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(checkDbQuery);
            // Verifica se il database è già presente
            if (resultSet.next()) {
                System.out.println("Il database " + dbName + " esiste già.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Errore durante la connessione al database: " + e.getMessage());
        }
        return false;
    }

    private static void open() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DatabaseManager.class.getResource("progressbar.fxml"));
            Parent root1;
            Stage stage;
            root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void LoadTable(String url, String user, String password, Label l) {


        String s = Thread.currentThread().getName();
        System.out.println(s);


        try {
            BufferedReader bw = new BufferedReader(new FileReader("C:\\Users\\gabry\\Desktop\\EmotionalSong\\src\\main\\java\\com\\example\\emotionalsongserver\\canzoni.txt"));
            String line;
            int i=0;
            int perc = 0;
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stm = conn.createStatement();
            System.out.println(perc+"% completato");
            while ((line = bw.readLine())!=null){
                String[] strpart = line.split("<SEP>");
                String anno  = strpart[0];
                String id = strpart[1];
                String canzone = strpart[2];
                String artista = strpart[3];
                String query = "INSERT INTO canzoni VALUES('"+id+"', '"+canzone+"', '"+artista+"', "+anno+")";
                stm.executeUpdate(query);
                i++;
                if (i==5155){
                    i=0;
                    perc++;
                    final int pprint = perc;
                    System.out.println(perc+"% completato");
                    Platform.runLater(() ->{
                        l.setText(pprint+"% completato");
                    });

                }
            }
            System.out.println("Inserimento Completato");
            conn.close();
        }catch (FileNotFoundException e){
            System.err.println("File non trovato");
        }catch (Exception e){
            System.err.println(e.toString());
        }
    }

}