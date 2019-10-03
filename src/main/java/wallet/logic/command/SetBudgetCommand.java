package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.record.Budget;
import wallet.storage.StorageManager;

import java.text.DateFormatSymbols;

/**
 * The SetCommand Class deals with the 'set' command.
 */
public class SetBudgetCommand extends Command {
    public static final String COMMAND_WORD = "set";
    public static final String MESSAGE_SET_BUDGET = " dollars is the budget set for ";
    public static final String MESSAGE_NOTE = "Note that to update your budget, simply set the budget for the same month and year again.";
    public static final String MESSAGE_USAGE = "Error in format for command."
            + "\nExample: " + COMMAND_WORD + "100 01/2019";

    private Budget budget = null;

    public SetBudgetCommand(Budget budget) {
        this.budget = budget;
    }

    @Override
    public boolean execute(Wallet wallet, StorageManager storageManager) {
        try {
            wallet.getBudgetList().addBudget(budget);
            System.out.println(budget.getAmount() + MESSAGE_SET_BUDGET + new DateFormatSymbols().getMonths()[budget.getMonth()-1] + " " + budget.getYear());
            System.out.println(MESSAGE_NOTE);
        } catch (Exception e) {
            System.out.println(MESSAGE_USAGE);
        }
        return false;
    }
}

