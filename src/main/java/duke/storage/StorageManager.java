package duke.storage;

import duke.exception.DukeException;
import duke.model.Budget;
import duke.model.ExpenseList;

import java.io.IOException;
import java.util.Map;

public class StorageManager implements Storage {

    private ExpenseListStorage expenseListStorage;
    private PlanAttributesStorage planAttributesStorage;
    private BudgetStorage budgetStorage;

    /**
     * Constructor for StorageManager
     * @param expenseListStorage storage for expense List
     * @param planAttributesStorage storage for PlanAttributes from PlanBot
     * @param budgetStorage Storage for budget
     */
    public StorageManager(ExpenseListStorage expenseListStorage,
                          PlanAttributesStorage planAttributesStorage,
                          BudgetStorage budgetStorage) {
        this.expenseListStorage = expenseListStorage;
        this.planAttributesStorage = planAttributesStorage;
        this.budgetStorage = budgetStorage;
    }

    @Override
    public void saveExpenseList(ExpenseList expenseList) throws DukeException {
        expenseListStorage.saveExpenseList(expenseList);
    }

    @Override
    public ExpenseList loadExpenseList() throws DukeException {
        return expenseListStorage.loadExpenseList();
    }

    @Override
    public void savePlanAttributes(Map<String, String> attributes) throws DukeException {
        planAttributesStorage.savePlanAttributes(attributes);
    }

    @Override
    public Map<String, String> loadPlanAttributes() {
        return planAttributesStorage.loadAttributes();
    }

    @Override
    public Budget loadBudget() throws IOException, DukeException {
        return budgetStorage.loadBudget();
    }

    @Override
    public void saveBudget(Budget budget) throws DukeException {
        budgetStorage.saveBudget(budget);
    }


}
