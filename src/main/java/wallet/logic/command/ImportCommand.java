//@@author Xdecosee

package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.record.ExpenseList;
import wallet.model.record.LoanList;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.model.record.Budget;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;

public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    private static final String MESSAGE_SUCCESS_ADD_CONTACT = "Got it. I've added this contact:";
    private static final String MESSAGE_SUCCESS_ADD_EXPENSE = "Got it. I've added this expense:";
    private static final String MESSAGE_SUCCESS_ADD_LOAN = "Got it. I've added this loan:";
    private static final String MESSAGE_IMPORT_PROGRESS = "Importing records...";
    private static final String MESSAGE_IMPORT_FINISH = "Finish Import!";
    public static final String MESSAGE_NEW_REMAINING_BUDGET = " is your budget left for ";
    public static final String MESSAGE_EXCEED_BUDGET = "Your budget has exceeded!!";
    public static final String MESSAGE_REACH_BUDGET = "You have reached your budget!!";
    private LoanList loanList = null;
    private ExpenseList expenseList = null;

    /**
     * Constructs the ImportCommand object with LoanList object.
     *
     * @param newList list of new loan entries.
     */
    public ImportCommand(LoanList newList) {
        this.loanList = newList;
    }

    /**
     * Constructs the ImportCommand object with LoanList object.
     *
     * @param newList list of new expense entries.
     */
    public ImportCommand(ExpenseList newList) {
        this.expenseList = newList;
    }

    /**
     * Imports data into wallet.
     *
     * @param wallet The Wallet Object.
     * @return false.
     */
    @Override
    public boolean execute(Wallet wallet) {

        System.out.println(MESSAGE_IMPORT_PROGRESS + "\n");

        if (loanList != null) {

            ArrayList<Loan> loanData = loanList.getLoanList();

            for (Loan loan : loanData) {
                //@@author Xdecosee-reused
                int largestId = wallet.getContactList().getLargestId(wallet.getContactList().getContactList()) + 1;
                loan.getPerson().setId(largestId);
                wallet.getContactList().addContact(loan.getPerson());
                wallet.getContactList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_ADD_CONTACT);
                System.out.println(loan.getPerson().toString());

                wallet.getLoanList().addLoan(loan);
                wallet.getLoanList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_ADD_LOAN);
                System.out.println(loan.toString());
                System.out.println();

            }

        }

        if (expenseList != null) {

            //@@author Xdecosee-reused
            ArrayList<Expense> expenseData = expenseList.getExpenseList();
            for (Expense expense : expenseData) {
                wallet.getExpenseList().addExpense(expense);
                LocalDate date = expense.getDate();

                for (Budget b : wallet.getBudgetList().getBudgetList()) {
                    if (b.getMonth() == date.getMonthValue() && b.getYear() == date.getYear()) {
                        BigDecimal monthBudget = BigDecimal.valueOf(b.getAmount());
                        BigDecimal expenseSum = BigDecimal.valueOf(wallet.getExpenseList()
                                .getMonthExpenses(b.getMonth(), b.getYear()));
                        BigDecimal accountedAmount = BigDecimal.valueOf(b.getAccountedExpenseAmount());
                        double remainingBudget = monthBudget.subtract(expenseSum).add(accountedAmount).doubleValue();
                        b.setExpenseTakenIntoAccount(true);
                        if (remainingBudget < 0) {
                            System.out.println(MESSAGE_EXCEED_BUDGET);
                        } else if (remainingBudget == 0) {
                            System.out.println(MESSAGE_REACH_BUDGET);
                        }
                        System.out.println("$" + remainingBudget + MESSAGE_NEW_REMAINING_BUDGET
                                + new DateFormatSymbols().getMonths()[b.getMonth() - 1] + " " + b.getYear());
                    }
                }

                wallet.getExpenseList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_ADD_EXPENSE);
                System.out.println(expense.toString());
                System.out.println();
            }
        }

        System.out.println(MESSAGE_IMPORT_FINISH);
        return false;
    }
}
