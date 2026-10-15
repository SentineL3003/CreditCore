package com.example.creditcore;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class OperatorController {
    //    ТАБЛИЦЫ
    // Клиент
    @FXML private TableView<Client> clientsTable;
    @FXML private TableColumn<Client, Integer> idColumn;
    @FXML private TableColumn<Client, String> fioColumn;
    @FXML private TableColumn<Client, String> passportColumn;
    @FXML private TableColumn<Client, String> contactColumn;
    @FXML private TableColumn<Client, LocalDate> dateColumn;
    @FXML private TableColumn<Client, Integer> ratingColumn;
    @FXML private TableColumn<Client, String> statusColumn;
    // Кредитный контракт
    @FXML private TableView<CreditContract> contractsTable;
    @FXML private TableColumn<CreditContract, Integer> contIdColumn;
    @FXML private TableColumn<CreditContract, LocalDate> contBeginDateColumn;
    @FXML private TableColumn<CreditContract, Integer> contRequestIdColumn;
    @FXML private TableColumn<CreditContract, BigDecimal> contAmountColumn;
    @FXML private TableColumn<CreditContract, BigDecimal> contPercentColumn;
    @FXML private TableColumn<CreditContract, String> contRepaymentScheduleColumn;
    @FXML private TableColumn<CreditContract, LocalDate> contEndDateColumnColumn;
    @FXML private TableColumn<CreditContract, String> contStatusColumnColumn;
    // График платежей
    @FXML private TableView<PaymentGrafic> graficTable;
    @FXML private TableColumn<PaymentGrafic, Integer> grafIdPaymentColumn;
    @FXML private TableColumn<PaymentGrafic, Integer> grafIdContractColumn;
    @FXML private TableColumn<PaymentGrafic, LocalDate> grafDatePlanColumn;
    @FXML private TableColumn<PaymentGrafic, BigDecimal> grafAmounPlanColumn;
    @FXML private TableColumn<PaymentGrafic, LocalDate> grafDateFactColumn;
    @FXML private TableColumn<PaymentGrafic, BigDecimal> grafAmounFactColumn;
    @FXML private TableColumn<PaymentGrafic, BigDecimal> grafPenaltiesColumn;
    // Поступления
    @FXML private TableView<Receipt> receiptsTable;
    @FXML private TableColumn<Receipt, Integer> recIdReceiptsColumn;
    @FXML private TableColumn<Receipt, Integer> recIdPaymentColumn;
    @FXML private TableColumn<Receipt, LocalDate> reqDateColumn;
    @FXML private TableColumn<Receipt, BigDecimal> reqAmountColumn;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        fioColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getFIO()));
        passportColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getPassport()));
        contactColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getContact()));
        dateColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getRegistrationDate()));
        ratingColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getRating()));
        statusColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getStatus()));

        contIdColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        contBeginDateColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getBeginDate()));
        contRequestIdColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getRequestId()));
        contAmountColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getAmount()));
        contPercentColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getPercent()));
        contRepaymentScheduleColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getRepaymentSchedule()));
        contEndDateColumnColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getEndDate()));
        contStatusColumnColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getStatus()));

        grafIdPaymentColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getIdPayment()));
        grafIdContractColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getIdContract()));
        grafDatePlanColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getDatePlan()));
        grafAmounPlanColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getAmountPlan()));
        grafDateFactColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getDateFact()));
        grafAmounFactColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getAmountFact()));
        grafPenaltiesColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getPenalties()));

        recIdReceiptsColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getIdReceipts()));
        recIdPaymentColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getIdPayment()));
        reqDateColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getDate()));
        reqAmountColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getAmount()));

        loadClients();
        loadContracts();
        loadGrafic();
        loadReceipts();
    }

    private void loadClients() {
        try {
            clientsTable.setItems(FXCollections.observableArrayList(Client.findAll()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Не удалось загрузить клиентов: " + e.getMessage()).showAndWait();
        }
    }

    private void loadContracts() {
        try {
            contractsTable.setItems(FXCollections.observableArrayList(CreditContract.findAll()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Не удалось загрузить договоры: " + e.getMessage()).showAndWait();
        }
    }

    private void loadGrafic() {
        try {
            graficTable.setItems(FXCollections.observableArrayList(PaymentGrafic.findAll()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Не удалось загрузить график: " + e.getMessage()).showAndWait();
        }
    }

    private void loadReceipts() {
        try {
            receiptsTable.setItems(FXCollections.observableArrayList(Receipt.findAll()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Не удалось загрузить поступления: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void exit() { Platform.exit(); }
}
