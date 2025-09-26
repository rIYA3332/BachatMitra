package com.bachatmitra.model;

public class Expense extends Transaction {
    public Expense(long id, double amount, String category, String account, String note, String date) {
        super(id, amount, category, account, note, date);
    }

    @Override
    public boolean isExpense() { return true; }

    @Override
    public String toString() {
        return "Expense " + super.toString();
    }
}
