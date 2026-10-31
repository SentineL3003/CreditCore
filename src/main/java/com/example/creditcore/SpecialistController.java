package com.example.creditcore;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class SpecialistController {
    @FXML private Button addClientButton;
    @FXML private TextField clientSearch;
    private final ObservableList<Client> clients = FXCollections.observableArrayList();
    @FXML private ComboBox<String> clientSearchBy;

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
    // Поручитель
    @FXML private TableView<Guarantor> guarantorsTable;
    @FXML private TableColumn<Guarantor, Integer> guarIdColumn;
    @FXML private TableColumn<Guarantor, String> guarFioColumn;
    @FXML private TableColumn<Guarantor, String> guarPassportColumn;
    @FXML private TableColumn<Guarantor, String> guarContactColumn;
    // Заявка
    @FXML private TableView<Request> requestsTable;
    @FXML private TableColumn<Request, Integer> reqIdColumn;
    @FXML private TableColumn<Request, LocalDate> reqDateColumn;
    @FXML private TableColumn<Request, Integer> reqClientColumn;
    @FXML private TableColumn<Request, Integer> reqEmployeeColumn;
    @FXML private TableColumn<Request, BigDecimal> reqAmountColumn;
    @FXML private TableColumn<Request, Integer> reqTermColumn;
    @FXML private TableColumn<Request, String> reqPurposeColumn;
    @FXML private TableColumn<Request, String> reqStatusColumn;
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

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        fioColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getFIO()));
        passportColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getPassport()));
        contactColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getContact()));
        dateColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getRegistrationDate()));
        ratingColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getRating()));
        statusColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getStatus()));

        guarIdColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        guarFioColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getFIO()));
        guarPassportColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getPassport()));
        guarContactColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getContact()));

        reqIdColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        reqDateColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getDate()));
        reqClientColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getClientId()));
        reqEmployeeColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getEmployeeId()));
        reqAmountColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getAmount()));
        reqTermColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getTerm()));
        reqPurposeColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getPurpose()));
        reqStatusColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getStatus()));

        contIdColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getId()));
        contBeginDateColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getBeginDate()));
        contRequestIdColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getRequestId()));
        contAmountColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getAmount()));
        contPercentColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getPercent()));
        contRepaymentScheduleColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getRepaymentSchedule()));
        contEndDateColumnColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getEndDate()));
        contStatusColumnColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getStatus()));

        FilteredList<Client> filteredClients = new FilteredList<>(clients, c -> true);
        clientsTable.setItems(filteredClients);

        clientSearch.textProperty().addListener((obs, oldText, newText) -> {
            String text = newText.trim().toLowerCase();
            filteredClients.setPredicate(client ->
                    text.isEmpty()
                            || client.getFIO().toLowerCase().contains(text)
                            || client.getPassport().toLowerCase().contains(text)
                            || client.getContact().toLowerCase().contains(text)
                            || client.getStatus().toLowerCase().contains(text));
        });

        loadClients();
        loadGuarantors();
        loadRequests();
        loadContracts();
    }

    private void loadClients() {
        try {
            clients.setAll(Client.findAll());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Не удалось загрузить клиентов: " + e.getMessage()).showAndWait();
        }
    }

    private void loadGuarantors() {
        try {
            guarantorsTable.setItems(FXCollections.observableArrayList(Guarantor.findAll()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Не удалось загрузить поручителей: " + e.getMessage()).showAndWait();
        }
    }

    private void loadRequests() {
        try {
            requestsTable.setItems(FXCollections.observableArrayList(Request.findAll()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Не удалось загрузить заявки: " + e.getMessage()).showAndWait();
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

    @FXML
    private void deleteClient() {
        Client selected = clientsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.INFORMATION, "Выберите клиента в таблице").showAndWait();
            return;
        }

        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall("{call Удалить_клиента(?)}")) {

            cs.setInt(1, selected.getId());
            cs.execute();
            loadClients();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void deleteGuarantor() {
        Guarantor selected = guarantorsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.INFORMATION, "Выберите поручителя в таблице").showAndWait();
            return;
        }

        try (Connection conn = Database.getConnection();
             CallableStatement cs = conn.prepareCall("{call Удалить_поручителя(?)}")) {

            cs.setInt(1, selected.getId());
            cs.execute();
            loadGuarantors();
        }  catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }

    }

    @FXML
    private void openAddClient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addClient-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Новый клиент");
            stage.setScene(new Scene(root));
            stage.show();

            loadClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openAddGuarantor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addGuarantor-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Новый поручитель");
            stage.setScene(new Scene(root));
            stage.show();

            loadGuarantors();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openAddRequest() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addRequest-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Новая заявка");
            stage.setScene(new Scene(root));
            stage.show();

            loadRequests();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openAddContract() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addContract-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Новый договор");
            stage.setScene(new Scene(root));
            stage.show();

            loadContracts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void exit() { Platform.exit(); }
}
