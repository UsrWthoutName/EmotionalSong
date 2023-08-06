package com.example.emotionalsongserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/** 
 * The class DeleteAllDatabase is an utility class which provide to delete all databases in a PostGreSQL server,
 * except the template database
**/
public class DeleteAllDatabases {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/";
        String user = "postgres";
        String password = "1a2b3c";

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            Statement statement = connection.createStatement();

            // Ottieni la lista di database
            String getDatabasesQuery = "SELECT datname FROM pg_database WHERE datistemplate = false;";
            statement.execute(getDatabasesQuery);
            // Esegui una query per eliminare ogni database
            while (statement.getResultSet().next()) {
                String dbName = statement.getResultSet().getString(1);
                String deleteDatabaseQuery = "DROP DATABASE IF EXISTS " + dbName + ";";
                statement.execute(deleteDatabaseQuery);
                System.out.println("Database eliminato: " + dbName);
            }

            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
