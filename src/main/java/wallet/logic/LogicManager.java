package wallet.logic;

import wallet.model.contact.ContactList;
import wallet.logic.command.Command;
import wallet.logic.parser.ParserManager;
import wallet.model.Wallet;
import wallet.model.record.ExpenseList;
import wallet.model.record.LoanList;
import wallet.model.record.RecordList;
import wallet.storage.Storage;
import wallet.model.task.ScheduleList;
import wallet.model.task.TaskList;

/**
 * The LogicManager Class handles the logic of Wallet.
 */
public class LogicManager {
    private final Storage storage;
    private final ParserManager parserManager;
    private final Wallet wallet;

    /**
     * Constructs a LogicManager object.
     */
    public LogicManager(Storage storage) {
        this.storage = storage;
        this.wallet = new Wallet(new RecordList(), new ExpenseList(), new ContactList(),
                new TaskList(storage.loadFile()), new ScheduleList(), new LoanList());
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
            isExit = command.execute(wallet, storage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error encountered while executing command.");
        }

        return isExit;
    }
}
