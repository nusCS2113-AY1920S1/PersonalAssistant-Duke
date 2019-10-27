package duke.logic.commands;

import duke.ui.InputHandler;
import duke.commons.exceptions.DukeException;
import duke.model.MealList;
import duke.model.Transaction;
import duke.model.TransactionList;
import duke.model.user.User;
import duke.storage.Storage;
import duke.ui.Ui;

public class AddTransactionCommand extends Command {
    private Transaction transaction;

    public AddTransactionCommand(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user,
                        InputHandler in, TransactionList transactions) throws DukeException {
        transactions.addTransaction(this.transaction);
        user.updateAccountBalance(transaction);
        storage.updateTransaction(transactions);
        storage.saveUser(user);
        ui.showTransactionAdded(this.transaction, user.getAccountBalance());
    }
}
