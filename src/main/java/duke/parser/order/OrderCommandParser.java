package duke.parser.order;

import duke.command.DeleteOrder;
import duke.command.order.AddOrder;
import duke.command.order.EditOrder;
import duke.command.order.OrderCommand;
import duke.parser.SubCommandParser;
import duke.parser.exceptions.ParseException;

public class OrderCommandParser implements SubCommandParser<OrderCommand> {

    @Override
    public OrderCommand parse(String subCommandAndArgs) throws ParseException {
        String subCommand = SubCommandParser.getSubCommandWord(subCommandAndArgs);
        String args = SubCommandParser.getArgs(subCommandAndArgs);

        switch (subCommand) {
            case AddOrder.COMMAND_WORD:
                return new AddOrderCommandParser().parse(args);
            case DeleteOrder.COMMAND_WORD:
                return new DeleteOrderCommandParser().parse(args);
            case EditOrder.COMMAND_WORD:
                return new EditOrderCommandParser().parse(args);
        }
        return null;
    }
}
