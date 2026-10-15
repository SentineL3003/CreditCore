package com.example.creditcore;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
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
        String login = loginField.getText().trim();
        String chosenRole = roleBox.getValue();

        if (chosenRole == null || login.isEmpty()) {
            errorLabel.setText("Выберите роль и введите логин");
            return;
        }

        String sql = "SELECT Номер_сотрудника, ФИО, Должность FROM Сотрудник WHERE Логин = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    errorLabel.setText("Пользователь не найден");
                    return;
                }
                String position = rs.getString("Должность");
                if (!position.equals(chosenRole)) {
                    errorLabel.setText("Роль не соответствует должности сотрудника");
                    return;
                }
                Session.start(rs.getInt("Номер_сотрудника"), rs.getString("ФИО"), position);
            }
            openMainWindow();

        } catch (SQLException | IOException e) {
            errorLabel.setText("Ошибка: " + e.getMessage());
        }


        openMainWindow();
    }

    private void openMainWindow() throws IOException {
        String fxml;
        if (Session.getPosition().equals("Кредитный специалист")) {
            fxml = "specialist-view.fxml";
        }
        else {
            fxml = "operator-view.fxml";
        }

        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        stage.setScene(new Scene(loader.load()));
        stage.centerOnScreen();
    }

    @FXML
    private void exit() {
        Platform.exit();
    }
}