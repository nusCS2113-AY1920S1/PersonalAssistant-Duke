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
import wallet.storage.StorageManager;
import wallet.reminder.Reminder;
import wallet.ui.Ui;

import java.util.ArrayList;
import java.util.Stack;

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
    private static Stack<Wallet> walletStack;

    /**
     * Constructs a LogicManager object.
     */
    public LogicManager() {
        this.storageManager = new StorageManager();
        this.wallet = new Wallet(new BudgetList(storageManager.loadBudget()), new RecordList(),
                new ExpenseList(storageManager.loadExpense()),
                new ContactList(storageManager.loadContact()),
                new LoanList(storageManager.loadLoan()));
        this.parserManager = new ParserManager();
        this.reminder = new Reminder();
        this.commandHistory = new ArrayList<>();
        this.walletStack = new Stack<>();
    }

    /**
     * Executes the command and returns the result.
     *
     * @param fullCommand The full command input by user.
     * @return A boolean isExit.
     */
    public boolean execute(String fullCommand) {
        boolean isExit = false;
        //System.out.println("Wallet state at start for loans:");
        //Ui.printLoanTable(walletStack.peek().getLoanList().getLoanList());
        try {
            Command command = parserManager.parseCommand(fullCommand);
            if (command != null) {
                isExit = command.execute(wallet);
                ExpenseParser.updateRecurringRecords(wallet);
                boolean isModified = storageManager.save(wallet);
                if (isModified) {
                    commandHistory.add(fullCommand);
                    walletStack.push(wallet);
                }
            } else {
                System.out.println(MESSAGE_ERROR_COMMAND);
            }

        } catch (Exception e) {
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

    /**
     * Gets the ArrayList commandHistory.
     *
     * @return The ArrayList commandHistory.
     */
    public static ArrayList<String> getCommandHistory() {
        return commandHistory;
    }

    public static Stack<Wallet> getWalletStack() {
        return walletStack;
    }

}
