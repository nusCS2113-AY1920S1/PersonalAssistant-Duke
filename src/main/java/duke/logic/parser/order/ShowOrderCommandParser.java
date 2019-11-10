package duke.logic.parser.order;

import duke.commons.core.Message;
import duke.logic.command.order.ShowOrderCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.order.Order;

/**
 * A parser that parses {@code ShowOrderCommand}.
 */
public class ShowOrderCommandParser implements Parser<ShowOrderCommand> {

    @Override
    public ShowOrderCommand parse(String args) throws ParseException {
        System.out.println(args);
        ArgumentMultimap map = ArgumentTokenizer.tokenize(
            args,
            CliSyntax.PREFIX_ORDER_STATUS
        );

        System.out.println(map.getPreamble());
        if (!map.getPreamble().isBlank()) {
            throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
        }

        if (map.getValue(CliSyntax.PREFIX_ORDER_STATUS).isEmpty()) {
            return new ShowOrderCommand();
        }


        try {
            Order.Status status = Order.Status.valueOf(map.getValue(CliSyntax.PREFIX_ORDER_STATUS).get().toUpperCase());
            return new ShowOrderCommand(status);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Message.MESSAGE_INVALID_STATUS);
        }

    }

}
