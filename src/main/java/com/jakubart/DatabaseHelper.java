package com.jakubart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;


public class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final String URL = "jdbc:sqlite:";
    public String urlDatabase;

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL + urlDatabase);
        } catch (SQLException e) {
            LOGGER.error("SQL Exception - connection to database failed!");
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
        } catch (SQLException e) {
            LOGGER.error("SQL Exception - created database failed!");
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
        } catch (SQLException e) {
            LOGGER.error("SQL Exception - unable to create table 'card'!");
        }
    }

    public boolean addAccount(Account account) {
        boolean status = false;
        String sql = "INSERT INTO card (number, pin) VALUES (?,?)";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account.getCardNumber());
            pstmt.setString(2, account.getPin());
            pstmt.executeUpdate();
            status = true;
        } catch (SQLException e) {
            LOGGER.error("SQL Exception - unable to add new account!");
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
        } catch (SQLException e) {
            LOGGER.error("SQL Exception - unable to find account by card number");
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
        } catch (SQLException e) {
            LOGGER.error("SQL Exception - unable to login account");
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
        } catch (SQLException e) {
            LOGGER.error("SQL Exception - unable to add income to balance");
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
        } catch (SQLException e) {
            LOGGER.error("SQL Exception - unable to make transfer");
        }
        return status;
    }

    public boolean deleteAccount(String cardNumber) {
        boolean status = false;
        String sql = "DELETE FROM card where number = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cardNumber);
            pstmt.executeUpdate();
            status = true;
        } catch (SQLException e) {
            LOGGER.error("SQL Exception - unable to delete account");
        }
        return status;

    }
}

