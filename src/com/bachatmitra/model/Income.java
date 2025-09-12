package com.bachatmitra.model;

import java.time.LocalDate;
/**
 *
 * @author Riya Bhatta
 */

/*
 * Incom class representing an income transaction
 * Inherits common properties from Transaction
 */

public class Income extends Transaction {
    private static final long serialVersionUID = 1L;

    public Income(long id, double amount, String category, String account, String note, LocalDate date) {
        super(id, amount, category, account, note, date);
    }

    @Override
    public boolean isExpense() { return false; }

    @Override
    public String toString() {
        return "Income " + super.toString();
    }
}

