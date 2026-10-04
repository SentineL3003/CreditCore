module com.example.creditcore {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.creditcore to javafx.fxml;
    exports com.example.creditcore;
}