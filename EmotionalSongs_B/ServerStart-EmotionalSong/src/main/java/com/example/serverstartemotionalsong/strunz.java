package com.example.serverstartemotionalsong;
import java.io.*;
import java.net.Socket;
//CLIENT
public class strunz {
    public static void main(String[] args) {
        try {

            Socket socket = new Socket("localhost", 2000); // Indirizzo IP e porta del server

            // Prepara gli oggetti per la comunicazione con il server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String query;

            System.out.println("Inserisci una query SQL (o 'exit' per uscire):");

            while (true) {
                query = consoleReader.readLine();

                if (query.equalsIgnoreCase("exit")) {
                    break;
                }

                // Invia la query al server
                out.println(query);

                // Leggi e stampa i risultati della query
                String result;
                while ((result = in.readLine()) != null) {
                    System.out.println(result);
                }

                System.out.println("Inserisci una nuova query:");
            }

            // Chiudi le risorse
            consoleReader.close();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
