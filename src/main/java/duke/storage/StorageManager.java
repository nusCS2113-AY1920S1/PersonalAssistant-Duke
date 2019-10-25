package duke.storage;

import duke.exception.DukeException;
import duke.model.ExpenseList;
import duke.model.IncomeList;

import java.util.Map;

public class StorageManager implements Storage {

    private ExpenseListStorage expenseListStorage;
    private PlanAttributesStorage planAttributesStorage;
    private IncomeListStorage incomeListStorage;

    public StorageManager(ExpenseListStorage expenseListStorage, PlanAttributesStorage planAttributesStorage, IncomeListStorage incomeListStorage) {
        this.expenseListStorage = expenseListStorage;
        this.planAttributesStorage = planAttributesStorage;
        this.incomeListStorage = incomeListStorage;
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

}
