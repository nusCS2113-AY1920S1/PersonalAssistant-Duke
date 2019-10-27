package duke.model;

import duke.exception.DukeException;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.Map;

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

    public String getMonthlyBudgetString();

    public void setMonthlyBudget(BigDecimal monthlyBudget);

    public void setCategoryBudget(String category, BigDecimal budgetBD);

    public BigDecimal getRemaining(BigDecimal total);

    public Map<String, BigDecimal> getBudgetCategory();

    public Budget getBudget();

    public  ObservableList<String> getBudgetObservableList();
    //******************************** Operations for other data....
    //******************************** For example, operations of monthly income list.
    // todo: add other data operations

    //PlanBot
    public ObservableList<PlanBot.PlanDialog> getDialogObservableList();
    public void processPlanInput(String input) throws DukeException;
    public Map<String, String> getKnownPlanAttributes();
    public PlanQuestionBank.PlanRecommendation getRecommendedBudgetPlan();


    //******************************** IncomeList operations

    public void addIncome(Income income);

    public void deleteIncome(int index) throws DukeException;

    public void clearIncome();

    public void filterIncome(String filterCriteria) throws DukeException;

    public void sortIncome(String sortCriteria) throws DukeException;

    public void viewIncome(String viewScope, int previous) throws DukeException;

    public ObservableList<Income> getIncomeExternalList();

    public IncomeList getIncomeList();
}
