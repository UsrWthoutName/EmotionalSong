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
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The DatabaseManager class provides methods for managing a database, 
 * creating tables and databases, and loading data into the database.
 * It extends the HelloController class.
 **/
public class  DatabaseManager extends HelloController{

    /** 
    * CreateTable method create a table in the database specified by an url, an username, a password 
    * using the given query
    * 
    * @param url is the url of the database
    * @param user is the username for the database connection
    * @param password is the password for the database connection
    * @param query is the SQL query which create the table.
    **/
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
    /** 
    * the method createDatabase creates a new database with the specified name in the given database server.
    * @param url contains the URL of the database server.
    * @param user contains the username for the database connection.
    * @param password contains the password for the database connection.
    * @param databaseName contains the name of the new database to create.
    */
    **/
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
    /** 
    * Check if a database with the specified name exists in the given database server
    * 
     * @param url     The URL of the database server.
     * @param user    The username for the database connection.
     * @param password The password for the database connection.
     * @param dbName  The name of the database to check.
     * @return true if the database exists; otherwise, false.
    **/
    
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
    /** 
     * the method LoadTable loads data from a file into a table in the database.
     *
     * @param url contains the URL of the database.
     * @param user contains the username for the database connection.
     * @param password The password for the database connection.
     * @param l is the label to display the progress of data loading.
     * @param url2 contains URL of the server where the data is loaded from.
     * @param ip contains the IP address of the server.
     * @param port contains the port number of the server.
     * @param progress contains the progress bar to show the loading progress.
     */**/
    public static void LoadTable(String url, String user, String password, Label l,String url2,String ip,String port,ProgressBar progress) {
        String s = Thread.currentThread().getName();
        System.out.println(s);
        try {
            BufferedReader bw = new BufferedReader(new FileReader("canzoni.txt"));
            String line;
            int i=0;
            int perc = 0;
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stm = conn.createStatement();
            System.out.println(perc+"% completato");
            progress.setVisible(true);
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
            conn.close();
            Platform.runLater(() ->{
                Thread.currentThread().interrupt();
                ServerManager.StopServer();
                ServerManager.executor(ip, Integer.parseInt(port),url2,user,password);
                l.setText("server online on ip address: " + ip + " and port:" + port);
                progress.setVisible(false);
            });


        }catch (FileNotFoundException e){
            System.err.println("File non trovato");
        }catch (Exception e){
            System.err.println(e.toString());
        }
    }

}
