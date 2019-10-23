package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.MealList;
import duke.model.Transaction;
import duke.model.TransactionList;
import duke.model.user.User;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.Scanner;

public class AddTransactionCommand extends Command {
    private Transaction transaction;

    public AddTransactionCommand(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user,  Scanner in, TransactionList transactions) throws DukeException {
        transactions.addTransaction(this.transaction, user);
        user.updateAccountBalance(transaction);
        storage.updateTransaction(transactions);
//        ArrayList<Transaction> transactionData = transactions.getTransactionTracker().get(this.transaction.getDate());
        ui.showTransactionAdded(this.transaction, user.getAccountBalance());
    }
}
