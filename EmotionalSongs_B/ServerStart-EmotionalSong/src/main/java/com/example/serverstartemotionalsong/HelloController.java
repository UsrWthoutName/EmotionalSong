package com.example.serverstartemotionalsong;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloController{
    private TextArea outputTextArea = new TextArea();
    @FXML
    public TextField GetIpAddress;
    @FXML
    public TextField GetPort;
    @FXML
    public TextField GetJdbcUrl;
    @FXML
    public TextField GetPostgresUsername;
    @FXML
    public TextField GetPostgresPassword;
    @FXML
    public Label LogLabel;
    @FXML
    public Button StartButton;
    @FXML
    public ImageView GifImg;
    public String queryUtenti="CREATE TABLE utentiregistrati(id SERIAL PRIMARY KEY,nome VARCHAR,cognome VARCHAR,datanascita DATE,cfiscale VARCHAR(16),password VARCHAR)";
    public String queryCanzoni="CREATE TABLE canzoni(id VARCHAR PRIMARY KEY,titolo VARCHAR,autore VARCHAR,anno INT)";
    public String queryPlaylist="CREATE TABLE playlist(id SERIAL PRIMARY KEY,nome VARCHAR,possessore INT REFERENCES utentiregistrati(id))";
    public String queryEmozioni="CREATE TABLE emozioni(idplaylist int,idcanzone VARCHAR REFERENCES canzoni(id),Amazement int,Solemnity int,Tenderness int,Nostalgia int,Calmness int,Power int,Joy int,Tension int,Sadness int,Amazement_N VARCHAR,Solemnity_N VARCHAR,Tenderness_N VARCHAR,Nostalgia_N VARCHAR,Calmness_N VARCHAR,Power_N VARCHAR,Joy_N VARCHAR,Tension_N VARCHAR,Sadness_N VARCHAR,  PRIMARY KEY(idplaylist, idcanzone))";

    public void StartSocketServer(){
        String ipAddress=GetIpAddress.getText();
        String PortNumber=GetPort.getText();
        String url="jdbc:postgresql://"+GetJdbcUrl.getText()+"/";
        String usr=GetPostgresUsername.getText();
        String pass=GetPostgresPassword.getText();
        String dbname="marungia22";
        boolean db=DatabaseManager.CheckDatabase(url,usr,pass,dbname);

        if(!ipAddress.isEmpty()||!GetPort.getText().isEmpty()||!url.isEmpty()||!usr.isEmpty()||!pass.isEmpty()){
            if(!db){
                DatabaseManager.createDatabase2(url,usr,pass,dbname);
                DatabaseManager.createTable2(url+dbname,usr,pass,queryCanzoni);
                DatabaseManager.createTable2(url+dbname,usr,pass,queryUtenti);
                DatabaseManager.createTable2(url+dbname,usr,pass,queryPlaylist);
                DatabaseManager.createTable2(url+dbname,usr,pass,queryEmozioni);
                String urlKK=url+dbname;
                DatabaseManager.LoadTable(urlKK,usr,pass);
            }else{
                ServerManager.executor(ipAddress,Integer.parseInt(PortNumber),url,usr,pass);
                LogLabel.setText("server online on ip address: "+ipAddress+" and port:"+PortNumber);
            }}
        else{
            LogLabel.setText("error");
        }
    }
    public void StopServer(){
        ServerManager.StopServer();
        LogLabel.setText("server offline");
    }
}