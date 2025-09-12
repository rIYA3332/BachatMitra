package com.bachatmitra.model;

/**
 *
 * @author Riya Bhatta
 */

/*
 * Custom exception for invalid transactions
 * Thrown when a transaction is not valid
 */

public class InvalidTransactionException extends Exception {
    public InvalidTransactionException(String message) {
        super(message);
    }
}

