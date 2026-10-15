package com.example.creditcore;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private final int id;
    private final LocalDate date;
    private final int clientId;
    private final int employeeId;
    private final BigDecimal amount;
    private final int term;
    private final String purpose;
    private final String status;

    public Request(int id, LocalDate date, int clientId, int employeeId, BigDecimal amount, int term, String purpose, String status) {
        this.id = id;
        this.date = date;
        this.clientId = clientId;
        this.employeeId = employeeId;
        this.amount = amount;
        this.term = term;
        this.purpose = purpose;
        this.status = status;
    }

    public int getId() { return id; }
    public LocalDate getDate() { return date; }
    public int getClientId() { return clientId; }
    public int getEmployeeId() { return employeeId; }
    public BigDecimal getAmount() { return amount; }
    public int getTerm() { return term; }
    public String getPurpose() { return purpose; }
    public String getStatus() { return status; }

    public static List<Request> findAll() throws SQLException {
        String sql = "SELECT Номер_заявки, Дата_подачи, Номер_клиента, Номер_сотрудника, "
                + "Сумма, Срок, Цель, Статус FROM Заявка ORDER BY Номер_заявки";
        List<Request> list = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Request(
                        rs.getInt("Номер_заявки"),
                        rs.getDate("Дата_подачи").toLocalDate(),
                        rs.getInt("Номер_клиента"),
                        rs.getInt("Номер_сотрудника"),
                        rs.getBigDecimal("Сумма"),
                        rs.getInt("Срок"),
                        rs.getString("Цель"),
                        rs.getString("Статус")));
            }
        }
        return list;
    }
}