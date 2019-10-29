package duke.logic;

import duke.exception.DukeException;
import duke.model.Expense;
import duke.model.Income;
import duke.model.PlanBot;
import duke.model.payment.Payment;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
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

    ObservableList<Payment> getFilteredPaymentList();

    // ObservableList<Payment> getPaymentSearchResult();

    public ObservableList<String> getSortIndicator();

    public ObservableList<Predicate<Payment>> getPredicateIndicator();

    // public ObservableList<String> getSearchKeywordIndicator();

}
