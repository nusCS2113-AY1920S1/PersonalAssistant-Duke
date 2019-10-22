package wallet.logic.parser;

import wallet.logic.command.AddCommand;
import wallet.model.Wallet;
import wallet.model.record.Expense;
import wallet.model.record.ExpenseList;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The ExpenseParser Class handles the logic of creating an Expense Object.
 */
public class ExpenseParser {
    //@@author kyang96
    /**
     * Returns the list of recurring expenses.
     * @param expenseList The list of all expenses.
     * @return The list of recurring expenses.
     */
    public static ArrayList<Expense> getRecurringRecords(ExpenseList expenseList) {
        ArrayList<Expense> recList = new ArrayList<Expense>();

        for (Expense e : expenseList.getExpenseList()) {
            if (e.isRecurring()) {
                recList.add(e);
            }
        }
        return recList;
    }

    /**
     * Updates the expense list with recurring expenses from the
     * input date until the end of the current month.
     */
    public static void updateRecurringRecords(Wallet wallet) {
        ExpenseList expenseList = wallet.getExpenseList();
        ArrayList<Expense> recList = getRecurringRecords(expenseList);
        LocalDate currentDate = LocalDate.now();
        for (Expense e : recList) {
            int lastDay = currentDate.lengthOfMonth();
            int currentMonth = currentDate.getMonthValue();
            int currentYear = currentDate.getYear();
            LocalDate expenseDate = e.getDate();
            if (expenseDate.getYear() > currentYear) {
                continue;
            }

            if (e.getRecFrequency().equals("DAILY")) {
                if (expenseDate.getYear() == currentYear && expenseDate.getMonthValue() > currentMonth
                        || (expenseDate.getMonthValue() == currentMonth && expenseDate.getDayOfMonth() == lastDay)) {
                    continue;
                }
                int index = expenseList.findExpenseIndex(e);
                e.setRecurring(false);
                e.setRecFrequency(null);
                expenseList.editExpense(index, e);
                expenseDate = expenseDate.plusDays(1);
                while (expenseDate.getMonthValue() <= currentMonth || expenseDate.getYear() < currentYear) {
                    Expense expense = null;
                    if (expenseDate.getMonthValue() == currentMonth && expenseDate.getDayOfMonth() == lastDay
                        && expenseDate.getYear() == currentYear) {
                        expense = new Expense(e.getDescription(), expenseDate,
                                e.getAmount(), e.getCategory(), true, "DAILY");
                    } else {
                        expense = new Expense(e.getDescription(), expenseDate,
                                e.getAmount(), e.getCategory(), false, null);
                    }
                    new AddCommand(expense).execute(wallet);
                    expenseDate = expenseDate.plusDays(1);
                }
            } else if (e.getRecFrequency().equals("WEEKLY")) {
                if (expenseDate.getYear() == currentYear && expenseDate.getMonthValue() > currentMonth
                        || (expenseDate.getMonthValue() == currentMonth && expenseDate.getDayOfMonth() > lastDay - 7)) {
                    continue;
                }
                int index = expenseList.findExpenseIndex(e);
                e.setRecurring(false);
                e.setRecFrequency(null);
                expenseList.editExpense(index, e);
                expenseDate = expenseDate.plusDays(7);
                while (expenseDate.getMonthValue() <= currentMonth || expenseDate.getYear() < currentYear) {
                    Expense expense = null;
                    if (expenseDate.getMonthValue() == currentMonth && expenseDate.getDayOfMonth() > lastDay - 7
                            && expenseDate.getYear() == currentYear) {
                        expense = new Expense(e.getDescription(), expenseDate,
                                e.getAmount(), e.getCategory(), true, "WEEKLY");
                    } else {
                        expense = new Expense(e.getDescription(), expenseDate,
                                e.getAmount(), e.getCategory(), false, null);
                    }
                    new AddCommand(expense).execute(wallet);
                    expenseDate = expenseDate.plusDays(7);
                }
            } else if (e.getRecFrequency().equals("MONTHLY")) {
                if (expenseDate.getYear() == currentYear && expenseDate.getMonthValue() >= currentMonth) {
                    continue;
                }
                int index = expenseList.findExpenseIndex(e);
                e.setRecurring(false);
                e.setRecFrequency(null);
                expenseList.editExpense(index, e);
                expenseDate = expenseDate.plusMonths(1);
                while (expenseDate.getMonthValue() <= currentMonth || expenseDate.getYear() < currentYear) {
                    Expense expense = null;
                    if (expenseDate.getMonthValue() == currentMonth && expenseDate.getYear() == currentYear) {
                        expense = new Expense(e.getDescription(), expenseDate,
                                e.getAmount(), e.getCategory(), true, "MONTHLY");
                    } else {
                        expense = new Expense(e.getDescription(), expenseDate,
                                e.getAmount(), e.getCategory(), false, null);
                    }
                    new AddCommand(expense).execute(wallet);
                    expenseDate = expenseDate.plusMonths(1);
                }
            }
        }
    }
    //@@author
}
