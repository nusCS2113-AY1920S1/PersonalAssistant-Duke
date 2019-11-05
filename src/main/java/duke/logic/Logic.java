package duke.logic;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.AutoCompleter;
import duke.logic.parser.exceptions.ParseException;
import duke.model.BakingHome;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.order.Order;
import duke.model.product.Product;
import duke.model.sale.Sale;
import duke.storage.BakingHomeStorage;
import javafx.collections.ObservableList;
import org.ocpsoft.prettytime.shade.org.apache.commons.logging.Log;

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

    Boolean isAutoCompletable(AutoCompleter.UserInputState currentState);

    AutoCompleter.UserInputState complete();

    ObservableList<Order> getFilteredOrderList();

    ObservableList<Sale> getFilteredSaleList();

    ObservableList<Product> getFilteredProductList();

    ObservableList<Item<Ingredient>> getFilteredInventoryList();

    ObservableList<Item<Ingredient>> getFilteredShoppingList();

}
