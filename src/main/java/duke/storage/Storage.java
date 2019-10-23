package duke.storage;

import duke.exception.DukeException;
import duke.model.ExpenseList;

import java.util.Map;

/**
 * API of the Storage component
 */
public interface Storage {

    void saveExpenseList(ExpenseList expenseList) throws DukeException;

    ExpenseList loadExpenseList() throws DukeException;

    void savePlanAttributes(Map<String, String> attributes) throws DukeException;

    Map<String, String> loadPlanAttributes();

    // todo: add other interface methods for other lists.
}
