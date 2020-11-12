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

    public boolean addIncome(int income, String cardNumber){
       return databaseHelper.updateBalance(income,cardNumber);
    }

    public boolean doTransfer(int amount, String recipient, String sender){
        return databaseHelper.makingTransfer(amount,recipient,sender);
    }

    public boolean closeAccount(String cardNumber){
        return databaseHelper.deleteAccount(cardNumber);
    }
}
