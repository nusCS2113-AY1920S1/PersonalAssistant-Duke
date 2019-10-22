package duke.logic;

import duke.exception.DukeException;
import duke.model.Expense;
import javafx.collections.ObservableList;

import java.math.BigDecimal;

/**
 * API of the Logic component.
 */

public interface Logic {

    CommandResult execute(String userInput) throws DukeException;

    ObservableList<Expense> getExternalExpenseList();

    BigDecimal getTagAmount(String tag);

}
