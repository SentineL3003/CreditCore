package com.example.creditcore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Guarantor {
    private final int id;
    private final String FIO;
    private final String passport;
    private final String contact;

    public Guarantor(int id, String fio, String passport, String contact) {
        this.id = id;
        FIO = fio;
        this.passport = passport;
        this.contact = contact;
    }

    public int getId() { return id; }
    public String getFIO() { return FIO; }
    public String getPassport() { return passport; }
    public String getContact() { return contact; }


    public static List<Guarantor> findAll() throws SQLException {
        String sql = "SELECT Номер_поручителя, ФИО, Паспортные_данные, Контактные_данные FROM Поручитель ORDER BY Номер_поручителя";

        List<Guarantor> list = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Guarantor(
                        rs.getInt("Номер_поручителя"),
                        rs.getString("ФИО"),
                        rs.getString("Паспортные_данные"),
                        rs.getString("Контактные_данные")
                ));
            }
        }
        return list;
    }
}