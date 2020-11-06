package com.jakubart;

import java.util.Random;

public class Account {

    private String cardNoumber;
    private String pin;
    private int balance;

    public Account() {
        this.cardNoumber = cardNoumberGenerator();
        this.pin = pinGenerator();
        this.balance = 0;
    }

    public String getCardNoumber() {
        return cardNoumber;
    }

    public void setCardNoumber(String cardNoumber) {
        this.cardNoumber = cardNoumber;
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
        return BIN + acountIdentifier + luhnaAlgorithmGenerator(BIN.concat(acountIdentifier).toCharArray());
    }

    public String pinGenerator() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private int luhnaAlgorithmGenerator(char[] cardNoumber) {
        int sumCardNoumbers = 0;
        for (int i = 0; i < cardNoumber.length; i++) {
            int temp = 0;
            if (i % 2 == 0) {
                temp = Integer.parseInt(String.valueOf(cardNoumber[i])) * 2;
                if (temp > 9) {
                    temp = temp - 9;
                }
                cardNoumber[i] = Character.forDigit(temp, 10);
            }
        }
        for (char c : cardNoumber) {
            sumCardNoumbers += Integer.parseInt(String.valueOf(c));
        }
        sumCardNoumbers = sumCardNoumbers % 10;
        return sumCardNoumbers == 0 ? 0 : 10 - sumCardNoumbers % 10;
    }
}
