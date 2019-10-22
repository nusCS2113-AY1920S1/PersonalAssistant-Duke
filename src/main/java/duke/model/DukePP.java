package duke.model;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.logging.Logger;

/**
 * Wraps all memory data of Duke++
 * Implements the interface of model module.
 */

public class DukePP implements Model {

    private static final Logger logger = LogsCenter.getLogger(DukePP.class);

    private final ExpenseList expenseList;
    private final PlanBot planBot;
    // todo: add other data inside the DukePP.

    public ObservableList<Expense> externalExpenseList;

    /**
     * Creates a DukePP.
     * This constructor is used for loading DukePP from storage.
     */
    // todo: pass more arguments to constructor as more data are implemented.
    public DukePP(ExpenseList expenseList, PlanBot planBot) {
        this.expenseList = expenseList;
        this.planBot = planBot;
    }

    //******************************** ExpenseList operations

    public void addExpense(Expense expense) {
        expenseList.add(expense);
        logger.info("Model's externalList length now is "
                + externalExpenseList.size());
    }

    public void deleteExpense(int index) throws DukeException {
        expenseList.remove(index);
    }

    public void clearExpense() {
        expenseList.clear();
    }

    public void filterExpense(String filterCriteria) throws DukeException {
        expenseList.setFilterCriteria(filterCriteria);
    }

    public void sortExpense(String sortCriteria) throws DukeException {
        expenseList.setSortCriteria(sortCriteria);
    }

    public void viewExpense(String viewScope, int previous) throws DukeException {
        expenseList.setViewScope(viewScope, previous);
    }

    public ObservableList<Expense> getExpenseExternalList() {
        logger.info("Model sends external List length "
                + expenseList.getExternalList().size());
        externalExpenseList = FXCollections.unmodifiableObservableList(expenseList.getExternalList());
        return externalExpenseList;
    }

    /**
     * Returns the expenseList for storage.
     */
    public ExpenseList getExpenseList() {
        return expenseList;
    }

    public ObservableList<PlanBot.PlanDialog> getDialogObservableList() {
        return planBot.getDialogObservableList();
    }

    public void processPlanInput(String input) {
        planBot.processInput(input);
    }

    //******************************** Operations for other data....
    //******************************** For example, operations of monthly income list.
    //    todo: add other data operations

}
