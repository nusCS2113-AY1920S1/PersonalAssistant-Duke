package duke.parser;

import duke.command.AddOrderCommand;
import duke.command.Command;
import duke.command.DeleteOrderCommand;
import duke.commons.DukeException;
import duke.order.Order;

import java.util.List;
import java.util.Map;

public class CommandParser {
    public static Command parseOrderAdd(Map<String, List<String>> params) throws DukeException {
        if (!params.containsKey("name") || !params.containsKey("contact") || !params.containsKey("by") || !params.containsKey("item")) {
            throw new DukeException("Must have name, contact, deadline & item.");
        }

        Order order = new Order(params.get("name").get(0),
                params.get("contact").get(0),
                TimeParser.convertStringToDate(params.get("by").get(0)));

        if (params.containsKey("rmk")) {
            order.setRemarks(params.get("rmk").get(0));
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
                throw new DukeException("Quantity should be an integer.");
            }
        }
        return new AddOrderCommand(order);
    }

    public static Command parseOrderDelete(Map<String, List<String>> params) throws DukeException {
        try {
            int index = Integer.parseInt(params.get("i").get(0));
            return new DeleteOrderCommand(index);
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a valid index.");
        }
    }
}
