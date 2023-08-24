package com.example.emotionalsongserver;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    @param clientSocket contains the client's socket used for the communication
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
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
           // ObjectOutputStream objout = new ObjectOutputStream(clientSocket.getOutputStream());
            BufferedReader in =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String op = in.readLine();
            //CLIENT INVIA UNO DEI SEGUENTI CODICI CHE IDENTIFICANO LA RICHIESTA
            String s;
            String[] cont;
            String q;
            switch (op){
                case "L":   //LOGIN
                        out.println("C");           //conferma ricezione comando
                        s = in.readLine();          //riceve informazioni in formato CREDENZIALE~PASSWORD
                        cont = s.split("~");
                        String cred = cont[0];
                        String pw = cont[1];
                        q = "SELECT username, id FROM utentiregistrati WHERE password='"+pw+"' AND id IN(SELECT id FROM utentiregistrati WHERE email = '"+cred+"' OR username='"+cred+"')";
                        try {
                            Connection conn = DriverManager.getConnection(url, username, password);
                            Statement statement = conn.createStatement();
                            ResultSet res= statement.executeQuery(q);
                            if (res.next()){
                                out.println(res.getString("username")+"~"+res.getString("id"));
                            }
                            else {
                                out.println("-1");  //no corrispondenza credenziale-password
                            }
                            conn.close();
                            System.out.println("login terminato");
                        }catch (Exception e){}
                    break;
                case "R":   //REGISTRAZIONE
                        out.println("C");
                        s = in.readLine();
                        cont = s.split("~");
                        //formato NOME~COGNOME~EMAIL~USERNAME~PASSWORD~CODICEFISCALE~INDIRIZZO~DATADINASCITA
                        try {
                            Connection conn = DriverManager.getConnection(url, username, password);
                            Statement statement = conn.createStatement();
                            q = "SELECT COUNT(id) FROM utentiregistrati WHERE email='"+cont[2]+"' OR username = '"+cont[3]+"'";
                            ResultSet res = statement.executeQuery(q);
                            res.next();

                            if (!res.getString("COUNT").equals("0")){
                                System.err.println("utente esiste già");
                                out.println("-1");
                            }
                            else {
                                q="INSERT INTO utentiregistrati (nome, cognome, datanascita, cfiscale, password, username, email, indirizzo) VALUES('"+cont[0]+"', '"+cont[1]+"', '"+cont[7]+"', '"+cont[5]+"', '"+cont[4]+"', '"+cont[3]+"', '"+cont[2]+"', '"+cont[6]+"')";
                                statement.executeUpdate(q);
                                q = "SELECT id FROM utentiregistrati WHERE username='"+cont[3]+"'";
                                res = statement.executeQuery(q);
                                res.next();
                                out.println();
                            }
                            conn.close();
                        }catch (Exception e){}
                    break;
                case "C":   //CANZONE
                        out.println("C");
                        s = in.readLine();
                        try {
                            Connection connection = DriverManager.getConnection(url, username, password);
                            Statement statement = connection.createStatement();
                            q = "SELECT AVG(amazement) as amAVG, AVG(solemnity) as solAVG, AVG(tenderness) as tendAVG, AVG(nostalgia) as nosAVG, AVG(calmness) as calAVG, AVG(power) as powAVG, AVG(joy) as joyAVG, AVG(tension) as tensAVG, AVG(sadness) as sadAVG FROM emozioni WHERE idcanzone = '"+s+"' AND valutata = TRUE";
                            ResultSet res = statement.executeQuery(q);
                            res.next();
                            //AMAZEMENT~SOLEMNITY~TENDERNESS~NOSTALGIA~CALMNESS~POWER~JOY~TENSION~SADNESS
                            String srvres = res.getString("amAVG")+"~"+res.getString("solAVG")+"~"+res.getString("tendAVG")+"~"+res.getString("nosAVG")+"~"+res.getString("calAVG")+"~"+res.getString("powAVG")+"~"+res.getString("joyAVG")+"~"+res.getString("tensAVG")+"~"+ res.getString("sadAVG");
                            System.out.println(srvres);
                            out.println(srvres);

                            int c = 0;
                            String[] em = {"amazement_n","solemnity_n", "tenderness_n", "nostalgia_n", "calmness_n", "power_n", "joy_n", "tension_n", "sadness_n" };

                            srvres = "";
                            while (c<9){
                                q = "SELECT "+em[c]+" FROM emozioni WHERE idcanzone = '"+s+"' AND "+em[c]+" IS NOT NULL";
                                res = statement.executeQuery(q);
                                while (res.next()){
                                    srvres = srvres+em[c]+"~"+res.getString(em[c])+"~";
                                }
                                c++;
                            }
                            if (srvres.equals("")){
                                out.println("-1");

                            }
                            else{
                                srvres = srvres.substring(0, srvres.length()-1);
                                System.out.println(srvres);
                                //EMOZIONE_N~RECENSIONE~...
                                out.println(srvres);
                            }


                        }catch (Exception e){
                            System.err.println(e);
                        }
                    break;
                case "S":   //RICERCA CANZONI
                        out.println("C");
                        s = in.readLine();
                        s = s.replace("'", "''");
                        try {
                            Connection conn = DriverManager.getConnection(url, username, password);
                            Statement statement = conn.createStatement();
                            q = "SELECT * FROM canzoni WHERE titolo = '"+s+"' OR autore = '"+s+"'";
                            ResultSet res =statement.executeQuery(q);

                            //POSSIBILE AGGIUNGERE CANZONI CONSIGLIATE
                            //BASATE SU SINGOLE PAROLE DELLA STRINGA DI RICERCA

                            String srvres = "";
                            //ID~TITOLO~AUTORE~ANNO
                            if (!res.next()){
                                out.println("-1");
                            }else {
                                srvres=res.getString("id")+"~"+res.getString("titolo")+"~"+res.getString("autore")+"~"+res.getString("anno")+"~";
                                while (res.next()){
                                    srvres=srvres+res.getString("id")+"~"+res.getString("titolo")+"~"+res.getString("autore")+"~"+res.getString("anno")+"~";
                                }
                                srvres =  srvres.substring(0, srvres.length()-1);
                                out.println(srvres);
                            }

                        }catch (Exception e){
                            System.err.println("non va un cazzo");
                        }
                    break;
                case "P":   //PLAYLIST
                        out.println("C");
                        s = in.readLine();  //s contiene ID utente
                        try {
                            Connection conn = DriverManager.getConnection(url, username, password);
                            Statement statement = conn.createStatement();
                            q="SELECT COUNT(id) FROM playlist WHERE possessore ='"+s+"'";   //NUMERO PLAYLIST CREATE DA UTENTE
                            ResultSet res = statement.executeQuery(q);
                            res.next();
                            String numEl = res.getString("COUNT");
                            out.println(numEl);
                            if (!numEl.equals("0")){
                                q="SELECT id, nome FROM playlist WHERE possessore = '"+s+"'";   //ELENCO PLAYLIST CREATE DA UTENTE
                                res= statement.executeQuery(q);
                                ArrayList<String> arrNm = new ArrayList<String>();
                                ArrayList<String> arrId = new ArrayList<String>();

                                while (res.next()){
                                    arrNm.add(res.getString("nome"));
                                    arrId.add(res.getString("id"));
                                }
                                int i=0;
                                //IDPLAYLIST~NOMEPLAYLIST~NUMEROCANZONI~IDPLAYLIST~NOMEPLAYLIST~NUMEROCANZONI~...
                                String srvresponse = "";
                                while (i<arrNm.size()){
                                    String idPlaylist = arrId.get(i);
                                    q="SELECT COUNT(*) FROM emozioni WHERE idplaylist = '"+idPlaylist+"'";  //NUMERO CANZONI IN OGNI PLAYLIST
                                    res= statement.executeQuery(q);
                                    res.next();
                                    String size = res.getString("COUNT");
                                    srvresponse = srvresponse+idPlaylist+"~"+arrNm.get(i)+"~"+size+"~";
                                    i++;
                                }
                                srvresponse =  srvresponse.substring(0, srvresponse.length()-1);
                                out.println(srvresponse);

                            }
                            conn.close();
                        }catch (Exception e){
                            System.err.println(e);
                        }
                    break;
                case "V":   //VALUTAZIONE
                    break;
                case "I":   //VISUALIZZAZIONE ELEMENTI IN PLAYLIST
                    break;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



    /*
    * vecchio metodo run
    * try {
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
                        int columnCount = rsmd.getColumnCount();
                        /*
                            Invia il numero di colonne al client
                            out.println(columnCount);
                            Invia i nomi delle colonne al client
                            for (int i = 1; i <= columnCount; i++) {
                                out.println(rsmd.getColumnName(i));}
                        *//*
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
    * */


}
