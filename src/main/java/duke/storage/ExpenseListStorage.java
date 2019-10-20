package duke.storage;

import duke.exception.DukeException;
import duke.model.ExpenseList;

public interface ExpenseListStorage {

    public void saveExpenseList(ExpenseList expenseList) throws DukeException;

    public ExpenseList loadExpenseList() throws DukeException;
}
