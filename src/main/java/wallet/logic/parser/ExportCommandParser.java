//@@author Xdecosee

package wallet.logic.parser;

import wallet.logic.LogicManager;
import wallet.logic.command.ExportCommand;
import wallet.model.record.Expense;
import wallet.model.record.ExpenseList;
import wallet.model.record.Budget;
import wallet.model.record.Loan;
import wallet.model.record.Category;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExportCommandParser implements Parser<ExportCommand> {

    public static final String MESSAGE_ERROR_WRONG_FORMAT = "Wrong Format for command input!";
    public static final String MESSAGE_ERROR_WRONG_YEARMONTH = "Wrong year and month input!";
    private double budgetLeft;

    /**
     * Returns an ExportCommand object.
     *
     * @param input User input of command.
     * @return An ExportCommand object.
     */
    @Override
    public ExportCommand parse(String input) {
        String[] arguments = input.split(" ", 2);
        if ("loan".equals(arguments[0])) {
            List<String[]> data = parseLoan();
            return new ExportCommand(data, arguments[0]);
        } else if ("expense".equals(arguments[0])) {
            if (arguments.length != 2) {
                System.out.println(MESSAGE_ERROR_WRONG_FORMAT);
                return null;
            }
            List<String[]> data = parseExpense(arguments[1]);
            if (data != null) {
                return new ExportCommand(data, arguments[0]);
            } else {
                return null;
            }
        }

        System.out.println(MESSAGE_ERROR_WRONG_FORMAT);
        return null;
    }

    /**
     * Formats expense storage data into list.
     *
     * @param argument month and year to export
     * @return list of formatted data.
     */
    private List<String[]> parseExpense(String argument) {


        List<String[]> data = new ArrayList<>();
        String[] monthYear = argument.split("/", 2);
        try {
            if (monthYear.length == 2) {
                int month = Integer.parseInt(monthYear[0].trim());
                int year = Integer.parseInt(monthYear[1].trim());

                ExpenseList expenseList = LogicManager.getWalletList().getWalletList().get(
                    LogicManager.getWalletList().getState()).getExpenseList();
                double totalSpent = expenseList.getMonthExpenses(month, year);
                String monthFormatted = DateTimeFormatter.ofPattern("MM/yyyy").format(YearMonth.of(year, month));
                int index = 1;
                data.add(new String[]{"Month", monthFormatted});
                if (findBudget(month, year)) {
                    data.add(new String[]{"Budget Left", "$" + budgetLeft});
                }
                data.add(new String[]{"Total Spent", "$" + totalSpent});
                data.add(new String[]{"S/N", "Description", "Amount($)", "Date", "Category", "Recur", "Frequency"});
                for (Expense e : expenseList.getExpenseList()) {
                    LocalDate recordDate = e.getDate();
                    if (month == recordDate.getMonthValue() && year == recordDate.getYear()) {
                        String indexOutput = Integer.toString(index);
                        String description = e.getDescription();
                        String date = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(e.getDate());
                        String amount = Double.toString(e.getAmount());
                        Category category = e.getCategory();
                        String isRecur = (e.isRecurring()) ? "yes" : "no";
                        String frequency = (e.isRecurring()) ? e.getRecFrequency() : "";
                        data.add(new String[]{indexOutput, description, amount, date, String.valueOf(category),
                            isRecur, frequency});
                        index++;
                    }
                }

            } else {
                System.out.println(MESSAGE_ERROR_WRONG_FORMAT);
                return null;
            }

        } catch (NumberFormatException e) {
            System.out.println(MESSAGE_ERROR_WRONG_YEARMONTH);
            return null;
        } catch (DateTimeException e){
            System.out.println(MESSAGE_ERROR_WRONG_YEARMONTH);
            return null;
        }

        return data;
    }

    /**
     * Find remaining budget of month.
     *
     * @param month month to find budget
     * @param year  month to find budget
     * @return boolean of found or not.
     */
    private boolean findBudget(int month, int year) {
        ArrayList<Budget> budgetList = LogicManager.getWalletList().getWalletList().get(
            LogicManager.getWalletList().getState()).getBudgetList().getBudgetList();
        for (Budget b : budgetList) {
            if (b.getMonth() == month && b.getYear() == year) {
                this.budgetLeft = b.getAmount();
                return true;
            }
        }
        return false;
    }

    /**
     * Formats loans storage data into list.
     *
     * @return list of formatted data.
     */
    private List<String[]> parseLoan() {

        ArrayList<Loan> loanList = LogicManager.getWalletList().getWalletList()
            .get(LogicManager.getWalletList().getState()).getLoanList().getLoanList();
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"S/N", "Description", "Amount($)", "Created Date", "Name", "Phone",
            "Other Details", "Lend/Borrow", "Settled"});
        int index = 1;
        for (Loan l : loanList) {
            String indexOutput = Integer.toString(index);
            String description = l.getDescription();
            String amount = Double.toString(l.getAmount());
            String createdDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(l.getDate());
            String isLend = (l.getIsLend()) ? "lend" : "borrow";
            String isSettled = (l.getIsSettled()) ? "yes" : "no";
            String personName = l.getPerson().getName();
            String personPhone = l.getPerson().getPhoneNum();
            if (personPhone == null) {
                personPhone = "";
            }
            String personDetail = l.getPerson().getDetail();
            if (personDetail == null) {
                personDetail = "";
            }
            data.add(new String[]{indexOutput, description, amount, createdDate, personName, personPhone,
                personDetail, isLend, isSettled});
            index++;
        }
        return data;
    }


}
