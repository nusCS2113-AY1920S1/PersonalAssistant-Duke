package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Budget;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.ui.Ui;

import java.text.DateFormatSymbols;
import java.time.LocalDate;

/**
 * The AddCommand Class which extends Command.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = "Usage for add command."
            + "\nThe options for recurrence rate (/r) are \\\"daily, weekly or monthly\\\""
            + "\nExample: " + COMMAND_WORD + "expense lunch $5 Food /on 20/02/2019"
            + "\nExample: " + COMMAND_WORD + "expense phone bills $100 bills /on 10/10/2019 /r monthly"
            + "\nExample: " + COMMAND_WORD + "expense dinner $10 Food";
    public static final String MESSAGE_SUCCESS_ADD_TASK = "Got it. I've added this task:";
    public static final String MESSAGE_SUCCESS_ADD_CONTACT = "Got it. I've added this contact:";
    public static final String MESSAGE_SUCCESS_ADD_EXPENSE = "Got it. I've added this expense:";
    public static final String MESSAGE_SUCCESS_ADD_LOAN = "Got it. I've added this loan:";
    public static final String MESSAGE_NEW_BUDGET = " is your new budget for ";
    public static final String MESSAGE_EXCEED_BUDGET = "Your budget has exceeded!!";
    public static final String MESSAGE_REACH_BUDGET = "You have reached your budget!!";
    public static final String MESSAGE_ERROR_ADD_EXPENSE = "Error in format when adding expense.\n"
            + MESSAGE_USAGE;

    private Expense expense = null;
    private Contact contact = null;
    private Loan loan = null;

    /**
     * Constructs the AddCommand object with Expense object.
     *
     * @param expense The Expense Object.
     */
    public AddCommand(Expense expense) {
        this.expense = expense;
    }

    /**
     * Constructs the AddCommand object with Contract object.
     *
     * @param contact The Contract object.
     */
    public AddCommand(Contact contact) {
        this.contact = contact;
    }

    /**
     * Constructs the AddCommand object with Loan object.
     *
     * @param loan The Loan object.
     */
    public AddCommand(Loan loan) {
        this.loan = loan;
    }

    /**
     * Add the Record objects into their respective lists and returns false.
     *
     * @param wallet The Wallet object.
     * @return a boolean variable which indicates
     */
    @Override
    public boolean execute(Wallet wallet) {
        if (expense != null) {
            wallet.getExpenseList().addExpense(expense);
            LocalDate date = expense.getDate();
            for (Budget b : wallet.getBudgetList().getBudgetList()) {
                if (b.getMonth() == date.getMonthValue() && b.getYear() == date.getYear()) {
                    b.setAmount(b.getAmount() - expense.getAmount());
                    wallet.getBudgetList().setModified(true);
                    if (b.getAmount() < 0) {
                        System.out.println(MESSAGE_EXCEED_BUDGET);
                    } else if (b.getAmount() == 0) {
                        System.out.println(MESSAGE_REACH_BUDGET);
                    }
                    System.out.println("$" + b.getAmount() + MESSAGE_NEW_BUDGET
                            + new DateFormatSymbols().getMonths()[b.getMonth() - 1] + " " + b.getYear());
                }
            }
            wallet.getRecordList().addRecord(expense);
            wallet.getExpenseList().setModified(true);
            System.out.println(MESSAGE_SUCCESS_ADD_EXPENSE);
            System.out.println(expense.toString());
        }
        if (contact != null) {
            wallet.getContactList().addContact(contact);
            wallet.getContactList().setModified(true);
            System.out.println(MESSAGE_SUCCESS_ADD_CONTACT);
            System.out.println(contact.toString());
        }
        if (loan != null) {
            wallet.getLoanList().addLoan(loan);
            wallet.getRecordList().addRecord(loan);
            wallet.getLoanList().setModified(true);
            System.out.println(MESSAGE_SUCCESS_ADD_LOAN);
            Ui.printLoanTableHeaders();
            Ui.printLoanRow(loan);
            Ui.printLoanTableClose();
        }

        return false;
    }
}
