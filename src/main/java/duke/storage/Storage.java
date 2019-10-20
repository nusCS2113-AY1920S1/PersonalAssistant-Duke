package duke.storage;

import duke.exception.DukeException;
import duke.model.ExpenseList;

/**
 * API of the Storage component
 */
public interface Storage {

    void saveExpenseList(ExpenseList expenseList) throws DukeException;

    ExpenseList loadExpenseList() throws DukeException;

    // todo: add other interface methods for other lists.
}
