module com.example.emotionalsongserver {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.emotionalsongserver to javafx.fxml;
    exports com.example.emotionalsongserver;
}