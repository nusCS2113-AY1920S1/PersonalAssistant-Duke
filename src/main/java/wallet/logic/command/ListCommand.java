//@@author matthewng1996

package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.ui.Ui;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The ListCommand Class handles all list commands.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_LIST_CONTACTS = "Here are the contacts in your list:";
    public static final String MESSAGE_LIST_EXPENSES = "Here are the expenses in your list:";
    public static final String MESSAGE_LIST_NO_EXPENSES = "There are no expenses for ";
    public static final String MESSAGE_LIST_NO_LOANS = "There are no loans for ";
    public static final String MESSAGE_LIST_RECURRING_EXPENSES = "Here are the recurring expenses in your list:";
    public static final String MESSAGE_LIST_LOANS = "Here are the loans in your list:";
    public static final String MESSAGE_USAGE = "Error in format for command."
        + "\nExample: " + COMMAND_WORD + " all"
        + "\nExample: " + COMMAND_WORD + " expense"
        + "\nExample: " + COMMAND_WORD + " expense /sortby date"
        + "\nExample: " + COMMAND_WORD + " loan"
        + "\nExample: " + COMMAND_WORD + " task"
        + "\nExample: " + COMMAND_WORD + " recurring";
    public static final String MESSAGE_LIST_ALL_SORT = "list all can only be sorted by date.";
    public static final String MESSAGE_LOANS_SORT = "loans can only be sorted by date, lend or borrow";
    public static final String MESSAGE_EXPENSE_SORT = "expenses can only be sorted by date and category.";
    public static final String MESSAGE_RECURRING_SORT = "recurring can only be sorted by date and category.";
    public static final String MESSAGE_CONTACT_SORT = "contacts can only be sorted by name (A to Z).";

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
     * @return False.
     */
    @Override
    public boolean execute(Wallet wallet) {
        if (record.contains("recurring")) {
            if ("recurring".equals(record)) {
                System.out.println(MESSAGE_LIST_RECURRING_EXPENSES);
                ArrayList<Expense> recList = wallet.getExpenseList().getRecurringExpense();
                Ui.printExpenseTable(recList);
            } else {
                String[] arguments = record.split(" ", 3);
                if (arguments[1].equals("/sortby")) {
                    if (arguments[2].equals("date")) {
                        ArrayList<Expense> expenseList = wallet.getExpenseList().getSortedRecurringExpenseDate();
                        System.out.println(MESSAGE_LIST_EXPENSES);
                        Ui.printExpenseTable(expenseList);
                    } else if (arguments[2].equals("category")) {
                        ArrayList<Expense> expenseList = wallet.getExpenseList().getSortedRecurringExpenseCategory();
                        System.out.println(MESSAGE_LIST_EXPENSES);
                        Ui.printExpenseTable(expenseList);
                    } else {
                        System.out.println(MESSAGE_RECURRING_SORT);
                    }
                } else {
                    System.out.println(MESSAGE_USAGE);
                }
            }
        } else if (record.contains("all")) {
            if ("all".equals(record)) {
                ArrayList<Loan> loanList = wallet.getLoanList().getLoanList();
                Ui.printLoanTable(loanList);
                ArrayList<Expense> expenseList = wallet.getExpenseList().getExpenseList();

                System.out.println(MESSAGE_LIST_CONTACTS);
                ArrayList<Contact> contactList = wallet.getContactList().getContactList();
                Ui.printContactTable(contactList);

                System.out.println(MESSAGE_LIST_EXPENSES);
                Ui.printExpenseTable(expenseList);
            } else {
                String[] arguments = record.split(" ", 3);
                if (arguments[1].equals("/sortby")) {
                    if (arguments[2].equals("date")) {
                        ArrayList<Loan> loanList = wallet.getLoanList().sortByDate();
                        Ui.printLoanTable(loanList);

                        System.out.println(MESSAGE_LIST_CONTACTS);
                        ArrayList<Contact> contactList = wallet.getContactList().getContactList();
                        Ui.printContactTable(contactList);

                        ArrayList<Expense> expenseSortedList = wallet.getExpenseList().sortByDate();
                        System.out.println(MESSAGE_LIST_EXPENSES);
                        Ui.printExpenseTable(expenseSortedList);
                    } else {
                        System.out.println(MESSAGE_LIST_ALL_SORT);
                    }
                } else {
                    System.out.println(MESSAGE_USAGE);
                }
            }
        } else if (record.contains("contact")) {
            //@@author Xdecosee
            ArrayList<Contact> contactList = wallet.getContactList().getContactList();
            if ("contact".equals(record)) {
                System.out.println(MESSAGE_LIST_CONTACTS);
                Ui.printContactTable(contactList);
            } else {
                String[] arguments = record.split(" ", 3);
                if (arguments[1].equals("/sortby")) {
                    if (arguments[2].equals("name")) {
                        ArrayList<Contact> sortedContacts = wallet.getContactList().sortByName();
                        System.out.println(MESSAGE_LIST_CONTACTS);
                        Ui.printContactTable(sortedContacts);
                    } else {
                        System.out.println(MESSAGE_CONTACT_SORT);
                    }
                } else {
                    System.out.println(MESSAGE_USAGE);
                }
            }

            //@@author
        } else if (record.contains("loan")) {
            if ("loan".equals(record)) {
                //@@author A0171206R
                ArrayList<Loan> loanList = wallet.getLoanList().getLoanList();
                Ui.printLoanTable(loanList);
                //@@author
            } else {
                String[] arguments = record.split(" ", 3);
                if (arguments[1].equals("/sortby")) {
                    if (arguments[2].equals("date")) {
                        ArrayList<Loan> loanList = wallet.getLoanList().sortByDate();
                        Ui.printLoanTable(loanList);
                    } else if (arguments[2].equals("lend")) {
                        ArrayList<Loan> loanList = wallet.getLoanList().sortByLend();
                        Ui.printLoanTable(loanList);
                    } else if (arguments[2].equals("borrow")) {
                        ArrayList<Loan> loanList = wallet.getLoanList().sortByBorrow();
                        Ui.printLoanTable(loanList);
                    } else {
                        System.out.println(MESSAGE_LOANS_SORT);
                    }
                } else {
                    System.out.println(MESSAGE_USAGE);
                }
            }
        } else if (record.contains("expense")) {
            if ("expense".equals(record)) {
                ArrayList<Expense> expenseList = wallet.getExpenseList().getExpenseList();
                System.out.println(MESSAGE_LIST_EXPENSES);
                Ui.printExpenseTable(expenseList);
            } else {
                String[] arguments = record.split(" ", 3);
                if (arguments[1].equals("/sortby")) {
                    if (arguments[2].equals("date")) {
                        ArrayList<Expense> expenseList = wallet.getExpenseList().sortByDate();
                        System.out.println(MESSAGE_LIST_EXPENSES);
                        Ui.printExpenseTable(expenseList);
                    } else if (arguments[2].equals("category")) {
                        ArrayList<Expense> expenseList = wallet.getExpenseList().sortByCategory();
                        System.out.println(MESSAGE_LIST_EXPENSES);
                        Ui.printExpenseTable(expenseList);
                    } else {
                        System.out.println(MESSAGE_EXPENSE_SORT);
                    }
                } else {
                    System.out.println(MESSAGE_USAGE);
                }
            }
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(record.trim(), formatter);

            ArrayList<Expense> expensesList = new ArrayList<Expense>();
            ArrayList<Loan> loansList = new ArrayList<>();

            if (date != null) {
                if (wallet.getExpenseList().getExpenseList().size() != 0
                        || wallet.getLoanList().getLoanList().size() != 0) {
                    if (wallet.getExpenseList().getExpenseList().size() != 0) {
                        for (Expense e : wallet.getExpenseList().getExpenseList()) {
                            if (e.getDate().equals(date)) {
                                expensesList.add(e);
                            }
                        }

                        if (expensesList.size() != 0) {
                            Ui.printExpenseTable(expensesList);
                        } else {
                            System.out.println(MESSAGE_LIST_NO_EXPENSES
                                + date.getDayOfMonth() + " "
                                + new DateFormatSymbols().getMonths()[date.getMonthValue() - 1]
                                + " " + date.getYear());
                        }
                    }

                    if (wallet.getLoanList().getLoanList().size() != 0) {
                        for (Loan l : wallet.getLoanList().getLoanList()) {
                            if (l.getDate().equals(date)) {
                                loansList.add(l);
                            }
                        }

                        if (loansList.size() != 0) {
                            Ui.printLoanTable(loansList);
                        } else {
                            System.out.println(MESSAGE_LIST_NO_LOANS
                                + date.getDayOfMonth() + " "
                                + new DateFormatSymbols().getMonths()[date.getMonthValue() - 1]
                                + " " + date.getYear());
                        }
                    }
                }
            } else {
                System.out.println(MESSAGE_USAGE);
            }
            //@@author
        }
        return false;
    }
}
