package com.example.creditcore;

import java.sql.Date;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentGrafic {
    private final int idPayment;
    private final int idContract;
    private final LocalDate datePlan;
    private final BigDecimal amountPlan;
    private final LocalDate dateFact;
    private final BigDecimal amountFact;
    private final BigDecimal penalties;


    public PaymentGrafic(int idPayment, int idContract, LocalDate datePlan, BigDecimal amountPlan, LocalDate dateFact, BigDecimal amountFact, BigDecimal penalties) {
        this.idPayment = idPayment;
        this.idContract = idContract;
        this.datePlan = datePlan;
        this.amountPlan = amountPlan;
        this.dateFact = dateFact;
        this.amountFact = amountFact;
        this.penalties = penalties;
    }

    public int getIdPayment() { return idPayment; }
    public int getIdContract() { return idContract; }
    public LocalDate getDatePlan() { return datePlan; }
    public BigDecimal getAmountPlan() { return amountPlan; }
    public LocalDate getDateFact() { return dateFact; }
    public BigDecimal getAmountFact() { return amountFact; }
    public BigDecimal getPenalties() { return penalties; }

    public static List<PaymentGrafic> findAll() throws SQLException {
        String sql = "SELECT Код_платежа, Код_договора, Дата_плановая, Сумма_плановая, "
               + "Дата_фактическая, Сумма_фактическая, Пени FROM График_платежей ORDER BY Код_платежа";

        List<PaymentGrafic> list = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Date factDateSql = rs.getDate("Дата_фактическая");   // может быть null

                LocalDate dateFact = null;
                if (factDateSql != null) {
                    dateFact = factDateSql.toLocalDate();
                }

                list.add(new PaymentGrafic(
                        rs.getInt("Код_платежа"),
                        rs.getInt("Код_договора"),
                        rs.getDate("Дата_плановая").toLocalDate(),
                        rs.getBigDecimal("Сумма_плановая"),
                        dateFact,
                        rs.getBigDecimal("Сумма_фактическая"),
                        rs.getBigDecimal("Пени")
                ));
            }
        }
        return list;
    }
}
