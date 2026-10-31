package com.example.creditcore;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;


public class AddRequestController {
    @FXML private TextField clientField;;
    @FXML private TextField employeeField;
    @FXML private DatePicker submitDatePicker;
    @FXML private TextField amountField;
    @FXML private TextField termField;
    @FXML private TextField purposeField;
    @FXML private Button cancelButton;

    @FXML
    private void onSave() {
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall("{call dbo.Добавить_заявку(?, ?, ?, ?, ?, ?, ?)}")) {

            cs.setInt(1, Integer.parseInt(clientField.getText().trim()));

            LocalDate date = submitDatePicker.getValue();
            if (date == null) cs.setNull(2, Types.DATE);
            else cs.setDate(2, java.sql.Date.valueOf(date));

            cs.setInt(3, Integer.parseInt(employeeField.getText().trim()));
            cs.setBigDecimal(4, new BigDecimal(amountField.getText().trim()));
            cs.setInt(5, Integer.parseInt(termField.getText().trim()));
            cs.setString(6, purposeField.getText().trim());
            cs.setString(7, "На рассмотрении");

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