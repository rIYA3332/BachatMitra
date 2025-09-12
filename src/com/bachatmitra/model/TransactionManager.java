package com.bachatmitra.model;

import java.io.*; // File hanling
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
/**
 *
 * @author Riya Bhatta
 */

/*
 * Manages all transactions
 * Responsible for adding income, saving to file, and loading from file
 */


public class TransactionManager {

    private List<Transaction> transactions; // lis tof all transactions
    private long nextId = 1;

    // File Handling
    private final String storageDir = "data";
    private final String storagePath = "data/transactions.csv";
    private static final DateTimeFormatter CSV_DATE_FORMAT = DateTimeFormatter.ofPattern("M/d/yyyy");

    public TransactionManager() {
        transactions = new ArrayList<>(); // ArrayList
        loadFromFile();
        for (Transaction t : transactions) {
            if (t.getId() >= nextId) nextId = t.getId() + 1;
        }
    }


    public Transaction addIncome(double amount, String category, String account, String note, LocalDate date)
            throws InvalidTransactionException {
        if (amount <= 0) throw new InvalidTransactionException("Income must be positive.");
        Income i = new Income(nextId++, amount, category, account, note, date);
        transactions.add(i);
        saveTransaction(i);
        return i;
    }

    private void saveTransaction(Transaction t) {
        File dir = new File(storageDir);
        if (!dir.exists()) dir.mkdirs(); // if direcotry nto exist

        boolean fileExists = new File(storagePath).exists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(storagePath, true))) {
            if (!fileExists) writer.println("id,type,amount,category,account,note,date"); // CSV header
            String type = t.isExpense() ? "Expense" : "Income";
            writer.printf("%d,%s,%.2f,%s,%s,%s,%s%n",
                    t.getId(), type, t.getAmount(), t.getCategory(),
                    t.getAccount(), t.getNote(), t.getDate().format(CSV_DATE_FORMAT));
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File f = new File(storagePath);
        if (!f.exists()) { // File does not exist
            transactions = new ArrayList<>();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(storagePath))) {
            transactions = new ArrayList<>();
            String line = reader.readLine(); // skip CSV header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 7) continue; // skip invalid lines

                long id = Long.parseLong(parts[0]);
                String type = parts[1];
                double amount = Double.parseDouble(parts[2]);
                String category = parts[3];
                String account = parts[4];
                String note = parts[5];
                LocalDate date = LocalDate.parse(parts[6], CSV_DATE_FORMAT);

                Transaction t;
                if (type.equalsIgnoreCase("Income"))
                    t = new Income(id, amount, category, account, note, date);
                else
                    continue; // skips other types

                transactions.add(t);
            }
        } catch (IOException e) {
            System.err.println("Failed to load transactions: " + e.getMessage());
            transactions = new ArrayList<>();
        }
    }
}
