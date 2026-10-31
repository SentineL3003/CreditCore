package com.example.creditcore;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class AddContractController {
    @FXML private TextField clientField;;
    @FXML private TextField requestField;
    @FXML private DatePicker beginDatePicker;
    @FXML private TextField amountField;
    @FXML private TextField percentField;
    @FXML private ChoiceBox<String> repaymentScheduleBox;
    @FXML private DatePicker endDatePicker;
    @FXML private ChoiceBox<String> statusBox;
    @FXML private Button cancelButton;

    @FXML
    private void initialize() {
        repaymentScheduleBox.getItems().addAll("Фиксированная", "Аннуитетная", "Дифференцированная");
    }

    @FXML
    private void onSave() {
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall("{call dbo.Добавить_договор(?, ?, ?, ?, ?, ?, ?)}")) {

            cs.setInt(1, Integer.parseInt(clientField.getText().trim()));
            cs.setInt(2, Integer.parseInt(requestField.getText().trim()));

            LocalDate beginDate = beginDatePicker.getValue();
            if (beginDate == null) cs.setNull(3, Types.DATE);
            else cs.setDate(3, java.sql.Date.valueOf(beginDate));

            cs.setBigDecimal(4, new BigDecimal(amountField.getText().trim()));
            cs.setBigDecimal(5, new BigDecimal(percentField.getText().trim()));
            cs.setString(6, repaymentScheduleBox.getValue());

            LocalDate endDate = endDatePicker.getValue();
            if (endDate == null) cs.setNull(7, Types.DATE);
            else cs.setDate(7, java.sql.Date.valueOf(endDate));

            cs.setString(8, "На рассмотрении");

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
