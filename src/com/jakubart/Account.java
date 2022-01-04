package com.jakubart;

import java.util.Random;

public class Account {

    private int id;
    private String cardNumber;
    private String pin;
    private int balance;

    public Account() {
    }

    public Account(int id, String cardNoumber, String pin, int balance) {
        this.id = id;
        this.cardNumber = cardNoumber;
        this.pin = pin;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String cardNoumberGenerator() {
        Random random = new Random();
        final String BIN = "400000";
        String acountIdentifier = String.valueOf(random.nextInt(899999999) + 100000000);
        return BIN + acountIdentifier + luhnaAlgorithmGenerator(BIN.concat(acountIdentifier).concat("0").toCharArray());
    }

    public String pinGenerator() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public int luhnaAlgorithmGenerator(char[] cardNoumber) {
        int sumCardNoumbers = 0;
        for (int i = 0; i < cardNoumber.length - 1; i++) {
            int temp = 0;
            if (i % 2 == 0) {
                temp = Integer.parseInt(String.valueOf(cardNoumber[i])) * 2;
                if (temp > 9) {
                    temp = temp - 9;
                }
                cardNoumber[i] = Character.forDigit(temp, 10);
            }
        }
        for (int i = 0; i < cardNoumber.length - 1; i++) {
            sumCardNoumbers += Integer.parseInt(String.valueOf(cardNoumber[i]));
        }
        sumCardNoumbers = sumCardNoumbers % 10;
        return sumCardNoumbers == 0 ? 0 : 10 - sumCardNoumbers % 10;
    }
}
