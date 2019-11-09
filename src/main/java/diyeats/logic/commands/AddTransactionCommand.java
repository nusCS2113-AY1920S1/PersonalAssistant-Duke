package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Transaction;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

//@@author GaryStu
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
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) throws ProgramException {
        ui.showLine();
        wallet.getTransactions().addTransaction(this.transaction);
        wallet.updateAccountBalance(this.transaction);
        storage.updateTransaction(wallet);
        ui.showTransactionAdded(this.transaction, wallet.getAccountBalance());
        ui.showLine();
    }
}
