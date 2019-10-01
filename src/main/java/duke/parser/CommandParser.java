package duke.parser;

import duke.command.AddOrderCommand;
import duke.command.Command;
import duke.command.DeleteOrderCommand;
import duke.command.EditOrderCommand;
import duke.command.recipe.AddRecipeCommand;
import duke.command.recipe.DeleteRecipeCommand;
import duke.commons.DukeException;
import duke.entities.Order;

import java.util.List;
import java.util.Map;

public class CommandParser {
    public static Command parseOrderAdd(Map<String, List<String>> params) throws DukeException {
        return new AddOrderCommand(params);
    }

    public static Command parseOrderDelete(Map<String, List<String>> params) throws DukeException {
        return new DeleteOrderCommand(params);
    }

    public static Command parseOrderEdit(Map<String, List<String>> params) throws DukeException {
        return new EditOrderCommand(params);
    }

    public static void addItemsToOrder(Map<String, List<String>> params, Order order) throws DukeException {
        if (!params.containsKey("item")) {
            return;
        }

        for (String item : params.get("item")) {
            String[] itemAndQty = item.split(",");
            if (itemAndQty.length < 2) {
                throw new DukeException("Must have item name and quantity");
            }
            if (itemAndQty[0].strip().equals("") || itemAndQty[1].strip().equals("")) {
                throw new DukeException("Item name and quantity should not be empty");
            }
            try {
                order.addItem(itemAndQty[0].strip(), Integer.parseInt(itemAndQty[1].strip()));
            } catch (NumberFormatException e) {
                throw new DukeException("Quantity should be an integer");
            }
        }
    }


    //////////////////////////////////////////////////////////////////////////////////
    public static Command parseRecipeDelete(Map<String, List<String>> params) throws DukeException {
        return new DeleteRecipeCommand(params);
    }

    public static Command parseRecipeAdd(Map<String, List<String>> params) {
        return new AddRecipeCommand(params);
    }
}
