package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.record.Expense;
import wallet.model.contact.Contact;
import wallet.model.record.Loan;


/**
 * EditCommand Class deals with commands that involves
 * in editing a specific object
 * in the a specific list.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_USAGE = "Usage for edit command."
            + "\nExample: " + COMMAND_WORD + " expense 2 /a 4.50 /c food /r daily"
            + "\nExample: " + COMMAND_WORD + " expense 2 /d lunch /a 9 /c Food /r no";
    public static final String MESSAGE_SUCCESS_EDIT_EXPENSE = "Successfully edited this expense:";
    public static final String MESSAGE_SUCCESS_EDIT_CONTACT = "Successfully edited this contact:";
    public static final String MESSAGE_SUCCESS_EDIT_LOAN = "Successfully edited this loan:";
    public static final String MESSAGE_ERROR_COMMAND = "An error encountered while executing command.";

    private Expense expense;
    private Contact contact;
    private Loan loan;

    /**
     * Constructs the EditCommand object with Expense object.
     *
     * @param expense The Expense Object.
     */
    public EditCommand(Expense expense) {
        this.expense = expense;
    }

    /**
     * Constructs the EditCommand object with Contact object.
     *
     * @param contact The Contact Object.
     */
    public EditCommand(Contact contact) {
        this.contact = contact;
    }

    /**
     * Constructs the EditCommand object with Loan object.
     *
     * @param loan The Loan Object.
     */
    public EditCommand(Loan loan) {
        this.loan = loan;
    }

    @Override
    public boolean execute(Wallet wallet) {
        if (expense != null) {
            int index = wallet.getExpenseList().findIndexWithId(expense.getId());
            Expense currentExpense = wallet.getExpenseList().getExpense(index);
            if (expense.getRecFrequency() == null || !expense.getRecFrequency().equals("")) {
                currentExpense.setRecurring(expense.isRecurring());
                currentExpense.setRecFrequency(expense.getRecFrequency());
            }
            if (expense.getAmount() != 0.0) {
                currentExpense.setAmount(expense.getAmount());
            }
            if (expense.getCategory() != null) {
                currentExpense.setCategory(expense.getCategory());
            }
            if (expense.getDescription() != null) {
                currentExpense.setDescription(expense.getDescription());
            }
            wallet.getExpenseList().editExpense(index, currentExpense);
            wallet.getExpenseList().setModified(true);
            System.out.println(MESSAGE_SUCCESS_EDIT_EXPENSE);
            System.out.println(currentExpense.toString());
        } else if (contact != null) {

            int index = wallet.getContactList().findIndexWithId(contact.getId());
            if (index != -1) {
                Contact currentContact = wallet.getContactList().getContact(index);
                if (contact.getName() != null) {
                    currentContact.setName(contact.getName());
                }
                //resetting detail
                if (contact.getDetail().equals("")) {
                    currentContact.setDetail(null);
                } else if (contact.getDetail() != null) {
                    currentContact.setDetail(contact.getDetail());
                }
                //resetting phone number
                if (contact.getPhoneNum().equals("")) {
                    currentContact.setPhoneNum(null);
                } else if (contact.getPhoneNum() != null) {
                    currentContact.setPhoneNum(contact.getPhoneNum());
                }

                wallet.getContactList().editContact(index, currentContact);
                wallet.getContactList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_EDIT_CONTACT);
                System.out.println(currentContact.toString());
            } else {
                System.out.println(MESSAGE_ERROR_COMMAND);
            }
        } else if (loan != null) {

            int index = wallet.getLoanList().findIndexWithId(loan.getId());
            Loan currentLoan = wallet.getLoanList().getLoan(index);

            if (loan.getDescription() != currentLoan.getDescription()) {
                currentLoan.setDescription(loan.getDescription());
            }
            /*if (loan.getAmount() != 0.0) {
                currentLoan.setAmount(loan.getAmount());
            }
            if (loan.getCreatedDate() != currentLoan.getCreatedDate()) {
                currentLoan.setCreatedDate(loan.getCreatedDate());
            }
            if (loan.getIsLend() != currentLoan.getIsLend()) {
                currentLoan.setIsLend(loan.getIsLend());
            }
            if (loan.getPerson() != currentLoan.getPerson()) {
                currentLoan.setPerson(loan.getPerson());
            }*/

            wallet.getLoanList().editLoan(index, currentLoan);
            wallet.getLoanList().setModified(true);
            System.out.println(MESSAGE_SUCCESS_EDIT_LOAN);
            System.out.println(currentLoan.toString());
            } else {
            System.out.println(MESSAGE_ERROR_COMMAND);
        }
        return false;
    }
}
