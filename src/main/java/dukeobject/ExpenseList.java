package dukeobject;

import exception.DukeException;

import java.io.File;
import java.math.BigDecimal;
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

    /**
     * Returns the total amount of money spent.
     *
     * @return BigDecimal of the total amount of money spent.
     */
    public BigDecimal getTotalAmount() {
        return internalList.stream()
            .map(Expense::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}