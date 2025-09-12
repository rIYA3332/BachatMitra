// encapsulation
package com.bachatmitra.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Riya Bhatta
 */

public abstract class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    protected long id;
    protected double amount;
    protected String category;
    protected String account;
    protected String note;
    protected LocalDate date;

    // Constructor
    public Transaction(long id, double amount, String category, String account, String note, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.account = account;
        this.note = note;
        this.date = date;
    }

    // Getters
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

    public LocalDate getDate() {
        return date;
    }

    // Setters
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Abstract method to determine if transaction is an expense
    public abstract boolean isExpense();

    // String representation of transaction
    @Override
    public String toString() {
        return String.format("[%d] %s | %.2f | %s | %s | %s",
                id, date, amount, category, account, note);
    }
}

