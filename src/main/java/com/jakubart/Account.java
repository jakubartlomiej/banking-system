package com.jakubart;

import java.util.Random;

public class Account {

    private int id;
    private String cardNumber;
    private String pin;
    private int balance;

    public Account() {
    }

    public Account(int id, String cardNumber, String pin, int balance) {
        this.id = id;
        this.cardNumber = cardNumber;
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

    public String cardNumberGenerator() {
        Random random = new Random();
        final String BIN = "400000";
        String accountIdentifier = String.valueOf(random.nextInt(899999999) + 100000000);
        return BIN + accountIdentifier + luhnaAlgorithmGenerator(BIN.concat(accountIdentifier).concat("0").toCharArray());
    }

    public String pinGenerator() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public int luhnaAlgorithmGenerator(char[] cardNumber) {
        int lastNumberInCard = 0;
        for (int i = 0; i < cardNumber.length - 1; i++) {
            int multiplyNumber = 0;
            if (i % 2 == 0) {
                multiplyNumber = Integer.parseInt(String.valueOf(cardNumber[i])) * 2;
                if (multiplyNumber > 9) {
                    multiplyNumber = multiplyNumber - 9;
                }
                cardNumber[i] = Character.forDigit(multiplyNumber, 10);
            }
        }
        for (int i = 0; i < cardNumber.length - 1; i++) {
            lastNumberInCard += Integer.parseInt(String.valueOf(cardNumber[i]));
        }
        lastNumberInCard = lastNumberInCard % 10;
        return lastNumberInCard == 0 ? 0 : 10 - lastNumberInCard % 10;
    }
}
