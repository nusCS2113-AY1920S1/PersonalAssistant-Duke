package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Transaction;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author GaryStu
/**
 * DeleteTransactionCommand is a public class that inherits from abstract class Command.
 * A DeleteTransactionCommand object encapsulates the index of transaction and date of the transaction to be deleted.
 */
public class DeleteTransactionCommand extends Command {
    private int index;
    private static Logger logger = Logger.getLogger(DeleteTransactionCommand.class.getName());

    /**
     * Constructor for DeleteTransactionCommand when only index is specified.
     * @param indexStr the index of the transaction to be deleted.
     */
    public DeleteTransactionCommand(String indexStr) {
        try {
            this.index = Integer.parseInt(indexStr.trim());
            logger.log(Level.FINE, "index is a valid int");
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Unable to parse input " + indexStr);
        }
    }

    /**
     * Constructor for DeleteTransactionCommand when both index and date are specified.
     * @param indexStr the index of the transaction to be deleted.
     * @param dateStr Date in which the transaction is to be deleted.
     */
    public DeleteTransactionCommand(String indexStr, String dateStr) {
        this(indexStr);
        if (!dateStr.isBlank()) {
            try {
                currentDate = LocalDate.parse(dateStr, dateFormat);
                logger.log(Level.FINE, "currentDate is parsable");
            } catch (DateTimeParseException e) {
                logger.log(Level.WARNING, "the date " + currentDate + " is not parsable");
                ui.showMessage("Unable to parse input" + dateStr + " as a date. ");
            }
        }
    }

    public DeleteTransactionCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Execute the DeleteTransactionCommand.
     * @param meals the MealList object in which the meals are supposed to be added.
     * @param storage the storage object that handles all reading and writing to files.
     * @param user the object that handles all user data.
     * @param wallet the wallet object that stores transaction information.
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        if (index <= 0 || index > wallet.getTransactions().getTransactionList(currentDate).size()) {
            logger.log(Level.WARNING, "the index " + index + " is out of bound");
            ui.showMessage("Index provided out of bounds for list of transactions on " + currentDate);
        } else {
            Transaction deletedTransaction = wallet.getTransactions().delete(currentDate, index);
            BigDecimal transactionAmount = deletedTransaction.getTransactionAmount();
            if (deletedTransaction.getType().equals("PAY")) {
                wallet.getAccount().deposit(transactionAmount);
            } else if (deletedTransaction.getType().equals("DEP")) {
                wallet.getAccount().withdraw(transactionAmount);
            }
            ui.showDeletedTransaction(deletedTransaction, wallet.getTransactions().getTransactionList(currentDate));
            try {
                storage.writeTransaction(wallet);
            } catch (ProgramException e) {
                ui.showMessage(e.getMessage());
            }
        }
        ui.showLine();
    }
}
