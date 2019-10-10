package duke.logic;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.exceptions.ParseException;
import duke.model.ReadOnlyBakingHome;
import duke.model.commons.Ingredient;
import duke.model.order.Order;
import duke.model.product.Product;
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

    ObservableList<Product> getFilteredProductList();

    ObservableList<Ingredient> getFilteredInventoryList();
}
