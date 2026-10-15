package com.example.creditcore;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private final int id;
    private final String FIO;
    private final String passport;
    private final String contact;
    private final LocalDate registrationDate;
    private final int rating;
    private final String status;

    public Client(int id, String fio, String passport, String contact, LocalDate registrationDate, int rating, String status) {
        this.id = id;
        this.FIO = fio;
        this.passport = passport;
        this.contact = contact;
        this.registrationDate = registrationDate;
        this.rating = rating;
        this.status = status;
    }

    public int getId() { return id; }
    public String getFIO() { return FIO; }
    public String getPassport() { return passport; }
    public String getContact() { return contact; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public int getRating() { return rating; }
    public final String getStatus() { return status; }

    // Получение всех клиентов из базы данных
    public static List<Client> findAll() throws SQLException {
        String sql = "SELECT Номер_клиента, ФИО, Паспортные_данные, " +
                "Контактные_данные, Дата_регистрации, " +
                "Кредитный_рейтинг, Статус_клиента " +
                "FROM Клиент ORDER BY Номер_клиента";

        List<Client> list = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Date date = rs.getDate("Дата_регистрации");

                list.add(new Client(
                        rs.getInt("Номер_клиента"),
                        rs.getString("ФИО"),
                        rs.getString("Паспортные_данные"),
                        rs.getString("Контактные_данные"),
                        date == null ? null : date.toLocalDate(),
                        rs.getInt("Кредитный_рейтинг"),
                        rs.getString("Статус_клиента")
                ));
            }
        }
        return list;
    }
}