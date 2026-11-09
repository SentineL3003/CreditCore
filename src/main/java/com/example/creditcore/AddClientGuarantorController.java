package com.example.creditcore;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AddClientGuarantorController {
    @FXML private TextField clientIdField;
    @FXML private TextField guarIdField;
    @FXML private Button cancelButton;

    private void onSave() {
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall("{call dbo.Добавить_поручителя_клиенту(?, ?)}")) {

            cs.setInt(1, Integer.parseInt(clientIdField.getText().trim()));
            cs.setInt(2, Integer.parseInt(guarIdField.getText().trim()));

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
