package com.bachatmitra.controller;

import com.bachatmitra.model.*;
import com.bachatmitra.view.TransactionView;
import java.time.LocalDate;
/*
 * BachatMitraController acts as the "Controller" in the MVC architecture.
 * - It connects the View (TransactionView) with the Model (TransactionManager).
 * - Displays menu options to the user and reads their input.
 * - Handles actions like adding income, adding expense, viewing all transactions, and exiting.
 * - Uses TransactionManager to perform operations and TransactionView to interact with the user.
 * - Runs in a loop until the user chooses to exit, while also handling errors gracefully.
 */
// It handles input, processes it, and updates output.
public class BachatMitraController {
    private TransactionManager manager;
    private TransactionView view;

    public BachatMitraController() {
        manager = new TransactionManager();
        view = new TransactionView();
    }

    public void run() {
        boolean running = true;
        while (running) { // running = true keeps the program going until the user chooses to exit.
            view.showMainMenu();
            int choice = view.readChoice();
            try {
                switch (choice) {
                    case 1:
                        double incomeAmt = view.readAmount();
                        String incomeCat = view.chooseIncomeCategory();
                        String incomeAcc = view.chooseAccount();
                        String incomeNote = view.readNote();
                        Transaction i = manager.addIncome(
                                incomeAmt, incomeCat, incomeAcc, incomeNote,
                                LocalDate.now().toString()
                        );
                        view.showTransactionAdded(i);
                        break;

                    case 2:
                        double expAmt = view.readAmount();
                        String expCat = view.chooseExpenseCategory();
                        String expAcc = view.chooseAccount();
                        String expNote = view.readNote();
                        Transaction e = manager.addExpense(
                                expAmt, expCat, expAcc, expNote,
                                LocalDate.now().toString()
                        );
                        view.showTransactionAdded(e);
                        break;

                    case 3:
                        view.showAllTransactions(manager.getAllTransactions());
                        break;
                    case 4: //newly added
                        view.showCategoryStatistics(manager);
                        break;
                    case 5:
                        view.showExitMessage();
                        running = false;
                        break;
                    default:
                        view.showError("Invalid choice.");
                }
            } catch (Exception ex) {
                view.showError(ex.getMessage());
            }
        }
    }
}
