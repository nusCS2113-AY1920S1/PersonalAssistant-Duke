package wallet.record;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ExpenseParser {
    public static Expense parseInput(String input){
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
        }
        return expense;
    }

    public static ArrayList<Expense> getRecurringRecords(ExpenseList expenseList){
        ArrayList<Expense> recList = new ArrayList<Expense>();

        for (Expense e : expenseList.getExpenseList()){
            if (e.isRecurring()) {
                recList.add(e);
            }
        }

        return recList;
    }

    public static void populateRecurringRecords(ExpenseList expenseList) {
        ArrayList<Expense> recList = getRecurringRecords(expenseList);
        LocalDate currentDate = LocalDate.now();
        for (Expense e : recList) {
            int lastDay = currentDate.lengthOfMonth();
            int currentMonth = currentDate.getMonthValue();
            LocalDate eDate = e.getCreatedDate();
            if (e.getRecFrequency().equals("DAILY")) {
                if (eDate.getMonthValue() == currentMonth && eDate.getDayOfMonth() == lastDay) {
                    continue;
                }
                int index = expenseList.findExpenseIndex(e);
                e.setRecurring(false);
                e.setRecFrequency("NULL");
                expenseList.editExpense(index, e);
                eDate = eDate.plusDays(1);
                while (eDate.getMonthValue() != currentMonth+1) {
                    if (eDate.getMonthValue() == currentMonth && eDate.getDayOfMonth() == lastDay) {
                        expenseList.addExpense(new Expense(e.getDescription(), eDate, e.getAmount(), e.getCategory(), true, "DAILY"));
                    } else {
                        expenseList.addExpense(new Expense(e.getDescription(), eDate, e.getAmount(), e.getCategory(), false, "NULL"));
                    }
                    eDate = eDate.plusDays(1);
                }
            } else if (e.getRecFrequency().equals("WEEKLY")) {
                if (eDate.getMonthValue() == currentMonth && eDate.getDayOfMonth() > lastDay-7) {
                    continue;
                }
                int index = expenseList.findExpenseIndex(e);
                e.setRecurring(false);
                e.setRecFrequency("NULL");
                expenseList.editExpense(index, e);
                eDate = eDate.plusDays(7);
                while (eDate.getMonthValue() != currentMonth+1) {
                    if (eDate.getMonthValue() == currentMonth && eDate.getDayOfMonth() > lastDay-7) {
                        expenseList.addExpense(new Expense(e.getDescription(), eDate, e.getAmount(), e.getCategory(), true, "WEEKLY"));
                    } else {
                        expenseList.addExpense(new Expense(e.getDescription(), eDate, e.getAmount(), e.getCategory(), false, "NULL"));
                    }
                    eDate = eDate.plusDays(7);
                }
            } else if (e.getRecFrequency().equals("MONTHLY")) {
                if (eDate.getMonthValue() == currentMonth) {
                    continue;
                }
                int index = expenseList.findExpenseIndex(e);
                e.setRecurring(false);
                e.setRecFrequency("NULL");
                expenseList.editExpense(index, e);
                eDate = eDate.plusMonths(1);
                while (eDate.getMonthValue() != currentMonth+1) {
                    if (eDate.getMonthValue() == currentMonth) {
                        expenseList.addExpense(new Expense(e.getDescription(), eDate, e.getAmount(), e.getCategory(), true, "MONTHLY"));
                    } else {
                        expenseList.addExpense(new Expense(e.getDescription(), eDate, e.getAmount(), e.getCategory(), false, "NULL"));
                    }
                    eDate = eDate.plusMonths(1);
                }
            }
        }
    }
}
