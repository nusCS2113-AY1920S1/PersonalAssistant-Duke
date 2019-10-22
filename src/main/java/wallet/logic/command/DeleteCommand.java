package wallet.logic.command;

import wallet.logic.LogicManager;
import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.ui.Ui;

/**
 * DeleteCommand class handles any command that involves deletion of Record objects.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_SUCCESS_DELETE_EXPENSE = "Noted. I've removed this expense:";
    public static final String MESSAGE_ERROR_DELETE_EXPENSE = "An error occurred when trying to delete expense.";
    public static final String MESSAGE_SUCCESS_DELETE_LOAN = "Noted. I've removed this loan:";
    public static final String MESSAGE_ERROR_DELETE_LOAN = "An error occurred when trying to delete loan.";
    public static final String MESSAGE_SUCCESS_DELETE_CONTACT = "Noted. I've removed this contact:";
    public static final String MESSAGE_ERROR_DELETE_CONTACT = "An error occurred when trying to delete contact.";
    private String object;
    private int id;

    /**
     * Constructs a DeleteCommand object.
     *
     * @param object A String object for deletion.
     * @param id The id of the object in a specified list.
     */
    public DeleteCommand(String object, int id) {
        this.object = object;
        this.id = id;
    }

    /**
     * Deletes the specific Record object by entry id
     * and returns false.
     *
     * @param wallet The Wallet object.
     * @return A boolean which indicates whether program terminates.
     */
    @Override
    public boolean execute(Wallet wallet) {
        switch (object) {
        case "expense":
            //@@author
            Expense expense = wallet.getExpenseList().deleteExpense(id);
            if (expense != null) {
                wallet.getExpenseList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_DELETE_EXPENSE);
                System.out.println(expense.toString());
            } else {
                System.out.println(MESSAGE_ERROR_DELETE_EXPENSE);
            }
            break;
        //@@author

        case "loan":
            Loan loan = wallet.getLoanList().deleteLoan(id);
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

            //@@author Xdecosee
            Contact contact = wallet.getContactList().deleteContact(id);
            if (contact != null) {
                wallet.getContactList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_DELETE_CONTACT);
                System.out.println(contact.toString());
            } else {
                System.out.println(MESSAGE_ERROR_DELETE_CONTACT);
            }
            break;
            //@@author

        default:
            break;
        }
        return false;
    }
}
