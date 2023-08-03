package com.example.serverstartemotionalsong;
import java.io.*;
import java.net.Socket;
import java.sql.*;
public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private String url;
    private String username;
    private String password;
    public ClientHandler(Socket clientSocket, String url, String username, String password) throws SQLException {
        this.clientSocket = clientSocket;
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
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
