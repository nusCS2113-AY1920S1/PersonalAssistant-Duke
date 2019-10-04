package dukeobjects;

import java.util.ArrayList;
import java.util.List;

public class ExpenseList {
    private static List<Expense> expenseList;

    public ExpenseList() {
        expenseList = new ArrayList<Expense>();
    }

    public ExpenseList(ArrayList<String> storageStrings) {
        expenseList = new ArrayList<Expense>();
    }

    public void add(Expense expense) {
        expenseList.add(expense);
    }

    public int getSize() {
        return expenseList.size();
    }

    public Expense getExpense(int i) {
        return expenseList.get(i);
    }

    public static List<Expense> getExpenseList() {
        return expenseList;
    }
}