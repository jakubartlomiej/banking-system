package com.jakubart;

import java.util.Objects;

public class Bank {

    private final DatabaseHelper databaseHelper = new DatabaseHelper();

    public Bank(String fileName) {
        databaseHelper.createDatabase(fileName);
    }

    public boolean addAccount(Account account) {
        return databaseHelper.addAccount(account);

    }

    public boolean checkIfExistAccount(String cardNumber) {
        return databaseHelper.findByCardNumber(cardNumber) != null;
    }

    public boolean login(String card, String pin) {
        return databaseHelper.loginAccount(card, pin);
    }

    public int checkAccountBalance(String cardNoumber) {
        int balance = 0;
        if (databaseHelper.findByCardNumber(cardNoumber) != null) {
            balance = Objects.requireNonNull(databaseHelper.findByCardNumber(cardNoumber)).getBalance();
        }
        return balance;
    }
}
