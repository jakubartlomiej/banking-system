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

    public String cardNoumberGenerator(){
        Random random = new Random();
        final String BIN = "400000";
        int acountIdentifier = random.nextInt(899999999)+100000000;
        int checkSum = random.nextInt(10);
        StringBuilder sb = new StringBuilder();
        sb.append(BIN).append(acountIdentifier).append(checkSum);
        return sb.toString();
    }

    public String pinGenerator(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i=0; i <4; i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
