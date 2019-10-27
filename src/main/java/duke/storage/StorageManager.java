package duke.storage;

import duke.exception.DukeException;
import duke.model.Budget;
import duke.model.ExpenseList;
import duke.model.payment.PaymentList;
import duke.storage.payment.PaymentListStorage;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class StorageManager implements Storage {

    private ExpenseListStorage expenseListStorage;
    private PlanAttributesStorage planAttributesStorage;
    private BudgetStorage budgetStorage;
    private PaymentListStorage paymentListStorage;

    public StorageManager(ExpenseListStorage expenseListStorage, PlanAttributesStorage planAttributesStorage,
                          BudgetStorage budgetStorage, PaymentListStorage paymentListStorage) {
        this.expenseListStorage = expenseListStorage;
        this.planAttributesStorage = planAttributesStorage;
        this.budgetStorage = budgetStorage;
        this.paymentListStorage = paymentListStorage;
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

    @Override
    public Optional<PaymentList> loadPaymentList() throws DukeException {
        return paymentListStorage.readPaymentList();
    }

    @Override
    public void savePaymentList(PaymentList paymentList) throws IOException {
        paymentListStorage.savePaymentList(paymentList);
    }

}
