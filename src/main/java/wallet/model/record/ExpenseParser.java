package wallet.model.record;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The ExpenseParser Class handles the logic of creating an Expense Object.
 */
public class ExpenseParser {
    /**
     * Parses the input of the user and returns a corresponding Expense object.
     * @param input The input of the user.
     * @return The Expense object.
     */
    public static Expense parseInput(String input) {
        Expense expense = null;
        try {
            String[] getRec = input.split("/r");
            String freq = getRec[1].trim().toUpperCase();
            String[] getCat = getRec[0].split("/cat");
            String cat = getCat[1].trim();
            String[] getDate = getCat[0].split("/on");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(getDate[1].trim(), formatter);
            String[] getDesc = getDate[0].split("\\$");

            if (freq.equals("DAILY") || freq.equals("WEEKLY") || freq.equals("MONTHLY")) {
                expense = new Expense(getDesc[0].trim(), date, Double.parseDouble(getDesc[1].trim()), cat, true, freq);
            } else {
                System.out.println("☹ OOPS!!! The options for recurrence (/r) are \"daily, weekly or monthly\"");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return expense;
    }

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
     * Populates the expense list with the recurring expenses.
     * @param expenseList The list of expenses.
     */
    public static void populateRecurringRecords(ExpenseList expenseList) {
        ArrayList<Expense> recList = getRecurringRecords(expenseList);
        LocalDate currentDate = LocalDate.now();
        for (Expense e : recList) {
            int lastDay = currentDate.lengthOfMonth();
            int currentMonth = currentDate.getMonthValue();
            LocalDate expenseDate = e.getDate();
            if (e.getRecFrequency().equals("DAILY")) {
                if (expenseDate.getMonthValue() == currentMonth && expenseDate.getDayOfMonth() == lastDay) {
                    continue;
                }
                int index = expenseList.findExpenseIndex(e);
                e.setRecurring(false);
                e.setRecFrequency("NULL");
                expenseList.editExpense(index, e);
                expenseDate = expenseDate.plusDays(1);
                while (expenseDate.getMonthValue() != currentMonth + 1) {
                    if (expenseDate.getMonthValue() == currentMonth && expenseDate.getDayOfMonth() == lastDay) {
                        expenseList.addExpense(new Expense(e.getDescription(), expenseDate,
                                e.getAmount(), e.getCategory(), true, "DAILY"));
                    } else {
                        expenseList.addExpense(new Expense(e.getDescription(), expenseDate,
                                e.getAmount(), e.getCategory(), false, "NULL"));
                    }
                    expenseDate = expenseDate.plusDays(1);
                }
            } else if (e.getRecFrequency().equals("WEEKLY")) {
                if (expenseDate.getMonthValue() == currentMonth && expenseDate.getDayOfMonth() > lastDay - 7) {
                    continue;
                }
                int index = expenseList.findExpenseIndex(e);
                e.setRecurring(false);
                e.setRecFrequency("NULL");
                expenseList.editExpense(index, e);
                expenseDate = expenseDate.plusDays(7);
                while (expenseDate.getMonthValue() != currentMonth + 1) {
                    if (expenseDate.getMonthValue() == currentMonth && expenseDate.getDayOfMonth() > lastDay - 7) {
                        expenseList.addExpense(new Expense(e.getDescription(), expenseDate,
                                e.getAmount(), e.getCategory(), true, "WEEKLY"));
                    } else {
                        expenseList.addExpense(new Expense(e.getDescription(), expenseDate,
                                e.getAmount(), e.getCategory(), false, "NULL"));
                    }
                    expenseDate = expenseDate.plusDays(7);
                }
            } else if (e.getRecFrequency().equals("MONTHLY")) {
                if (expenseDate.getMonthValue() == currentMonth) {
                    continue;
                }
                int index = expenseList.findExpenseIndex(e);
                e.setRecurring(false);
                e.setRecFrequency("NULL");
                expenseList.editExpense(index, e);
                expenseDate = expenseDate.plusMonths(1);
                while (expenseDate.getMonthValue() != currentMonth + 1) {
                    if (expenseDate.getMonthValue() == currentMonth) {
                        expenseList.addExpense(new Expense(e.getDescription(), expenseDate,
                                e.getAmount(), e.getCategory(), true, "MONTHLY"));
                    } else {
                        expenseList.addExpense(new Expense(e.getDescription(), expenseDate,
                                e.getAmount(), e.getCategory(), false, "NULL"));
                    }
                    expenseDate = expenseDate.plusMonths(1);
                }
            }
        }
    }
}
