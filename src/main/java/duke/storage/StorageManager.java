package duke.storage;

import duke.exception.DukeException;
import duke.model.ExpenseList;

public class StorageManager implements Storage {

    private ExpenseListStorage expenseListStorage;

    public StorageManager(ExpenseListStorage expenseListStorage) {
        this.expenseListStorage = expenseListStorage;
    }

    @Override
    public void saveExpenseList(ExpenseList expenseList) throws DukeException {
        expenseListStorage.saveExpenseList(expenseList);
    }

    @Override
    public ExpenseList loadExpenseList() throws DukeException {
        return expenseListStorage.loadExpenseList();
    }
}
