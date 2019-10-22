package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.ui.Ui;

import java.util.ArrayList;


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
            //@@author Xdecosee
            int index = wallet.getContactList().findIndexWithId(contact.getId());
            if (index != -1) {
                Contact currentContact = wallet.getContactList().getContact(index);
                ArrayList<Loan> loanList = wallet.getLoanList().getLoanList();
                if (contact.getName() != null) {
                    currentContact.setName(contact.getName());
                }
                //resetting detail
                if ("".equals(contact.getDetail())) {
                    currentContact.setDetail(null);
                } else if (contact.getDetail() != null) {
                    currentContact.setDetail(contact.getDetail());
                }
                //resetting phone number
                if ("".equals(contact.getPhoneNum())) {
                    currentContact.setPhoneNum(null);
                } else if (contact.getPhoneNum() != null) {
                    currentContact.setPhoneNum(contact.getPhoneNum());
                }
                wallet.getContactList().editContact(index, currentContact);
                wallet.getContactList().setModified(true);

                for (Loan l : loanList) {
                    if (l.getPerson().getId() == currentContact.getId()) {
                        int loanIndex = wallet.getLoanList().findIndexWithId(l.getId());
                        Loan toUpdate = wallet.getLoanList().getLoan(loanIndex);
                        toUpdate.setPerson(currentContact);
                        wallet.getLoanList().editLoan(loanIndex, toUpdate);
                        wallet.getLoanList().setModified(true);

                    }
                }


                System.out.println(MESSAGE_SUCCESS_EDIT_CONTACT);
                System.out.println(currentContact.toString());
            } else {
                System.out.println(MESSAGE_ERROR_COMMAND);
            }
            //@@author
        } else if (loan != null) {

            int index = wallet.getLoanList().findIndexWithId(loan.getId());

            if (index != -1) {
                wallet.getLoanList().editLoan(index, loan);
                wallet.getLoanList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_EDIT_LOAN);
                Ui.printLoanTableHeaders();
                Ui.printLoanRow(loan);
                Ui.printLoanTableClose();
            } else {
                System.out.println(MESSAGE_ERROR_COMMAND);
            }
        }
        return false;
    }
}
