package wallet.logic;

import wallet.logic.command.Command;
import wallet.logic.parser.ParserManager;
import wallet.model.Wallet;
import wallet.model.contact.ContactList;
import wallet.logic.parser.ExpenseParser;
import wallet.model.record.BudgetList;
import wallet.model.record.ExpenseList;
import wallet.model.record.LoanList;
import wallet.model.record.RecordList;
import wallet.model.task.ScheduleList;
import wallet.model.task.TaskList;
import wallet.storage.StorageManager;
import wallet.reminder.Reminder;

import java.util.ArrayList;

/**
 * The LogicManager Class handles the logic of Wallet.
 */
public class LogicManager {
    public static final String MESSAGE_ERROR_COMMAND = "An error encountered while executing command.";
    private StorageManager storageManager;
    private ParserManager parserManager;
    private static Wallet wallet;
    private static Reminder reminder;
    private static ArrayList<String> commandHistory;

    /**
     * Constructs a LogicManager object.
     */
    public LogicManager() {
        this.storageManager = new StorageManager();
        this.wallet = new Wallet(new BudgetList(storageManager.loadBudget()), new RecordList(),
                new ExpenseList(storageManager.loadExpense()),
                new ContactList(storageManager.loadContact()), new TaskList(storageManager.loadTask()),
                new ScheduleList(), new LoanList(storageManager.loadLoan()));
        this.parserManager = new ParserManager();
        this.reminder = new Reminder();
        this.commandHistory = new ArrayList<String>();
    }

    /**
     * Executes the command and returns the result.
     *
     * @param fullCommand The full command input by user.
     * @return A boolean isExit.
     */
    public boolean execute(String fullCommand) {
        boolean isExit = false;
        try {
            Command command = parserManager.parseCommand(fullCommand);
            if (command != null) {
                isExit = command.execute(wallet);
                ExpenseParser.updateRecurringRecords(wallet);
                storageManager.save(wallet);
            } else {
                //System.out.println(MESSAGE_ERROR_COMMAND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(MESSAGE_ERROR_COMMAND);
        }

        return isExit;
    }

    /**
     * Gets the Wallet object.
     *
     * @return The Wallet object.
     */
    public static Wallet getWallet() {
        return wallet;
    }

    /**
     * Gets the Reminder object.
     *
     * @return The Reminder object.
     */
    public static Reminder getReminder() {
        return reminder;
    }

    public static ArrayList<String> getCommandHistory() {
        return commandHistory;
    }
}
