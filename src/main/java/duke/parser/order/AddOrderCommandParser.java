package duke.parser.order;

import duke.commons.Message;
import duke.logic.command.order.AddOrderCommand;
import duke.model.commons.Customer;
import duke.model.commons.Product;
import duke.model.order.Order;
import duke.parser.ArgumentMultimap;
import duke.parser.ArgumentTokenizer;
import duke.parser.Parser;
import duke.parser.TimeParser;
import duke.parser.exceptions.ParseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static duke.parser.CliSyntax.*;


public class AddOrderCommandParser implements Parser<AddOrderCommand> {
    @Override
    public AddOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_CUSTOMER_NAME,
                PREFIX_CUSTOMER_CONTACT,
                PREFIX_ORDER_ITEM,
                PREFIX_ORDER_DEADLINE,
                PREFIX_ORDER_STATUS,
                PREFIX_ORDER_REMARKS
        );
        Customer customer = new Customer(
                map.getValue(PREFIX_CUSTOMER_NAME).orElse("customer"),
                map.getValue(PREFIX_CUSTOMER_CONTACT).orElse("N/A"));

        Order order = new Order(
                customer,
                TimeParser.convertStringToDate(map.getValue(PREFIX_ORDER_DEADLINE).orElse("now")),
                Order.Status.valueOf(map.getValue(PREFIX_ORDER_STATUS).orElse("ACTIVE").toUpperCase()),
                map.getValue(PREFIX_ORDER_REMARKS).orElse(""),
                getItems(map.getAllValues(PREFIX_ORDER_ITEM))
        );

        return new AddOrderCommand(order);
    }

    private static Map<Product, Integer> getItems(List<String> itemArg) throws ParseException {
        Map<Product, Integer> items = new HashMap<>();
        for (String itemString : itemArg) {
            String[] itemAndQty = itemString.split(",");
            if (itemAndQty.length < 2) {
                throw new ParseException(Message.MESSAGE_ITEM_MISSING_NAME_OR_QUANTITY);
            }
            if (itemAndQty[0].strip().equals("") || itemAndQty[1].strip().equals("")) {
                throw new ParseException(Message.MESSAGE_ITEM_MISSING_NAME_OR_QUANTITY);
            }
            try {
                items.put(new Product(itemAndQty[0].strip()), Integer.parseInt(itemAndQty[1].strip()));
            } catch (NumberFormatException e) {
                throw new ParseException(Message.MESSAGE_INVALID_NUMBER_FORMAT);
            }
        }
        return items;
    }
}
