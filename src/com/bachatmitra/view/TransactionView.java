package com.bachatmitra.view;

import com.bachatmitra.model.Transaction;
import com.bachatmitra.model.TransactionManager;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TransactionView {
    private Scanner sc = new Scanner(System.in);

    public void showMainMenu() {
        System.out.println("\n*** Bachat Mitra ***");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View All Transactions");
        System.out.println("4. Category Statistics");
        System.out.println("5. Exit");
        System.out.print("Choose: ");
    }

    public int readChoice() {
        return Integer.parseInt(sc.nextLine().trim());
    }

    public double readAmount() {
        System.out.print("Enter amount: ");
        return Double.parseDouble(sc.nextLine().trim());
    }

    public String chooseIncomeCategory() {
        System.out.println("Choose Income Category:");
        System.out.println("1. Allowance");
        System.out.println("2. Salary");
        System.out.println("3. Petty Cash");
        System.out.println("4. Bonus");
        System.out.println("5. Dashain");
        System.out.print("Choose: ");
        switch (sc.nextLine().trim()) {
            case "1": return "Allowance";
            case "2": return "Salary";
            case "3": return "Petty Cash";
            case "4": return "Bonus";
            case "5": return "Dashain";
            default: return "Other";
        }
    }

    public String chooseExpenseCategory() {
        System.out.println("Choose Expense Category:");
        System.out.println("1. Food");
        System.out.println("2. Rent");
        System.out.println("3. Travel");
        System.out.println("4. Gift");
        System.out.println("5. Apparel");
        System.out.println("6. Education");
        System.out.println("7. Investment");
        System.out.print("Choose: ");
        switch (sc.nextLine().trim()) {
            case "1": return "Food";
            case "2": return "Rent";
            case "3": return "Travel";
            case "4": return "Gift";
            case "5": return "Apparel";
            case "6": return "Education";
            case "7": return "Investment";
            default: return "Other";
        }
    }

    public String chooseAccount() {
        System.out.println("Choose Account:");
        System.out.println("1. Cash");
        System.out.println("2. Bank");
        System.out.println("3. Card");
        System.out.print("Choose: ");
        switch (sc.nextLine().trim()) {
            case "1": return "Cash";
            case "2": return "Bank";
            case "3": return "Card";
            default: return "Other";
        }
    }

    public String readNote() {
        System.out.print("Enter note: ");
        return sc.nextLine().trim();
    }

    public void showTransactionAdded(Transaction t) {
        System.out.println("Added: " + t);
    }

    public void showAllTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("\n*** All Transactions ***");
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }


    public void showCategoryStatistics(TransactionManager manager) { //
        System.out.print("Choose type: 1. Income  2. Expense : ");
        String choice = sc.nextLine().trim();
        boolean isExpense = choice.equals("2");

        Map<String, Double> summary = manager.summaryByCategory(isExpense); // summary: hold the category-to-amount results.
        double total = summary.values().stream().mapToDouble(Double::doubleValue).sum(); // adds all the amount in teh map

        if (total == 0) {
            System.out.println("No transactions found for this type.");
            return;
        }

        System.out.println("\n" + (isExpense ? "Expense" : "Income") + " Category Stats:");
        summary.forEach((cat, val) -> { // Loops through each category in the summary map.
            double perc = (val / total) * 100;
            System.out.printf("%s: %.2f (%.2f%%)\n", cat, val, perc); // string: floating-point number with 2 decimal places
        });
    }

    public void showError(String msg) {

        System.out.println("Error: " + msg);
    }

    public void showExitMessage() {

        System.out.println("Exiting. Goodbye!");
    }
}
