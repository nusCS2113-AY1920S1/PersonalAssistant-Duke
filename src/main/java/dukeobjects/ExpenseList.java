package dukeobjects;

import java.io.File;
import java.util.List;

public class ExpenseList extends DukeList<Expense> {
    private static final String FILE_NAME = "expenses.txt";

    public ExpenseList(File userDirectory) {
        super(new File(userDirectory, FILE_NAME));
    }

    @Override
    public List<Expense> getExternalList() {
        List<Expense> filteredSortedList = internalList;
        externalList = filteredSortedList;
        return filteredSortedList;
    }

    @Override
    protected Expense itemFromStorageString(String storageString) {
        return new Expense.Builder(storageString).build();
    }
}