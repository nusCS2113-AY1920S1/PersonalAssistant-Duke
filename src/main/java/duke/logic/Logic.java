package duke.logic;

import duke.logic.command.commons.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.ReadOnlyBakingHome;
import duke.model.order.Order;
import duke.parser.exceptions.ParseException;
import javafx.collections.ObservableList;

/**
 * API of the Logic component.
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    ReadOnlyBakingHome getReadingHome();

    ObservableList<Order> getFilteredOrderList();

}
