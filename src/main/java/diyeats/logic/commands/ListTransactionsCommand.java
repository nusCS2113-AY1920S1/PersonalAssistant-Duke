package diyeats.logic.commands;

import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Transaction;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

//@@author GaryStu
/**
 * ListTransactionsCommand is a public class that inherits from abstract class Command.
 * It lists all the transaction(s) that happens on a certain date.
 */
public class ListTransactionsCommand extends Command {

    /**
     * Constructor for ListTransactionCommand.
     */
    public ListTransactionsCommand() {
    }

    /**
     * Constructor for ListTransactionCommand.
     * @param date The date of the transactions to list.
     */
    public ListTransactionsCommand(LocalDate date) {
        if (date != null) {
            currentDate = date;
        }
    }

    /**
     * Constructor for ListTransactionCommand.
     * @param flag the flag to indicate whether the parser fails.
     * @param messageStr the error message concerned.
     */
    public ListTransactionsCommand(boolean flag, String messageStr) {
        this.isFail = flag;
        this.errorStr = messageStr;

        if (!this.isFail) {
            ui.showMessage(messageStr);
            ui.showMessage("Showing list of meals from " + currentDate.format(dateFormat) + " instead.");
        }
    }

    /**
     * Execute the ListTransaction command.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files.
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information.
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) {
        ui.showLine();
        ArrayList<Transaction> transactions = wallet.getTransactions().getTransactionList(currentDate);
        ui.showTransactions(transactions, currentDate, wallet);
        ui.showLine();
    }
}
