package com.example.creditcore;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private final int idReceipts;
    private final int idPayment;
    private final LocalDate Date;
    private final BigDecimal amount;

    public Receipt(int idReceipts, int idPayment, LocalDate date, BigDecimal amount) {
        this.idReceipts = idReceipts;
        this.idPayment = idPayment;
        Date = date;
        this.amount = amount;
    }

    public int getIdReceipts() { return idReceipts; }
    public int getIdPayment() { return idPayment; }
    public LocalDate getDate() { return Date; }
    public BigDecimal getAmount() { return amount; }

    public static List<Receipt> findAll() throws SQLException {
        String sql = "SELECT Код_поступления, Код_платежа, Дата, Сумма FROM Поступление ORDER BY Код_поступления";

        List<Receipt> list = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Receipt(
                        rs.getInt("Код_поступления"),
                        rs.getInt("Код_платежа"),
                        rs.getDate("Дата").toLocalDate(),
                        rs.getBigDecimal("Сумма")
                ));
            }
        }
        return list;
    }
}
