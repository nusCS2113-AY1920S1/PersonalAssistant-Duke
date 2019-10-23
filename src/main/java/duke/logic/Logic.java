package duke.logic;

import duke.exception.DukeException;
import duke.model.Expense;
import duke.model.PlanBot;
import javafx.collections.ObservableList;

import java.math.BigDecimal;

/**
 * API of the Logic component.
 */

public interface Logic {

    CommandResult execute(String userInput) throws DukeException;

    ObservableList<Expense> getExternalExpenseList();

    ObservableList<PlanBot.PlanDialog> getDialogObservableList();

    BigDecimal getTagAmount(String tag);

}
