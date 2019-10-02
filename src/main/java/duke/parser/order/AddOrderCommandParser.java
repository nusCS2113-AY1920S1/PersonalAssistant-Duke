package duke.parser.order;

import duke.command.AddOrderCommand;
import duke.commons.Message;
import duke.entities.Order;
import duke.parser.*;
import duke.parser.exceptions.ParseException;

import java.util.List;


public class AddOrderCommandParser implements Parser<AddOrderCommand> {
    @Override
    public AddOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_ORDER_NAME,
                CliSyntax.PREFIX_ORDER_CONTACT,
                CliSyntax.PREFIX_ORDER_ITEM,
                CliSyntax.PREFIX_ORDER_DEADLINE,
                CliSyntax.PREFIX_ORDER_STATUS,
                CliSyntax.PREFIX_ORDER_REMARKS
        );
        Order order = new Order();
        order.setCustomerName(map.getValue(CliSyntax.PREFIX_ORDER_NAME).orElse(""));
        order.setCustomerContact(map.getValue(CliSyntax.PREFIX_ORDER_CONTACT).orElse(""));
        order.setRemarks(map.getValue(CliSyntax.PREFIX_ORDER_REMARKS).orElse(""));
        order.setStatus(Order.Status.valueOf(map.getValue(
                CliSyntax.PREFIX_ORDER_STATUS).orElse("ACTIVE")
                .toUpperCase())
        );
        order.setDeliveryDate(TimeParser.convertStringToDate(
                map.getValue(CliSyntax.PREFIX_ORDER_DEADLINE)
                        .orElse("now")));
        if (map.getValue(CliSyntax.PREFIX_ORDER_ITEM).isPresent()) {
            addItemsToOder(map.getAllValues(CliSyntax.PREFIX_ORDER_ITEM), order);
        }
        return new AddOrderCommand(order);
    }

    private static void addItemsToOder(List<String> itemArg, Order toAdd) throws ParseException {
        if (itemArg.size() == 0) {
            return;
        }
        for (String itemString : itemArg) {
            String[] itemAndQty = itemString.split(",");
            if (itemAndQty.length < 2) {
                throw new ParseException(Message.MESSAGE_ITEM_MISSING_NAME_OR_QUANTITY);
            }
            if (itemAndQty[0].strip().equals("") || itemAndQty[1].strip().equals("")) {
                throw new ParseException(Message.MESSAGE_ITEM_MISSING_NAME_OR_QUANTITY);
            }
            try {
                toAdd.addItem(itemAndQty[0].strip(), Integer.parseInt(itemAndQty[1].strip()));
            } catch (NumberFormatException e) {
                throw new ParseException(Message.MESSAGE_INVALID_NUMBER_FORMAT);
            }
        }
    }
}
