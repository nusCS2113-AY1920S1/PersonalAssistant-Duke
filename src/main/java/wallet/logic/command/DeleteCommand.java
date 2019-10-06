package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.record.Expense;
import wallet.model.task.Task;
import wallet.storage.StorageManager;

/**
 * DeleteCommand class handles any command that involves deletion of Record objects.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_SUCCESS_DELETE_TASK = "Noted. I've removed this task:";
    public static final String MESSAGE_SUCCESS_DELETE_EXPENSE = "Noted. I've removed this expense:";
    public static final String MESSAGE_ERROR_DELETE_EXPENSE = "An error occurred when trying to delete expense.";

    private String object;
    private int index;

    /**
     * Constructs a DeleteCommand object.
     *
     * @param object A String object for deletion.
     * @param index The index of the object in a specified list.
     */
    public DeleteCommand(String object, int index) {
        this.object = object;
        this.index = index;
    }

    /**
     * Deletes the specific Record object and returns false.
     *
     * @param wallet The Wallet object.
     * @param storageManager The StorageManager object.
     * @return A boolean which indicates whether program terminates.
     */
    @Override
    public boolean execute(Wallet wallet, StorageManager storageManager) {
        switch (object) {
        case "task":
            Task task = wallet.getTaskList().getTask(index);
            wallet.getTaskList().deleteTask(index);
            storageManager.deleteTask(wallet.getTaskList().getTaskList(), index);
            System.out.println(MESSAGE_SUCCESS_DELETE_TASK);
            System.out.println(task.toString());
            break;

        case "expense":
            Expense expense = wallet.getExpenseList().deleteExpense(index);
            if (expense != null) {
                System.out.println(MESSAGE_SUCCESS_DELETE_EXPENSE);
                System.out.println(expense.toString());
            } else {
                System.out.println(MESSAGE_ERROR_DELETE_EXPENSE);
            }
            break;

        default:
            break;
        }
        return false;
    }
}
