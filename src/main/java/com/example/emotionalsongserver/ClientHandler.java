package com.example.emotionalsongserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;

/** 
The ClientHandler class takes care about the connection between the client and the query to a PostGreSql database,
adding the support of multithread

**/
public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private String url;
    private String username;
    private String password;
    /**
    class ClientHandler construct a new ClientHandler object
    @param clientSocker contains the client's socket used for the communication
    @param url contains the URL of the PostGreSql database
    @param username contains the username of the user
    @param password contains the password of the user
    @throws SQLException if there's an error during database connection
    **/
    public ClientHandler(Socket clientSocket, String url, String username, String password) throws SQLException {
        this.clientSocket = clientSocket;
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
    /**
    the class run starts the client handling process on a separate thread.
    this method will be executed when the thread is started 
    **/
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            // Configura la connessione al database PostgreSQL
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                System.out.println("Connessione al database stabilita.");

                String query;
                while ((query = in.readLine()) != null) {
                    if (query.equalsIgnoreCase("exit")) {
                        break;
                    }
                    try (PreparedStatement statement = connection.prepareStatement(query);
                         ResultSet resultSet = statement.executeQuery()) {

                        ResultSetMetaData rsmd = resultSet.getMetaData();
                        int columnCount = rsmd.getColumnCount();/*
                    // Invia il numero di colonne al client
                    out.println(columnCount);
                    // Invia i nomi delle colonne al client
                    for (int i = 1; i <= columnCount; i++) {
                        out.println(rsmd.getColumnName(i));}
                     */
                        while (resultSet.next()) {
                            StringBuilder result = new StringBuilder();
                            for (int i = 1; i <= columnCount; i++) {
                                result.append(resultSet.getString(i)).append("\t");
                            }
                            out.println(result.toString().trim());
                        }
                    } catch (SQLException ex) {
                        out.println("Errore nella query: " + ex.getMessage());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                in.close();
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
