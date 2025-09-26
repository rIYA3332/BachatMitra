package com.bachatmitra.model;

public class InvalidTransactionException extends Exception {
    public InvalidTransactionException(String message) { // It takes a String message as a parameter, which describes what went wrong.
        super(message); // super(message) calls the constructor of the parent class (Exception) and passes the message to it.
        // This allows the message to be displayed when the exception is thrown, e.g., "Income must be positive."
    }
}
