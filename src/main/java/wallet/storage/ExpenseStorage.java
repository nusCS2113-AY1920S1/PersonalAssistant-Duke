package wallet.storage;

import wallet.model.record.Expense;

import java.util.ArrayList;

public class ExpenseStorage extends Storage<Expense> {
    public static final String DEFAULT_STORAGE_FILEPATH_EXPENSE = "expense.txt";

    @Override
    public ArrayList<Expense> loadFile() {
        return null;
    }

    @Override
    public void writeToFile(Expense expense) {

    }

    @Override
    public void updateToFile(Expense expense, int index) {

    }

    @Override
    public void removeFromFile(ArrayList<Expense> expenseList, int index) {

    }
}
