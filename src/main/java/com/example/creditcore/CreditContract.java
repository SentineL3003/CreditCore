package com.example.creditcore;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreditContract {
    private final int id;
    private final LocalDate beginDate;
    private final int requestId;
    private final BigDecimal amount;
    private final BigDecimal percent;
    private final String repaymentSchedule;
    private final LocalDate endDate;
    private final String status;

    public CreditContract(int id, LocalDate beginDate, int requestId, BigDecimal amount,
                          BigDecimal percent, String repaymentSchedule, LocalDate endDate, String status) {
        this.id = id;
        this.beginDate = beginDate;
        this.requestId = requestId;
        this.amount = amount;
        this.percent = percent;
        this.repaymentSchedule = repaymentSchedule;
        this.endDate = endDate;
        this.status = status;
    }

    public int getId() { return id; }
    public LocalDate getBeginDate() { return beginDate; }
    public int getRequestId() { return requestId; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getPercent() { return percent; }
    public String getRepaymentSchedule() { return repaymentSchedule; }
    public LocalDate getEndDate() { return endDate; }
    public String getStatus() { return status; }

    public static List<CreditContract> findAll() throws SQLException {
        String sql = "SELECT Код_договора, Дата_заключения, Номер_заявки, Сумма_выдачи, "
                + "Процентная_ставка, Схема_погашения, Дата_окончания, Статус "
                + "FROM Кредитный_договор ORDER BY Код_договора";
        List<CreditContract> list = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new CreditContract(
                        rs.getInt("Код_договора"),
                        rs.getDate("Дата_заключения").toLocalDate(),
                        rs.getInt("Номер_заявки"),
                        rs.getBigDecimal("Сумма_выдачи"),
                        rs.getBigDecimal("Процентная_ставка"),
                        rs.getString("Схема_погашения"),
                        rs.getDate("Дата_окончания").toLocalDate(),
                        rs.getString("Статус")));
            }
        }
        return list;
    }
}