package com.bachatmitra.model;

// serialization allows o convert object's state in byte stream - which can be stored in a file
// this is important as computers only understand binary(0 and 1)
import java.io.Serializable; // Imports Serializable interface, allowing instances of this class (and subclasses) to be saved to a file or sent over a network.

/*
 * Abstract class representing a financial transaction (Income or Expense).
 * Implements Serializable for saving/loading objects, holds common fields,
 * provides getters, and requires subclasses to define isExpense().
 */

public abstract class Transaction implements Serializable {
    /*
    * serialVersionUID is a unique identifier for a Serializable class in Java.
    * When you serialize an object, Java saves both: The object’s state (its variables) and The class version information (serialVersionUID helps here)
    * If later the class changes (e.g., you add/remove fields) and you try to deserialize old objects:
    * Java checks the serialVersionUID.
    * If it matches, deserialization succeeds.
    * If it doesn’t match, Java throws an InvalidClassException because the old object might not fit the new class.
     */
    private static final long serialVersionUID = 1L;

    protected long id; // protected: accessible in subclasses (Income/Expense) but not outside the package
    protected double amount;
    protected String category;
    protected String account;
    protected String note;
    protected String date;

    public Transaction(long id, double amount, String category, String account, String note, String date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.account = account;
        this.note = note;
        this.date = date;
    }

    // Getter methods: allow other classes to read the values of these fields
    public long getId() {
        return id;
    }
    public double getAmount() {
        return amount;
    }
    public String getCategory() {
        return category;
    }
    public String getAccount() {
        return account;
    }
    public String getNote() {
        return note;
    }
    public String getDate() {
        return date;
    }
    //Abstract method: must be implemented in subclasses.
    //Determines whether the transaction is an expense (true) or not (false).
    public abstract boolean isExpense();

    @Override
    public String toString() { // overrides toString() method
        return String.format("[%d] %s | %.2f | %s | %s | %s", // Returns a formatted string representation of the transaction:
                id, date, amount, category, account, note);
    }
}
