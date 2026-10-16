package com.example.creditcore;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

public class AddClientController {
    @FXML private TextField fullNameField;
    @FXML private TextField passportField;
    @FXML private TextField contactField;
    @FXML private DatePicker registrationDatePicker;
    @FXML private Spinner<Integer> creditRatingSpinner;
    @FXML private ChoiceBox<String> statusChoiceBox;
    @FXML private Button cancelButton;

    @FXML
    private void onSave() {
        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall("{call dbo.Добавить_клиента(?, ?, ?, ?, ?, ?)}")) {

            cs.setString(1, fullNameField.getText());
            cs.setString(2, passportField.getText());
            cs.setString(3, contactField.getText());

            LocalDate date = registrationDatePicker.getValue();
            if (date == null) cs.setNull(4, Types.DATE);
            else cs.setDate(4, java.sql.Date.valueOf(date));

            Integer rating = creditRatingSpinner.getValue();
            if (rating == null) cs.setNull(5, Types.SMALLINT);
            else cs.setInt(5, rating);

            cs.setString(6, statusChoiceBox.getValue());

            cs.execute();
            onCancel();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void initialize() {
        statusChoiceBox.getItems().addAll("Подал заявку", "Активный", "Должник", "Неактивный");
        statusChoiceBox.setValue("Подал заявку");

        creditRatingSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 50, 1));
    }

    @FXML
    private void onCancel() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}
