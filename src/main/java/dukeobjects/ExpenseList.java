package dukeobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    /**
     * Returns the total amount of money spent.
     *
     * @return BigDecimal of the total amount of money spent.
     */
    public BigDecimal getTotalAmount() {
        BigDecimal total = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        for (Expense expense : expenseList) {
            total = total.add(expense.amount);
        }
        return total;
    }
}