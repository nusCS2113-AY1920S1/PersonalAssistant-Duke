package duke.logic;

import duke.exception.DukeException;
import duke.model.Expense;
import duke.model.Income;
import duke.model.PlanBot;
import duke.model.payment.Payment;
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

    ObservableList<Payment> getFilteredPaymentList();

    StringProperty getPaymentSortingCriteria();

    ObjectProperty<Predicate> getPaymentPredicate();

    StringProperty getExpenseListTotalString();

    StringProperty getSortCriteriaString();

    StringProperty getViewCriteriaString();

    StringProperty getFilterCriteriaString();

    StringProperty getIncomeListTotalString();
}
