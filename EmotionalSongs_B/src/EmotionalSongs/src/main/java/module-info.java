module com.example.emotionalsongs {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.emotionalsongs to javafx.fxml;
    exports com.example.emotionalsongs;
}