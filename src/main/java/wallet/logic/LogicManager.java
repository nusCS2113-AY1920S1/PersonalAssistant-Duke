package wallet.logic;

import wallet.model.contact.ContactList;
import wallet.logic.command.Command;
import wallet.logic.parser.ParserManager;
import wallet.model.Wallet;
import wallet.model.record.*;
import wallet.storage.Storage;
import wallet.model.task.ScheduleList;
import wallet.model.task.TaskList;
import wallet.storage.StorageManager;
import wallet.storage.TaskStorage;

/**
 * The LogicManager Class handles the logic of Wallet.
 */
public class LogicManager {
    private final StorageManager storageManager;
    private final ParserManager parserManager;
    private final Wallet wallet;

    /**
     * Constructs a LogicManager object.
     */
    public LogicManager() {
        this.storageManager = new StorageManager();
        this.wallet = new Wallet(new RecordList(), new ExpenseList(storageManager.loadExpense()),new ContactList(),
                new TaskList(storageManager.loadTask()), new ScheduleList(), new LoanList(storageManager.loadLoan()));
        this.parserManager = new ParserManager();
    }

    /**
     * Executes the command and returns the result.
     * @param fullCommand The full command input by user.
     * @return
     */
    public boolean execute(String fullCommand) {
        boolean isExit = false;
        try {
            Command command = parserManager.parseCommand(fullCommand);
            isExit = command.execute(wallet, storageManager);
            ExpenseParser.updateRecurringRecords(wallet, storageManager);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error encountered while executing command.");
        }

        return isExit;
    }
}
