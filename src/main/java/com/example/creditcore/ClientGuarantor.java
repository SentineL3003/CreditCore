package com.example.creditcore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Заменить на представление
public class ClientGuarantor {
    private final int clintID;
    private final int guarantorID;

    public ClientGuarantor(int clintID, int guarantorID) {
        this.clintID = clintID;
        this.guarantorID = guarantorID;
    }

    public int getClintID() { return clintID; }
    public int getGuarantorID() { return guarantorID; }

    public static List<ClientGuarantor> findAll() throws SQLException {
        String sql = "SELECT * FROM Клиент_поручитель";

        List<ClientGuarantor> list = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new ClientGuarantor(
                        rs.getInt("Номер_клиента"),
                        rs.getInt("Номер_поручителя")
                ));
            }
        }
        return list;
    }
}