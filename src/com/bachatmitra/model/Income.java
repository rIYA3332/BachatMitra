package com.bachatmitra.model;

public class Income extends Transaction {
    public Income(long id, double amount, String category, String account, String note, String date) {
        super(id, amount, category, account, note, date); // Calls super(...) to pass these values to the parent class Transaction constructor, which initializes the inherited fields.
    }

    @Override
    public boolean isExpense() {
        return false;
    }

    @Override
    public String toString() {

        return "Income " + super.toString();
    }
}
