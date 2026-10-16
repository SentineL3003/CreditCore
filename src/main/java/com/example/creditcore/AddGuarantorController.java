package com.example.creditcore;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AddGuarantorController {
    @FXML private TextField fullNameField;
    @FXML private TextField passportField;
    @FXML private TextField contactField;
    @FXML private Button cancelButton;

    @FXML
    private void onSave() {
        try (Connection conn = Database.getConnection();
            CallableStatement cs = conn.prepareCall("{call dbo.Добавить_поручителя(?, ?, ?)}")) {

            cs.setString(1, fullNameField.getText());
            cs.setString(2, passportField.getText());
            cs.setString(3, contactField.getText());

            cs.execute();
            onCancel();
        }

        catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void onCancel() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}
