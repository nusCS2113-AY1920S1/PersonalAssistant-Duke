package wallet.record;

import java.util.ArrayList;

public class ExpenseList {
    private ArrayList<Expense> expenseList;

    public ExpenseList() {
        this.expenseList = new ArrayList<Expense>();
    }

    public ExpenseList(ArrayList<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(ArrayList<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public void addExpense(Expense e) {
        expenseList.add(e);
    }

    public Expense getExpense(int index) {
        return expenseList.get(index);
    }

    public void editExpense(int index, Expense e) {
        expenseList.set(index, e);
    }

    public int findExpenseIndex(Expense e) {
        return expenseList.indexOf(e);
    }

    public int getSize() {
        return expenseList.size();
    }
}
