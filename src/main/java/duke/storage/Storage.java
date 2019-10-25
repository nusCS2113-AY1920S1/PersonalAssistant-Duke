package duke.storage;

import duke.exception.DukeException;
import duke.model.ExpenseList;
import duke.model.IncomeList;

import java.util.Map;

/**
 * API of the Storage component
 */
public interface Storage {

    void saveExpenseList(ExpenseList expenseList) throws DukeException;

    ExpenseList loadExpenseList() throws DukeException;

    void savePlanAttributes(Map<String, String> attributes) throws DukeException;

    Map<String, String> loadPlanAttributes();

    void saveIncomeList(IncomeList incomeList) throws DukeException;

    IncomeList loadIncomeList() throws DukeException;


    // todo: add other interface methods for other lists.
}
