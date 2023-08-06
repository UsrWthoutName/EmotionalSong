package com.example.emotionalsongserver;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The ServerManager class is responsible for managing the server and handling client connections,utilizing JavaFX animations and concurrent tasks to manage server operations.
 **/
public class ServerManager {
    private static ExecutorService executor;
    private static ServerSocket serverSocket;
      /**
     * The method executor starts the server on the specified IP address and port number using the given database connection parameters.
     * This method sets up an executor and runs the server in a separate thread to accept client connections.
     *
     * @param ipAddress The IP address on which the server should listen.
     * @param portNumber The port number to bind the server.
     * @param url The URL of the database to establish a connection.
     * @param usr The username for the database connection.
     * @param pass The password for the database connection.
     **/
    public static void executor(String ipAddress,int PortNumber ,String url,String usr, String pass){
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> startServer(ipAddress,PortNumber,url,usr,pass));
    }
     /**
     * The StartServer method starts the server on the specified IP address and port number using the given database connection parameters.
     * This method binds the server to the specified IP and port, accepts client connections, and handles them in separate threads.
     *
     * @param ip contains the IP address on which the server should listen.
     * @param porta contains the port number to bind the server.
     * @param url contains the URL of the database to establish a connection.
     * @param usr contains the username for the database connection.
     * @param pass contains the password for the database connection.
     */
    public static void startServer(String ip, int porta, String url, String usr, String pass) {
        try {String specificIpAddress = ip;
            InetAddress ipAddress = InetAddress.getByName(specificIpAddress);
            int port = porta;
            serverSocket = new ServerSocket(port, 0, ipAddress);
            System.out.println("Server avviato su " + ipAddress + ":" + port + ". In attesa di connessioni...");
            ExecutorService executor = Executors.newCachedThreadPool();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuova connessione accettata da: " + clientSocket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket,url,usr,pass);
                executor.submit(clientHandler);}} catch (IOException | SQLException e) {e.printStackTrace();}}
     /**
     * The StopServer method stops the server and shuts down the executor, closing all client connections.
     * This method should be called when you want to stop the server and release its resources.
     **/
    public static void StopServer(){
        if (serverSocket != null) {
            try {serverSocket.close();
                System.out.println("server offline");
            } catch (IOException e) {
                e.printStackTrace();
            }}if (executor != null) {
            executor.shutdownNow();}}
}
