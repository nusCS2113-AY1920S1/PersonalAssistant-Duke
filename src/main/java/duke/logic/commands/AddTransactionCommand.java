package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.meal.MealList;
import duke.model.wallet.Transaction;
import duke.model.wallet.TransactionList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.Scanner;

public class AddTransactionCommand extends Command {
    private Transaction transaction;

    public AddTransactionCommand(Transaction transaction) {
        this.transaction = transaction;
    }

    public AddTransactionCommand(boolean flag, String message) {
        this.isFail = true;
        this.error = message;
    }
    @Override
    public void execute(MealList tasks, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        wallet.getTransactions().addTransaction(this.transaction);
        ui.showTransactionAdded(this.transaction, wallet.getAccountBalance());
        ui.showLine();
    }

    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {
    }
}
