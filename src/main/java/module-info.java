module com.example.serverstartemotionalsong {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires json.simple;


    opens com.example.serverstartemotionalsong to javafx.fxml;
    exports com.example.serverstartemotionalsong;
}