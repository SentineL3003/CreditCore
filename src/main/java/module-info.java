module com.example.creditcore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.base;


    opens com.example.creditcore to javafx.fxml;
    exports com.example.creditcore;
}