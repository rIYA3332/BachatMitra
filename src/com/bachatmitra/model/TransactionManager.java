package com.bachatmitra.model;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;//new

/*
 * TransactionManager class
 * This class is responsible for managing all financial transactions (Income and Expense)
 * in the Bachat Mitra application. It keeps transactions in memory, assigns unique IDs,
 * saves them to a CSV file for persistence, and loads existing transactions from CSV on startup.
 */

public class TransactionManager {
    private List<Transaction> transactions; // a list in memory to store all transaction object
    private long nextId = 1; // keeps track of unique id for each transactions

    private final String storageDir = "data"; // storage directory - folder name where CSV file is stored
    private final String storagePath = "data/transactions.csv"; // path where CSV file is stored
    private static final DateTimeFormatter CSV_DATE_FORMAT = DateTimeFormatter.ofPattern("M/d/yyyy"); // defines how dates are stored in CSV

    public TransactionManager() { // constructor
        transactions = new ArrayList<>(); // initializes transaction as empty list
        loadFromFile(); // calls method to load existing transactions from CSV file
        for (Transaction t : transactions) {
            if (t.getId() >= nextId) nextId = t.getId() + 1;
        } // Loops through loaded transactions to update nextId so new transactions get unique IDs.
    }
    /*
    * Takes input details for an income transaction.
    * Throws an exception if the amount is ≤ 0.
    * Creates a new Income object with a unique ID.
    * Adds it to the transactions list in memory.
    * Calls saveTransaction() → writes the transaction to CSV.
    * Returns the newly created Income object.
     */

    public Transaction addIncome(double amount, String category, String account, String note, String date)
            throws InvalidTransactionException {
        if (amount <= 0) throw new InvalidTransactionException("Income must be positive.");
        Income i = new Income(nextId++, amount, category, account, note, date);
        transactions.add(i);
        saveTransaction(i);
        return i;
    }

    public Transaction addExpense(double amount, String category, String account, String note, String date)
            throws InvalidTransactionException {
        if (amount <= 0) throw new InvalidTransactionException("Expense must be positive.");
        Expense e = new Expense(nextId++, amount, category, account, note, date);
        transactions.add(e);
        saveTransaction(e);
        return e;
    }



    /*
    Useful for displaying all transactions in the UI
     */
    public List<Transaction> getAllTransactions() {
        return transactions; //  Returns the full list of transactions in memory.
    }
   // category section
    public Map<String, Double> summaryByCategory(boolean isExpense) {
        return transactions.stream()
                .filter(t -> t.isExpense() == isExpense)
                .collect(Collectors.groupingBy( // .collect(...) takes the filtered stream and gathers it into a result.
                        // Collectors.groupingBy(...)  groups items by a key.
                        Transaction::getCategory, // For each transaction, groups them by their category eg: food[100,200]
                        Collectors.summingDouble(Transaction::getAmount) // add up the tottal amount
                ));
    }


    /*
    Ensures the directory exists; if not, it creates it.
    Checks if CSV file exists.
    Opens the file in append mode.
    Writes CSV header if file is new.
    Writes the transaction details as a comma-separated line in CSV:
    ID, type, amount, category, account, note, date
    Catches IO exceptions if file cannot be written.
     */
    private void saveTransaction(Transaction t) { // saves the transaction to the CSV file
        File dir = new File(storageDir);
        if (!dir.exists()) dir.mkdirs();

        boolean fileExists = new File(storagePath).exists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(storagePath, true))) {
            if (!fileExists) writer.println("id,type,amount,category,account,note,date"); // header in CSV
            String type = t.isExpense() ? "Expense" : "Income";
            /*
            * t.getDate() returns a String representing the date, e.g., "2025-09-18". (given by user)
            * LocalDate.parse(...) converts that String into a LocalDate object.
            * LocalDate is a Java class that represents dates without time (year, month, day).
            * Once it’s a LocalDate, you can do things like add days, compare dates, format it differently, etc.
             */
            LocalDate d = LocalDate.parse(t.getDate());
            /*
            printf = "print formatted"-
            Works like System.out.printf but writes to the PrintWriter (PrintWriter is a class in java.io used to write text to files, streams, or the console.).
            This string defines the format for 7 values, separated by commas (CSV style)
            %d - integer, %s - string, %.2f - floating-point number with 2 decimal places, %n - new line
            long → %d
type       "Income" or "Expense" → %s
             */
            // This line saves one transaction as a comma-separated row in the CSV file with proper formatting
            writer.printf("%d,%s,%.2f,%s,%s,%s,%s%n",
                    t.getId(), type, t.getAmount(), t.getCategory(),
                    t.getAccount(), t.getNote(), d.format(CSV_DATE_FORMAT));
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }
    /*
    Checks if CSV file exists; if not, creates an empty list.
    Opens CSV file using BufferedReader.
    Skips the first line (header).
    Reads each line:
    Splits by comma → array of 7 elements: id,type,amount,category,account,note,date.
    If the array length is not 7 → skips the line.
    Tries to parse each field:
    Long.parseLong for ID.
    Double.parseDouble for amount.
    LocalDate.parse for date.
    Creates Income or Expense object based on type.
    Adds transaction to the transactions list.
    If parsing fails → prints: Skipping invalid line: ...
     */

    // Load Transactions from CSV
    private void loadFromFile() {
        File f = new File(storagePath);
        if (!f.exists()) {
            transactions = new ArrayList<>();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(storagePath))) {
            transactions = new ArrayList<>();
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 7) continue;
                try {
                    // Skip empty or invalid IDs safely
                    if (parts[0].isEmpty()) {
                        System.out.println("Skipping line with empty ID: " + line);
                        continue;
                    }

                    long id = Long.parseLong(parts[0]);
                    String type = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    String category = parts[3];
                    String account = parts[4];
                    String note = parts[5];
                    String dateString = parts[6];
                    String date = LocalDate.parse(dateString, CSV_DATE_FORMAT).toString();

                    Transaction t;
                    if (type.equalsIgnoreCase("Income"))
                        t = new Income(id, amount, category, account, note, date);
                    else
                        t = new Expense(id, amount, category, account, note, date);

                    transactions.add(t);
                } catch (Exception ex) {
                    //System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load transactions: " + e.getMessage());
            transactions = new ArrayList<>();
        }
    }
}
