package com.example.creditcore;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML private Label label;
    @FXML private ComboBox<String> roleBox;
    @FXML private TextField loginField;
    @FXML private PasswordField passField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;

    @FXML
    private void initialize() {
        roleBox.getItems().setAll("Кредитный специалист", "Оператор по платежам");
    }

    @FXML
    protected void login() throws IOException {
        openMainWindow();
    }

    private void openMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent root = loader.load();

        MainController main = loader.getController();
        //main.setRole(role);

        Stage mainStage = new Stage();
        mainStage.setTitle("CreditCore");
        mainStage.setScene(new Scene(root));
        mainStage.show();

        ((Stage) loginButton.getScene().getWindow()).close();
    }

    @FXML
    private void exit() {
        Platform.exit();
    }
}