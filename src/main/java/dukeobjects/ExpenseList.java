package dukeobjects;

import exception.DukeException;

import java.io.File;
import java.util.List;

public class ExpenseList extends DukeList<Expense> {
    private static final String FILE_NAME = "expenses.txt";

    public ExpenseList(File userDirectory) {
        super(new File(userDirectory, FILE_NAME));
    }

    /**
     * Updates {@code externalList}, then returns it.
     *
     * @return {@code externalList}.
     */
    @Override
    public List<Expense> getExternalList() {
        List<Expense> filteredSortedList = internalList;
        externalList = filteredSortedList;
        return filteredSortedList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expense itemFromStorageString(String storageString) throws DukeException {
        return new Expense.Builder(storageString).build();
    }
}