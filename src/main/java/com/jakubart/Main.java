package com.jakubart;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        Bank bank = new Bank("banking.db");
        Account account = new Account();
        Menu menu = new Menu();
        menu.main();
        try {
            while (true) {
                System.out.print(">");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        account.setCardNumber(account.cardNumberGenerator());
                        account.setPin(account.pinGenerator());
                        while (bank.checkIfExistAccount(account.getCardNumber())) {
                            account.setCardNumber(account.cardNumberGenerator());
                        }
                        if (bank.addAccount(account)) {
                            System.out.println("\nYour card have been created");
                            System.out.println("Your card number:");
                            System.out.println(account.getCardNumber());
                            System.out.println("Your card PIN:");
                            System.out.println(account.getPin() + "\n");
                        }
                        account.setCardNumber("");
                        account.setPin("");
                        menu.main();
                        break;
                    case 2:
                        System.out.print("Enter your card number:\n>");
                        account.setCardNumber(scanner.next());
                        System.out.print("Enter your PIN:\n>");
                        account.setPin(scanner.next());
                        if (bank.login(account.getCardNumber(), account.getPin())) {
                            System.out.println("You have successfully logged in!\n");
                            menu.account();
                            do {
                                System.out.print(">");
                                choice = scanner.nextInt();
                                switch (choice) {
                                    case 1:
                                        System.out.println("Balance: " + bank.checkAccountBalance(account.getCardNumber()) + "\n");
                                        menu.account();
                                        break;
                                    case 2:
                                        System.out.print("Enter income: \n>");
                                        if (bank.addIncome(scanner.nextInt(), account.getCardNumber())) {
                                            System.out.println("Income was added!");
                                        }
                                        menu.account();
                                        break;
                                    case 3:
                                        System.out.println("Transfer");
                                        System.out.print("Enter card number:\n>");
                                        String transferCard = scanner.next();
                                        if (transferCard.equals(account.getCardNumber())) {
                                            System.out.println("You can't transfer money to the same account!\n");
                                            menu.account();
                                        } else if (account.luhnaAlgorithmGenerator(transferCard.toCharArray()) != Integer.parseInt(String.valueOf(transferCard.charAt(transferCard.length() - 1)))) {
                                            System.out.println("Probably you made mistake in the card number. Please try again!\n");
                                            menu.account();
                                        } else if (!bank.checkIfExistAccount(transferCard)) {
                                            System.out.println("Such a card does not exist.\n");
                                            menu.account();
                                        } else {
                                            System.out.print("Enter how much money you want to transfer::\n>");
                                            int amount = scanner.nextInt();
                                            if (amount > bank.checkAccountBalance(account.getCardNumber())) {
                                                System.out.println("Not enough money!\n");
                                            } else {
                                                if (bank.doTransfer(amount, transferCard, account.getCardNumber())) {
                                                    System.out.println("Success!\n");
                                                }
                                            }
                                            menu.account();
                                        }
                                        break;
                                    case 4:
                                        if (bank.closeAccount(account.getCardNumber())) {
                                            System.out.println("The account has been closed!");

                                        }
                                        account.setId(0);
                                        account.setCardNumber("");
                                        account.setPin("");
                                        account.setBalance(0);
                                        menu.main();
                                        break;
                                    case 5:
                                        account.setId(0);
                                        account.setCardNumber("");
                                        account.setPin("");
                                        account.setBalance(0);
                                        System.out.println("You have successfully logged out!\n");
                                        menu.main();
                                        break;
                                    case 0:
                                        System.out.println("Bye !");
                                        System.exit(1);
                                }
                            } while (choice < 4);
                        } else {
                            account.setCardNumber("");
                            account.setPin("");
                            System.out.println("Wrong card number or PIN!\n");
                            menu.main();
                        }
                        break;
                    case 0:
                        System.out.println("Bye !");
                        System.exit(1);
                        break;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Error input parametr");
            System.out.println(e.getMessage());
        }
    }
}
