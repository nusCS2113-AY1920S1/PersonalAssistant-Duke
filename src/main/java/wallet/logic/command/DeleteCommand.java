package wallet.logic.command;

import wallet.logic.LogicManager;
import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.model.task.Task;
import wallet.ui.Ui;

/**
 * DeleteCommand class handles any command that involves deletion of Record objects.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_SUCCESS_DELETE_TASK = "Noted. I've removed this task:";
    public static final String MESSAGE_SUCCESS_DELETE_EXPENSE = "Noted. I've removed this expense:";
    public static final String MESSAGE_ERROR_DELETE_EXPENSE = "An error occurred when trying to delete expense.";
    public static final String MESSAGE_SUCCESS_DELETE_LOAN = "Noted. I've removed this loan:";
    public static final String MESSAGE_ERROR_DELETE_LOAN = "An error occurred when trying to delete loan.";
    public static final String MESSAGE_SUCCESS_DELETE_CONTACT = "Noted. I've removed this contact:";
    public static final String MESSAGE_ERROR_DELETE_CONTACT = "An error occurred when trying to delete contact.";
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
     * @return A boolean which indicates whether program terminates.
     */
    @Override
    public boolean execute(Wallet wallet) {
        switch (object) {
        case "task":
            Task task = wallet.getTaskList().getTask(index);
            wallet.getTaskList().deleteTask(index);
            System.out.println(MESSAGE_SUCCESS_DELETE_TASK);
            System.out.println(task.toString());
            break;

        case "expense":
            Expense expense = wallet.getExpenseList().deleteExpense(index);
            if (expense != null) {
                wallet.getExpenseList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_DELETE_EXPENSE);
                System.out.println(expense.toString());
            } else {
                System.out.println(MESSAGE_ERROR_DELETE_EXPENSE);
            }
            break;

        case "loan":
            Loan loan = wallet.getLoanList().deleteLoan(index);
            if (loan != null) {
                wallet.getLoanList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_DELETE_LOAN);
                Ui.printLoanTableHeaders();
                Ui.printLoanRow(loan);
                Ui.printLoanTableClose();
                if (!LogicManager.getWallet().getLoanList().checkUnsettledLoan()) {
                    LogicManager.getReminder().autoRemindStop();
                    System.out.println("Turning off auto reminders because all loans have been settled!");
                }
            } else {
                System.out.println(MESSAGE_ERROR_DELETE_LOAN);
            }
            break;

        case "contact":
            //Delete by Id not Array Index
            Contact contact = wallet.getContactList().deleteContact(index);
            if (contact != null) {
                wallet.getContactList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_DELETE_CONTACT);
                System.out.println(contact.toString());
            } else {
                System.out.println(MESSAGE_ERROR_DELETE_CONTACT);
            }
            break;

        default:
            break;
        }
        return false;
    }
}
