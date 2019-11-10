package duke.storage;

import duke.exception.DukeException;
import duke.model.Budget;
import duke.model.BudgetView;
import duke.model.ExpenseList;
import duke.model.payment.PaymentList;
import duke.model.IncomeList;


import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * API of the Storage component.
 */
public interface Storage {

    void saveExpenseList(ExpenseList expenseList) throws DukeException;

    ExpenseList loadExpenseList() throws DukeException;

    void savePlanAttributes(Map<String, String> attributes) throws DukeException;

    Map<String, String> loadPlanAttributes();

    void saveIncomeList(IncomeList incomeList) throws DukeException;

    IncomeList loadIncomeList() throws DukeException;

    Budget loadBudget() throws IOException, DukeException;

    void saveBudget(Budget budget) throws DukeException;

    BudgetView loadBudgetView() throws IOException, DukeException;

    void saveBudgetView(BudgetView budgetView) throws DukeException;

    /**
     * Loads paymentList from storage.
     *
     * @return PaymentList as Optional.
     * @throws DukeException If errors occur during loading process.
     */
    Optional<PaymentList> loadPaymentList() throws DukeException;

    /**
     * Saves paymentList into storage.
     *
     * @param paymentList The paymentList to be saved in storage.
     * @throws IOException If errors occur during saving process.
     */
    void savePaymentList(PaymentList paymentList) throws IOException;

}
