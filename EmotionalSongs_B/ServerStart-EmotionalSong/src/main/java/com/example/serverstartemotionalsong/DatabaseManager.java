package com.example.serverstartemotionalsong;


import java.io.*;
import java.sql.*;


public class  DatabaseManager {

    public static void createDatabase(String url, String user, String password, String dbName) {
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
            } else {// Il database non è presente, quindi crea il database
                String createDbQuery = "CREATE DATABASE " + dbName + ";";
                int rowsAffected = statement.executeUpdate(createDbQuery);
                if (rowsAffected > 0) {
                    System.out.println("Il database " + dbName + " è stato creato con successo.");
                } else {System.out.println("Errore durante la creazione del database " + dbName + ".");
                }}// Chiusura delle risorseresultSet.close();statement.close();connection.close();
        } catch (SQLException e) {System.out.println("Errore durante la connessione al database: " + e.getMessage());}}

    public static void createTable(String url, String user, String password, String tableName,String query) {
        try {// Connessione al database
            Connection connection = DriverManager.getConnection(url, user, password);
            // Query per verificare se la tabella esiste già
            String checkTableQuery = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_name = '" + tableName + "';";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(checkTableQuery);
            // Verifica se la tabella è già presente
            if (resultSet.next()) {
                System.out.println("La tabella " + tableName + " esiste già.");
            } else {
                // La tabella non è presente, quindi crea la tabella
                int rowsAffected = statement.executeUpdate(query);
                if (rowsAffected > 0) {
                    System.out.println("La tabella " + tableName + " è stata creata con successo.");
                } else {
                    System.out.println("Errore durante la creazione della tabella " + tableName + ".");
                }}
            // Chiusura delle risorse
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Errore durante la connessione al database: " + e.getMessage());
        }
    }

    public static boolean CheckTable(String url, String user, String password, String tableName){
        try {// Connessione al database
            Connection connection = DriverManager.getConnection(url, user, password);
            // Query per verificare se la tabella esiste già
            String checkTableQuery = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_name = '" + tableName + "';";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(checkTableQuery);
            // Verifica se la tabella è già presente
            if (resultSet.next()) {
               return true;
            }
        } catch (SQLException e) {
            System.out.println("Errore durante la connessione al database: " + e.getMessage());
        }
        return false;

    }

    //CARICAMENTO TABELLA CANZONI
    public static void LoadTable(String url, String user, String password) {
        try {
            BufferedReader bw = new BufferedReader(new FileReader("C:\\Users\\gabry\\Desktop\\ServerStart-EmotionalSong\\src\\main\\java\\com\\example\\serverstartemotionalsong\\canzoni.txt"));
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
                    System.out.println(perc+"% completato");
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
