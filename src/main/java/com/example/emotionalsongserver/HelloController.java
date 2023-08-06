package com.example.emotionalsongserver;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The HelloController class is the controller for the main application view.
 * It manages user interactions and server operations related to the application
 */
public class HelloController implements Initializable{
    private final TextArea outputTextArea = new TextArea();
    @FXML
    public TextField GetIpAddress;
    @FXML
    public TextField GetPort;
    @FXML
    public TextField GetJdbcUrl;
    @FXML
    public  TextField GetPostgresUsername;
    @FXML
    public TextField GetPostgresPassword;
    @FXML
    public Label LogLabel;
    @FXML
    public Button StartButton;
    @FXML
    public ImageView GifImg;
    @FXML
    public ProgressBar progress;
    @FXML
    public CheckBox CBsave;

    public String queryUtenti="CREATE TABLE utentiregistrati(id SERIAL PRIMARY KEY,nome VARCHAR,cognome VARCHAR,datanascita DATE,cfiscale VARCHAR(16),password VARCHAR)";
    public String queryCanzoni="CREATE TABLE canzoni(id VARCHAR PRIMARY KEY,titolo VARCHAR,autore VARCHAR,anno INT)";
    public String queryPlaylist="CREATE TABLE playlist(id SERIAL PRIMARY KEY,nome VARCHAR,possessore INT REFERENCES utentiregistrati(id))";
    public String queryEmozioni="CREATE TABLE emozioni(idplaylist int,idcanzone VARCHAR REFERENCES canzoni(id),Amazement int,Solemnity int,Tenderness int,Nostalgia int,Calmness int,Power int,Joy int,Tension int,Sadness int,Amazement_N VARCHAR,Solemnity_N VARCHAR,Tenderness_N VARCHAR,Nostalgia_N VARCHAR,Calmness_N VARCHAR,Power_N VARCHAR,Joy_N VARCHAR,Tension_N VARCHAR,Sadness_N VARCHAR,  PRIMARY KEY(idplaylist, idcanzone))";

    /**
     * The method StartSocketServer start the socket server and manages database operations based on user inputs.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void StartSocketServer() throws IOException {
        String ipAddress = GetIpAddress.getText();
        String PortNumber = GetPort.getText();
        String url = "jdbc:postgresql://" + GetJdbcUrl.getText() + "/";
        String usr = GetPostgresUsername.getText();
        String pass = GetPostgresPassword.getText();
        String dbname = "db9";
        boolean db = DatabaseManager.CheckDatabase(url, usr, pass, dbname);
        if ((ipAddress.length() == 0) || (PortNumber.length() == 0) || (url.length() == 0) || (usr.length() == 0) || (pass.length() == 0)) {
            LogLabel.setText("error");
        } else {
            if (CBsave.isSelected()){
                FileWriter fw = new FileWriter("init.txt");
                String info = ipAddress+"\n"+PortNumber+"\n"+GetJdbcUrl.getText()+"\n"+usr+"\n"+pass;
                fw.write(info);
                fw.close();
            }
            if (!db) {
                DatabaseManager.createDatabase(url, usr, pass, dbname);
                DatabaseManager.createTable(url + dbname, usr, pass, queryCanzoni);
                DatabaseManager.createTable(url + dbname, usr, pass, queryUtenti);
                DatabaseManager.createTable(url + dbname, usr, pass, queryPlaylist);
                DatabaseManager.createTable(url + dbname, usr, pass, queryEmozioni);
                String urlKK = url + dbname;
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        DatabaseManager.LoadTable(urlKK, usr, pass, LogLabel,url,ipAddress,PortNumber,progress);
                        return null;}};
                Thread thread = new Thread(task);
                thread.start();
            } else {
                ServerManager.StopServer();
                ServerManager.executor(ipAddress, Integer.parseInt(PortNumber), url, usr, pass);
                LogLabel.setText("server online on ip address: " + ipAddress + " and port:" + PortNumber);
            }
        }
    }
    /**
     * The method stopServer stop the running socket server.
     */
    public void StopServer(){
        ServerManager.StopServer();
        LogLabel.setText("server offline");
    }
    /**
     * The method initialize initialize the controller by reading previous settings from a file.
     * @param url            contains the url location used to resolve relative paths for the root object.
     * @param resourceBundle contains the resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            BufferedReader br = new BufferedReader(new FileReader("init.txt"));
            GetIpAddress.setText(br.readLine());
            GetPort.setText(br.readLine());
            GetJdbcUrl.setText(br.readLine());
            GetPostgresUsername.setText(br.readLine());
            GetPostgresPassword.setText(br.readLine());

        }catch (IOException e){}
    }
}





























