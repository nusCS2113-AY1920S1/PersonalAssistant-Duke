package wallet.logic.command;

import wallet.contact.Contact;
import wallet.model.Wallet;
import wallet.record.Expense;
import wallet.storage.Storage;
import wallet.task.Task;

public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_SUCCESS_ADD_TASK = "Got it. I've added this task:";
    public static final String MESSAGE_SUCCESS_ADD_CONTACT = "Got it. I've added this contact:";
    public static final String MESSAGE_SUCCESS_ADD_EXPENSE = "Got it. I've added this expense:";

    private Expense expense = null;
    private Task task = null;
    private Contact contact = null;

    public AddCommand(Expense expense) {
        this.expense = expense;
    }

    public AddCommand(Task task) {
        this.task = task;
    }

    public AddCommand(Contact contact) {
        this.contact = contact;
    }

    @Override
    public boolean execute(Wallet wallet, Storage storage) {
        if (expense != null) {
            wallet.getExpenseList().addExpense(expense);
            System.out.println(MESSAGE_SUCCESS_ADD_EXPENSE);
            System.out.println(expense.toString());
        }
        if (task != null) {
            wallet.getTaskList().addTask(task);
            System.out.println(MESSAGE_SUCCESS_ADD_TASK);
            System.out.println(task.toString());
            storage.writeFile(task);
        }
        if (contact != null) {
            wallet.getContactList().addContact(contact);
            System.out.println(MESSAGE_SUCCESS_ADD_CONTACT);
            System.out.println(contact.toString());
        }

        return false;
    }
}
