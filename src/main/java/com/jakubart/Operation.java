package com.jakubart;

import java.util.Scanner;

public class Operation {

    public void printCreatedAccount(Account account) {
        System.out.println("\nYour card have been created");
        System.out.println("Your card number:");
        System.out.println(account.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(account.getPin() + "\n");
    }

    public void wrongCredential(Account account) {
        account.setCardNumber("");
        account.setPin("");
        System.out.println("Wrong card number or PIN!\n");
    }

    public void printAccountBalance(Bank bank, Account account) {
        System.out.println("Balance: " + bank.checkAccountBalance(account.getCardNumber()) + "\n");
    }

    public void createIncome(Scanner scanner, Bank bank, Account account) {
        System.out.print("Enter income: \n>");
        if (bank.addIncome(scanner.nextInt(), account.getCardNumber())) {
            System.out.println("Income was added!");
        }
    }

    public void makeTransfer(Scanner scanner, Bank bank, Account account, Menu menu) {
        System.out.println("Transfer");
        System.out.print("Enter card number:\n>");
        String transferCard = scanner.next();
        if (transferCard.equals(account.getCardNumber())) {
            System.out.println("You can't transfer money to the same account!\n");
            menu.printAccountMenu();
        } else if (checkCardForLuhnaAlgorithm(account, transferCard)) {
            System.out.println("Probably you made mistake in the card number. Please try again!\n");
            menu.printAccountMenu();
        } else if (!bank.checkIfExistAccount(transferCard)) {
            System.out.println("Such a card does not exist.\n");
            menu.printAccountMenu();
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
            menu.printAccountMenu();
        }
    }

    private boolean checkCardForLuhnaAlgorithm(Account account, String transferCard) {
        return account.luhnaAlgorithmGenerator(transferCard.toCharArray()) != Integer.parseInt(String.valueOf(transferCard.charAt(transferCard.length() - 1)));
    }

    public void closeAccount(Bank bank, Account account) {
        if (bank.closeAccount(account.getCardNumber())) {
            System.out.println("The account has been closed!");
        }
    }

    public void logOut(Account account) {
        account.setId(0);
        account.setCardNumber("");
        account.setPin("");
        account.setBalance(0);
    }
}
