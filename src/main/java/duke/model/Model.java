package duke.model;

import duke.exception.DukeException;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {

    //******************************** ExpenseList operations

    public void addExpense(Expense expense);

    public void deleteExpense(int index) throws DukeException;

    public void clearExpense();

    public void filterExpense(String filterCriteria) throws DukeException;

    public void sortExpense(String sortCriteria) throws DukeException;

    public void viewExpense(String viewScope, int previous) throws DukeException;

    public ObservableList<Expense> getExpenseExternalList();

    public ExpenseList getExpenseList();

    //******************************** Operations for other data....
    //******************************** For example, operations of monthly income list.
    // todo: add other data operations

}
