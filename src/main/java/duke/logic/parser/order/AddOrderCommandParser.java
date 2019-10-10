package duke.logic.parser.order;

import duke.logic.command.order.AddOrderCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.commons.TimeParser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.order.Customer;
import duke.model.order.Order;

import static duke.logic.parser.commons.CliSyntax.PREFIX_CUSTOMER_CONTACT;
import static duke.logic.parser.commons.CliSyntax.PREFIX_CUSTOMER_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_DEADLINE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_ITEM;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_REMARKS;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_STATUS;

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
                ParserUtil.parseStatus(map.getValue(PREFIX_ORDER_STATUS).orElse("ACTIVE")),
                map.getValue(PREFIX_ORDER_REMARKS).orElse("N/A"),
                ParserUtil.parseItems(map.getAllValues(PREFIX_ORDER_ITEM))
            );

            return new AddOrderCommand(order);
    }


}
