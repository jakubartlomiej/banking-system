package com.jakubart;

import java.sql.*;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:";
    public String urlDatabase;

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + urlDatabase);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public void createDatabase(String fileName) {
        String urlFileDb = URL + fileName;
        this.urlDatabase = fileName;
        try (Connection conn = DriverManager.getConnection(urlFileDb)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        createTableCard(urlFileDb);
    }

    private static void createTableCard(String url) {
        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	number TEXT ,\n"
                + "	pin TEXT,\n"
                + "	balance INTEGER DEFAULT 0 \n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException throwables) {
            System.out.println("Unable to create table");
            throwables.printStackTrace();
        }
    }

    public boolean addAccount(Account account) {
        boolean status = false;
        String sql = "INSERT INTO card (number, pin) VALUES (?,?)";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account.getCardNoumber());
            pstmt.setString(2, account.getPin());
            pstmt.executeUpdate();
            status = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public Account findByCardNumber(String cardNumber) {
        String sql = "SELECT id,number,pin,balance FROM card WHERE number = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("id")
                        , rs.getString("number")
                        , rs.getString("pin")
                        , rs.getInt("balance"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean loginAccount(String card, String pin) {
        boolean status = false;
        String sql = "SELECT * FROM card WHERE NUMBER = ? AND PIN = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, card);
            pstmt.setString(2, pin);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                status = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public boolean updateBalance(int income, String cardNumber) {
        boolean status = false;
        String sql = "UPDATE card SET balance = balance + ? where number = ? ";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, income);
            pstmt.setString(2, cardNumber);
            pstmt.executeUpdate();
            status = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public boolean makingTransfer(int amount, String recipient, String sender) {
        boolean status = false;
        String sqlSender = "UPDATE card SET balance = balance - ? where number = ?";
        String sqlRecipient = "UPDATE card SET balance = balance + ? where number = ?";
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        try {
            conn = this.getConnection();
            if (conn == null) {
                return status;
            }
            conn.setAutoCommit(false);
            pstmt1 = conn.prepareStatement(sqlSender);
            pstmt1.setInt(1, amount);
            pstmt1.setString(2, sender);
            int rowAffected = pstmt1.executeUpdate();
            if (rowAffected != 1) {
                conn.rollback();
                return status;
            }
            pstmt2 = conn.prepareStatement(sqlRecipient);
            pstmt2.setInt(1, amount);
            pstmt2.setString(2, recipient);
            pstmt2.executeUpdate();
            conn.commit();
            status = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }
}

