package com.jakubart;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DatabaseHelper.getConnection();
        Scanner scanner = new Scanner(System.in);
        int choice;
        String cardNoumber;
        String pin;
        Bank bank = new Bank();
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");

        while (true) {
            System.out.print(">");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    Account account = new Account();
                    while (bank.checkIfExistAccount(account)) {
                        account.setCardNoumber(account.cardNoumberGenerator());
                    }
                    if (bank.addAccount(account)) {
                        System.out.println("Your card has been created");
                        System.out.println("Your card number:");
                        System.out.println(account.getCardNoumber());
                        System.out.println("Your card PIN:");
                        System.out.println(account.getPin() + "\n");
                    }
                    System.out.println("1. Create an account");
                    System.out.println("2. Log into account");
                    System.out.println("0. Exit");
                    break;
                case 2:
                    System.out.print("Enter your card number:\n>");
                    cardNoumber = scanner.next();
                    System.out.print("Enter your PIN:\n>");
                    pin = scanner.next();
                    if (bank.login(cardNoumber, pin)) {
                        System.out.println("You have successfully logged in!\n");
                        System.out.println("1. Balance");
                        System.out.println("2. Log out");
                        System.out.println("0. Exit");
                        do {
                            System.out.print(">");
                            choice = scanner.nextInt();
                            switch (choice) {
                                case 1:
                                    System.out.println("Balance: " + bank.checkAccountBalance(cardNoumber) + "\n");
                                    System.out.println("1. Balance");
                                    System.out.println("2. Log out");
                                    System.out.println("0. Exit");
                                    break;
                                case 2:
                                    cardNoumber = "";
                                    pin = "";
                                    System.out.println("You have successfully logged out!\n");
                                    System.out.println("1. Create an account");
                                    System.out.println("2. Log into account");
                                    System.out.println("0. Exit");
                                    break;
                                case 0:
                                    System.out.println("Bye !");
                                    System.exit(1);
                            }
                        } while (choice != 2);

                    } else {
                        System.out.println("Wrong card number or PIN!\n");
                        System.out.println("1. Create an account");
                        System.out.println("2. Log into account");
                        System.out.println("0. Exit");
                    }
                    break;
                case 0:
                    System.out.println("Bye !");
                    System.exit(1);
                    break;
            }
        }
    }
}
