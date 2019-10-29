package duke.storage;

import duke.exception.DukeException;
import duke.model.Budget;
import duke.model.BudgetView;
import duke.model.ExpenseList;
import duke.model.IncomeList;

import java.io.IOException;
import java.util.Map;

public class StorageManager implements Storage {

    private ExpenseListStorage expenseListStorage;
    private PlanAttributesStorage planAttributesStorage;
    private IncomeListStorage incomeListStorage;
    private BudgetStorage budgetStorage;
    private BudgetViewStorage budgetViewStorage;

   /**
    * Constructor for StorageManager
    * @param expenseListStorage storage for expense List
    * @param planAttributesStorage storage for PlanAttributes from PlanBot
    * @param budgetStorage Storage for budget
    */
    public StorageManager(ExpenseListStorage expenseListStorage, 
                          PlanAttributesStorage planAttributesStorage, 
                          IncomeListStorage incomeListStorage, 
                          BudgetStorage budgetStorage,
                          BudgetViewStorage budgetViewStorage) {
        this.expenseListStorage = expenseListStorage;
        this.planAttributesStorage = planAttributesStorage;
        this.incomeListStorage = incomeListStorage;
        this.budgetStorage = budgetStorage;
        this.budgetViewStorage = budgetViewStorage;
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
    public void saveIncomeList(IncomeList incomeList) throws DukeException {
        incomeListStorage.saveIncomeList(incomeList);
    }

    @Override
    public IncomeList loadIncomeList() throws DukeException {
        return incomeListStorage.loadIncomeList();
    }

    public Budget loadBudget() throws IOException, DukeException {
        return budgetStorage.loadBudget();
    }

    @Override
    public void saveBudget(Budget budget) throws DukeException {
        budgetStorage.saveBudget(budget);
    }

    @Override
    public BudgetView loadBudgetView() throws IOException, DukeException {
        return budgetViewStorage.loadBudgetView();
    }

    @Override
    public void saveBudgetView(BudgetView budgetView) throws DukeException {
        budgetViewStorage.saveBudgetView(budgetView);
    }
}
