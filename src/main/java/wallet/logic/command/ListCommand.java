package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.model.task.Task;
import wallet.storage.StorageManager;

/**
 * The ListCommand Class handles all list commands.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_LIST_TASKS = "Here are the tasks in your list:";
    public static final String MESSAGE_LIST_CONTACTS = "Here are the contacts in your list:";
    public static final String MESSAGE_LIST_EXPENSES = "Here are the expenses in your list:";
    public static final String MESSAGE_LIST_RECURRING_EXPENSES = "Here are the recurring expenses in your list:";
    public static final String MESSAGE_LIST_LOANS = "Here are the loans in your list:";
    public static final String MESSAGE_USAGE = "Error in format for command."
            + "\nExample: " + COMMAND_WORD + " all"
            + "\nExample: " + COMMAND_WORD + " expense"
            + "\nExample: " + COMMAND_WORD + " loan"
            + "\nExample: " + COMMAND_WORD + " task"
            + "\nExample: " + COMMAND_WORD + " recurring";

    private final String record;

    /**
     * Constructs a ListCommand object.
     *
     * @param record The Record object.
     */
    public ListCommand(String record) {
        this.record = record;
    }

    /**
     * Lists the Record objects in any list and returns false.
     *
     * @param wallet The Wallet object.
     * @param storageManager The StorageManager object.
     * @return False.
     */
    @Override
    public boolean execute(Wallet wallet, StorageManager storageManager) {
        boolean isListAll = false;
        int counter;
        switch (record) {
        case "recurring":
            counter = 1;
            System.out.println(MESSAGE_LIST_RECURRING_EXPENSES);
            for (Expense e : wallet.getExpenseList().getExpenseList()) {
                if (e.isRecurring()) {
                    System.out.println(counter + ". " + e.toString());
                    counter++;
                }
            }
            break;

        case "all":
            isListAll = true;
            //fallthrough

        case "task":
            counter = 1;
            System.out.println(MESSAGE_LIST_TASKS);
            for (Task t : wallet.getTaskList().getTaskList()) {
                System.out.println(counter + "." + t.toString());
                counter++;
            }
            if (!isListAll) {
                break;
            }
            //else fallthrough

        case "contact":
            counter = 1;
            System.out.println(MESSAGE_LIST_CONTACTS);
            for (Contact c : wallet.getContactList().getContactList()) {
                System.out.println(counter + "." + c.toString());
                counter++;
            }
            if (!isListAll) {
                break;
            }
            //else fallthrough

        case "expense":
            counter = 1;
            System.out.println(MESSAGE_LIST_EXPENSES);
            for (Expense e : wallet.getExpenseList().getExpenseList()) {
                System.out.println(counter + ". " + e.toString());
                counter++;
            }
            if (!isListAll) {
                break;
            }
            //else fallthrough

        case "loan":
            counter = 1;
            System.out.println(MESSAGE_LIST_LOANS);
            for (Loan l : wallet.getLoanList().getLoanList()) {
                System.out.println(counter + ". " + l.toString());
                counter++;
            }
            break;

        default:
            System.out.println(MESSAGE_USAGE);
        }
        return false;
    }
}
