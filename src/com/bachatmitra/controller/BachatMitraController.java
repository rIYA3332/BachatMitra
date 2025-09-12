package com.bachatmitra.controller;



import com.bachatmitra.model.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author Riya Bhatta
 */

/*
 * Controller class for Bachat Mitra application
 * Handles user input and manages income transactions
 */

public class BachatMitraController {

    private TransactionManager manager;
    private Scanner scanner;

    public BachatMitraController() {
        manager = new TransactionManager();
        scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("***Bachat Mitra***");

        while (true) {
            System.out.println("\n1. Add Income");
            System.out.println("2. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addIncomeFlow();
                case "2" -> exitProgram();
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // exception handling
    private void addIncomeFlow() {
        try {
            System.out.print("Amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            // Category selection
            System.out.println("Choose Category:");
            System.out.println("1. Allowance\n2. Salary\n3. Petty Cash\n4. Bonus\n5. Other");
            String categoryChoice = scanner.nextLine().trim();
            String category = switch (categoryChoice) {
                case "1" -> "Allowance";
                case "2" -> "Salary";
                case "3" -> "Petty Cash";
                case "4" -> "Bonus";
                default -> "Other";
            };

            // Account selection
            System.out.println("Choose Account:");
            System.out.println("1. Cash\n2. Account\n3. Card");
            String accountChoice = scanner.nextLine().trim();
            String account = switch (accountChoice) {
                case "1" -> "Cash";
                case "2" -> "Account";
                case "3" -> "Card";
                default -> "Cash";
            };

            System.out.print("Note: ");
            String note = scanner.nextLine().trim();

            LocalDate date = readDateOrToday();

            Transaction t = manager.addIncome(amount, category, account, note, date);
            System.out.println("Income added: " + t);

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (InvalidTransactionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // exception handling
    private LocalDate readDateOrToday() {
        System.out.print("Date (yyyy-MM-dd) or press ENTER for today: ");
        String s = scanner.nextLine().trim();
        if (s.isEmpty()) return LocalDate.now();
        try {
            return LocalDate.parse(s);
        } catch (DateTimeParseException e) {
            System.out.println("Bad date, using today.");
            return LocalDate.now();
        }
    }

    private void exitProgram() {
        System.out.println("Exiting. Goodbye!");
        System.exit(0);
    }
}

