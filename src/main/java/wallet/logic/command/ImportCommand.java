//@@author Xdecosee

package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.port.ImportList;
import wallet.model.record.Budget;
import wallet.model.record.Expense;
import wallet.model.record.Loan;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;

public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS_ADD_CONTACT = "Got it. I've added this contact:";
    public static final String MESSAGE_SUCCESS_ADD_EXPENSE = "Got it. I've added this expense:";
    public static final String MESSAGE_SUCCESS_ADD_LOAN = "Got it. I've added this loan:";
    public static final String MESSAGE_NEW_BUDGET = " is your new budget for ";
    public static final String MESSAGE_EXCEED_BUDGET = "Your budget has exceeded!!";
    public static final String MESSAGE_REACH_BUDGET = "You have reached your budget!!";
    private ImportList importList;
    private String type;

    public ImportCommand(ImportList importList, String type) {
        this.type = type;
        this.importList = importList;
    }

    @Override
    public boolean execute(Wallet wallet) {

        if ("loans".equals(type)) {

            ArrayList<Loan> loanData = importList.getLoanList();
            for (Loan loan : loanData) {

                //@@author Xdecosee-reused
                int largestId = wallet.getContactList().getLargestId(wallet.getContactList().getContactList()) + 1;
                loan.getPerson().setId(largestId);
                wallet.getContactList().addContact(loan.getPerson());
                wallet.getContactList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_ADD_CONTACT);
                System.out.println(loan.getPerson().toString());

                wallet.getLoanList().addLoan(loan);
                wallet.getRecordList().addRecord(loan);
                wallet.getLoanList().setModified(true);
                System.out.println(MESSAGE_SUCCESS_ADD_LOAN);
                System.out.println(loan.toString());

            }

        } else if ("expenses".equals(type)) {

            //@@author Xdecosee-reused
            ArrayList<Expense> expenseData = importList.getExpenseList();
            for (Expense expense : expenseData) {
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
        }


        return false;
    }
}
