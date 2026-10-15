package com.example.creditcore;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
    private void onSave() {}

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
