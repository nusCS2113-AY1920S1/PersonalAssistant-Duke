package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Transaction;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

//@@author GaryStu
public class ListTransactionsCommand extends Command {
    private String sortBy = "default";

    public ListTransactionsCommand() {
    }

    public ListTransactionsCommand(LocalDate date) {
        if (date != null) {
            currentDate = date;
        }
    }

    public ListTransactionsCommand(boolean flag, String messageStr) {
        this.isFail = flag;
        this.errorStr = messageStr;

        if (!this.isFail) {
            ui.showMessage(messageStr);
            ui.showMessage("Showing list of meals from " + currentDate.format(dateFormat) + " instead.");
        }
    }

    public ListTransactionsCommand(LocalDate date, String sortBy) {
        this(date);
        this.sortBy = sortBy;
    }

    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) throws ProgramException {
        ui.showLine();
        ArrayList<Transaction> transactions = wallet.getTransactions().getTransactionList(currentDate);
        ui.showTransactions(transactions, currentDate);
    }
}
