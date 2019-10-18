package wallet.logic.command;

import wallet.logic.LogicManager;
import wallet.model.Wallet;
import wallet.model.contact.Contact;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.model.task.Task;
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

    public static final String MESSAGE_LIST_TASKS = "Here are the tasks in your list:";
    public static final String MESSAGE_LIST_CONTACTS = "Here are the contacts in your list:";
    public static final String MESSAGE_LIST_EXPENSES = "Here are the expenses in your list:";
    public static final String MESSAGE_LIST_NO_EXPENSES = "There are no expenses for ";
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
     * @return False.
     */
    @Override
    public boolean execute(Wallet wallet) {
        boolean isListAll = false;
        int counter;
        switch (record) {
        case "recurring":
            wallet.getExpenseList().listRecurringExpense();
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
            ArrayList<Expense> expenseList = LogicManager.getWallet().getExpenseList().getExpenseList();
            Ui.printExpenseTable(expenseList);
            if (!isListAll) {
                break;
            }
            //else fallthrough

        case "loan":
            wallet.getLoanList().listLoanList();
            break;

        default:
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(record.trim(), formatter);

            ArrayList<Expense> expensesList = new ArrayList<Expense>();

            if (!date.equals(null) && wallet.getExpenseList().getExpenseList().size() != 0) {
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
                /*for (Loan l : wallet.getLoanList().getLoanList()) {
                    if (l.getDate().equals(date)) {
                        System.out.println(l.toString());
                    }
                }*/
                break;
            } else {
                System.out.println(MESSAGE_USAGE);
            }
        }
        return false;
    }
}
