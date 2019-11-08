package DIYeats.logic.commands;

import DIYeats.commons.exceptions.DukeException;
import DIYeats.model.meal.MealList;
import DIYeats.model.user.User;
import DIYeats.model.wallet.Transaction;
import DIYeats.model.wallet.Wallet;
import DIYeats.storage.Storage;

/**
 * AddTransactionCommand is a public class that inherits from abstract class Command.
 * An AddTransactionCommand is a command that stores the transaction information(cost and date)
 * within a Wallet object.
 */
public class AddTransactionCommand extends Command {
    private Transaction transaction;

    /**
     * Constructor for AddTransactionCommand.
     * @param transaction the transaction object to be stored within wallet
     */

    public AddTransactionCommand(Transaction transaction) {
        this.transaction = transaction;
    }

    public AddTransactionCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Executes the AddTransactionCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) throws DukeException {
        ui.showLine();
        wallet.getTransactions().addTransaction(this.transaction);
        wallet.updateAccountBalance(this.transaction);
        storage.updateTransaction(wallet);
        ui.showTransactionAdded(this.transaction, wallet.getAccountBalance());
        ui.showLine();
    }
}
