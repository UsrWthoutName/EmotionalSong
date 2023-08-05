package com.example.serverstartemotionalsong;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ServerManager {
    private static ExecutorService executor;
    private static ServerSocket serverSocket;
    public static void executor(String ipAddress,int PortNumber ,String url,String usr, String pass){
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> startServer(ipAddress,PortNumber,url,usr,pass));
    }
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

    public static void StopServer(){
        if (serverSocket != null) {
            try {serverSocket.close();
                System.out.println("server offline");
            } catch (IOException e) {
                e.printStackTrace();
            }}if (executor != null) {
            executor.shutdownNow();}}




}
