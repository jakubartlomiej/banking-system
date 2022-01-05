package com.jakubart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        Bank bank = new Bank("banking.db");
        Account account = new Account();
        Menu menu = new Menu();
        menu.printMainMenu();
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
                            menu.printCreatedAccount(account);
                        }
                        account.setPin("");
                        account.setCardNumber("");
                        menu.printMainMenu();
                        break;
                    case 2:
                        System.out.print("Enter your card number:\n>");
                        account.setCardNumber(scanner.next());
                        System.out.print("Enter your PIN:\n>");
                        account.setPin(scanner.next());
                        if (bank.login(account.getCardNumber(), account.getPin())) {
                            System.out.println("You have successfully logged in!\n");
                            menu.printAccountMenu();
                            do {
                                System.out.print(">");
                                choice = scanner.nextInt();
                                switch (choice) {
                                    case 1:
                                        menu.printAccountBalance(bank, account);
                                        menu.printAccountMenu();
                                        break;
                                    case 2:
                                        menu.createIncome(scanner, bank, account);
                                        menu.printAccountMenu();
                                        break;
                                    case 3:
                                        menu.makeTransfer(scanner, bank, account);
                                        break;
                                    case 4:
                                        menu.closeAccount(bank, account);
                                        menu.logOut(account);
                                        menu.printMainMenu();
                                        break;
                                    case 5:
                                        menu.logOut(account);
                                        System.out.println("You have successfully logged out!\n");
                                        menu.printMainMenu();
                                        break;
                                    case 0:
                                        System.out.println("Bye !");
                                        System.exit(1);
                                }
                            } while (choice < 4);
                        } else {
                            menu.wrongCredential(account);
                            menu.printMainMenu();
                        }
                        break;
                    case 0:
                        System.out.println("Bye !");
                        System.exit(1);
                        break;
                }
            }
        } catch (InputMismatchException e) {
            LOGGER.error("InputMismatchException - error input parameter");
        }
    }
}
