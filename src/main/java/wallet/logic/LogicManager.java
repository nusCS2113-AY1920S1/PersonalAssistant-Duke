package wallet.logic;

import wallet.logic.command.Command;
import wallet.logic.parser.ParserManager;
import wallet.model.Wallet;
import wallet.model.WalletList;
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

//save wallet state first before doing command
//if my command modifies stuff, then i add the wallet object into the list

/**
 * The LogicManager Class handles the logic of Wallet.
 */
public class LogicManager {
    public static final String MESSAGE_ERROR_COMMAND = "An error encountered while executing command.";
    private static StorageManager storageManager;
    private ParserManager parserManager;
    private static Wallet wallet;
    private static Reminder reminder;
    private static ArrayList<String> commandHistory;
    private static WalletList walletList;
    private int state = 0;

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
        this.walletList = new WalletList();
        walletList.getWalletList().add(wallet);
    }

    /**
     * Executes the command and returns the result.
     *
     * @param fullCommand The full command input by user.
     * @return A boolean isExit.
     */
    public boolean execute(String fullCommand) {
        boolean isExit = false;
        StorageManager newStorageManager = new StorageManager();

        Wallet newWallet = newWallet = new Wallet(new BudgetList(newStorageManager.loadBudget()), new RecordList(),
                new ExpenseList(newStorageManager.loadExpense()),
                new ContactList(newStorageManager.loadContact()),
                new LoanList(newStorageManager.loadLoan()));
        try {
            Command command = parserManager.parseCommand(fullCommand);
            if (command != null) {
                if(!fullCommand.equals("undo")
                        && !fullCommand.equals("list loan")
                        && !fullCommand.equals("list contact")
                        && !fullCommand.equals("list expense")) {
                    //only valid if my current state is the same size as the walletlist
                    if(walletList.getState() == walletList.getWalletList().size()-1) {
                        walletList.getWalletList().add(newWallet);
                        state++;
                        walletList.setState(state);
                    } else {
                        System.out.println("Removing the unused branch");
                        removeUnusedNodes(walletList);
                        System.out.println("After removing unused branch, wallet looks like: ");
                        printWalletList(walletList.getWalletList());
                        System.out.println("**************************");
                        walletList.getWalletList().add(newWallet);
                        state = walletList.getState()+1;
                        walletList.setState(state);
                    }
                }
                isExit = command.execute(walletList.getWalletList().get(walletList.getState()));
                ExpenseParser.updateRecurringRecords(walletList.getWalletList().get(walletList.getState()));
                boolean isModified = newStorageManager.save(walletList.getWalletList().get(walletList.getState()));
                if (isModified) {
                    commandHistory.add(fullCommand);
                }
            } else {
                System.out.println(MESSAGE_ERROR_COMMAND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(MESSAGE_ERROR_COMMAND);
        }
        printWalletList(walletList.getWalletList());
        return isExit;
    }

    public void printWalletList(ArrayList<Wallet> walletList) {
        System.out.println("/**************************Wallet History********************************/");
        for(Wallet wallet: walletList) {
            System.out.println("wallet object: " + wallet);
            Ui.printLoanTable(wallet.getLoanList().getLoanList());
            //Ui.printContactTable(wallet.getContactList().getContactList());
            //Ui.printExpenseTable(wallet.getExpenseList().getExpenseList());
        }
    }

    public void removeUnusedNodes(WalletList walletList) {
        System.out.println("Removing unwanted wallet nodes");
        int maxState = walletList.getWalletList().size()-1;
        int currentState = walletList.getState();
        int loops = maxState - currentState;
        while (loops > 0) {
            walletList.getWalletList().remove(maxState);
            maxState--;
            loops--;
        }
        walletList.setState(walletList.getWalletList().size()-1);
        System.out.println("removeUnusedNodes... wallet state = " + walletList.getState());
    }

    /**
     * Gets the Wallet object.
     *
     * @return The Wallet object.
     */
    public static Wallet getWallet() {
        return wallet;
    }

    public static void setWallet(Wallet wallet1) {
        wallet = wallet1;
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

    public static WalletList getWalletList() {
        return walletList;
    }

    public static StorageManager getStorageManager() {
        return storageManager;
    }
}
