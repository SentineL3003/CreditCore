package com.example.creditcore;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ChangeRequestStatusController {
    @FXML private TextField requestField;
    @FXML private ChoiceBox<String> statusChoiceBox;
    @FXML private Button cancelButton;

    @FXML
    private void initialize() {
        statusChoiceBox.getItems().addAll("Одобрена", "Отклонена");
    }

    // Вызывается из SpecialistController до показа окна: клиент, выбранный в таблице
    public void setRequest(int clientId) {
        requestField.setText(String.valueOf(clientId));
        requestField.setEditable(false);   // уберите строку, если номер клиента можно менять
    }

    @FXML
    private void onSave() {
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall("{call dbo.Изменить_статус_заявки(?, ?)}")) {

            cs.setInt(1, Integer.parseInt(requestField.getText().trim()));
            cs.setString(2, statusChoiceBox.getValue());

            cs.execute();
            onCancel();


        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void onCancel() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }
}
