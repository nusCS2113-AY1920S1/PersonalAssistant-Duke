package DukeObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpenseList{
    protected List<Expense> expenseList;

    public ExpenseList(){
        expenseList = new ArrayList<Expense>();
    }
    public ExpenseList(ArrayList<String> storageStrings){
        expenseList = new ArrayList<Expense>();
    }

    public void add(Expense expense){
        expenseList.add(expense);
    }

    public int getSize(){
        return expenseList.size();
    }

    public Expense getExpense(int i){
        return expenseList.get(i);
    }

    public List<Expense> getExpenseList(){
        return expenseList;
    }
}