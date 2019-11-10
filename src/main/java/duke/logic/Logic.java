package duke.logic;

import duke.exception.DukeException;
import duke.model.Expense;
import duke.model.Income;
import duke.model.PlanBot;
import duke.model.payment.Payment;
import duke.model.payment.PaymentList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Predicate;

/**
 * API of the Logic component.
 */
public interface Logic {

    /**
     * Executes the user input and returns the result.
     *
     * @param userInput The command as entered by the user.
     * @return the result of the command execution.
     * @throws DukeException If an error occurs during parsing or command execution.
     */
    CommandResult execute(String userInput) throws DukeException;

    ObservableList<Expense> getExternalExpenseList();

    ObservableList<PlanBot.PlanDialog> getDialogObservableList();

    BigDecimal getTagAmount(String tag);

    ObservableList<Income> getExternalIncomeList();

    ObservableList<String> getBudgetObservableList();

    BigDecimal getMonthlyBudget();

    BigDecimal getTotalAmount();

    BigDecimal getRemaining(BigDecimal total);

    Map<Integer, String> getBudgetViewCategory();

    BigDecimal getBudgetTag(String category);

    /**
     * Returns an unmodifiable external list of PaymentList.
     *
     * @return The external list to be displayed.
     */
    ObservableList<Payment> getUnmodifiableFilteredPaymentList();

    /**
     * Returns the sorting criteria used in PaymentList.
     *
     * @return The sorting criteria being used.
     */
    ObjectProperty<PaymentList.SortingCriteria> getPaymentSortingCriteria();

    /**
     * Returns the predicate used in PaymentList.
     *
     * @return The predicate being used.
     */
    ObjectProperty<Predicate> getPaymentPredicate();

    StringProperty getExpenseListTotalString();

    StringProperty getSortCriteriaString();

    StringProperty getViewCriteriaString();

    StringProperty getFilterCriteriaString();

    StringProperty getIncomeListTotalString();
}
