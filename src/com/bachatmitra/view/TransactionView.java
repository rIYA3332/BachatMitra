package com.bachatmitra.view;

import com.bachatmitra.model.Transaction;

/**
 *
 * @author Riya Bhatta
 */

public class TransactionView {

    public void showMainMenu() {
        System.out.println("\n***Bachat Mitra***");
        System.out.println("1. Add Income");
        System.out.println("2. Exit");
        System.out.print("Choose: ");
    }

    public String readInput(String prompt) {
        System.out.print(prompt);
        return new java.util.Scanner(System.in).nextLine().trim();
    }

    public void showTransactionAdded(Transaction t) {
        System.out.println("Income added: " + t);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public void showExitMessage() {
        System.out.println("Exiting. Goodbye!");
    }
}


