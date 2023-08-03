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


    public void StartSocketServer(){
        String ipAddress=GetIpAddress.getText();
        String PortNumber=GetPort.getText();
        String url=GetJdbcUrl.getText();
        String usr=GetPostgresUsername.getText();
        String pass=GetPostgresPassword.getText();
        if(!ipAddress.isEmpty()||!GetPort.getText().isEmpty()||!url.isEmpty()||!usr.isEmpty()||!pass.isEmpty()){
            ServerManager.executor(ipAddress,Integer.parseInt(PortNumber),url,usr,pass);
            LogLabel.setText("server online on ip address: "+ipAddress+" and port:"+PortNumber);
        }
        else{
            LogLabel.setText("error");
        }

    }
    public void StopServer(){
        ServerManager.StopServer();
        LogLabel.setText("server offline");
    }

}