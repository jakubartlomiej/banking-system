package com.jakubart;

import java.util.ArrayList;

public class Bank {

    private ArrayList<Account> accountsList;

    public Bank() {
        accountsList = new ArrayList<>();
    }

    public ArrayList<Account> getAccountsList() {
        return accountsList;
    }

    public void setAccountsList(ArrayList<Account> accountsList) {
        this.accountsList = accountsList;
    }

    public boolean addAccount(Account account) {
        return accountsList.add(account);

    }

    public boolean checkIfExistAccount(Account account) {
        boolean accountExsist = false;
        for (Account item : accountsList) {
            if (item.getCardNoumber().equals(account.getCardNoumber())) {
                accountExsist = true;
                break;
            }
        }
        return accountExsist;
    }

    public boolean login(String card, String pin) {
        boolean login = false;
        for (Account item : accountsList) {
            if (item.getCardNoumber().equals(card) && item.getPin().equals(pin)) {
                login = true;
                break;
            }
        }
        return login;
    }

    public int checkAccountBalance(String cardNoumber) {
        int balance = 0;
        for (Account item : accountsList) {
            if (item.getCardNoumber().equals(cardNoumber)) {
                balance = item.getBalance();
                break;
            }
        }
        return balance;
    }
}
