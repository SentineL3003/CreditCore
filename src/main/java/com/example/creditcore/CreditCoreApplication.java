package com.example.creditcore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CreditCoreApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreditCoreApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 340, 420);
        stage.setTitle("CreditCore");
        stage.setScene(scene);
        stage.show();
    }
}