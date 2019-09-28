package duke.parser;

import duke.command.AddOrderCommand;
import duke.command.Command;
import duke.command.DeleteOrderCommand;
import duke.command.EditOrderCommand;
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

    public static void modifyOrdrer(Map<String, List<String>> params, Order order) throws DukeException {
        if (params.containsKey("name")) {
            order.setCustomerName(params.get("name").get(0));
        }
        if (params.containsKey("contact")) {
            order.setCustomerContact(params.get("contact").get(0));
        }
        if (params.containsKey("rmk")) {
            order.setCustomerContact(params.get("rmk").get(0));
        }
        if (params.containsKey("by")) {
            order.setDeliveryDate(TimeParser.convertStringToDate(params.get("by").get(0)));
        }
        if (params.containsKey("status")) {
            try {
                order.setStatus(Order.Status.valueOf(params.get("status").get(0).toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new DukeException("Invalid status");
            }
        }
        addItemsToOrder(params, order);
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

    private static void checkParameters(Map<String, List<String>> params) throws DukeException {
        if (!(params.containsKey("secondary")
                ^ params.containsKey("i")
                ^ params.containsKey("id"))) {
            throw new DukeException("Too many parameters");
        }
        if (!params.containsKey("secondary")
                && !params.containsKey("i")
                && !params.containsKey("id")) {
            throw new DukeException("Too few parameters");
        }
    }

    public static Order getOrderByIndexOrId(List<Order> orders, Map<String, List<String>> params) throws DukeException {
        checkParameters(params);
        if (params.containsKey("secondary") || params.containsKey("i")) {
            int index = getOrderIndex(orders, params);
            return orders.get(index);
        } else if (params.containsKey("id")) {

        } else {
            throw new DukeException("Please specify an order");
        }
        return null;
    }

    public static int getOrderIndex(List<Order> orders, Map<String, List<String>> params) throws DukeException {
        String indexParameter;
        if (params.containsKey("secondary")) {
            indexParameter = params.get("secondary").get(0);
        } else {
            indexParameter = params.get("i").get(0);
        }
        int index;
        try {
            index = Integer.parseInt(indexParameter) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a valid index.");
        }
        if (index < 0 || index >= orders.size()) {
            throw new DukeException("Index out of bound");
        }
        return index;
    }
}
