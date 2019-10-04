package duke.parser.order;

import duke.logic.command.order.AddOrderCommand;
import duke.logic.command.order.OrderCommand;
import duke.parser.SubCommandParser;
import duke.parser.exceptions.ParseException;

//import duke.logic.command.order.DeleteOrderCommand;
//import duke.logic.command.order.EditOrderCommand;

public class OrderCommandParser implements SubCommandParser<OrderCommand> {

    @Override
    public OrderCommand parse(String subCommandAndArgs) throws ParseException {
        String subCommand = SubCommandParser.getSubCommandWord(subCommandAndArgs);
        String args = SubCommandParser.getArgs(subCommandAndArgs);

        switch (subCommand) {
            case AddOrderCommand.COMMAND_WORD:
                return new AddOrderCommandParser().parse(args);
//            case DeleteOrderCommand.COMMAND_WORD:
//                return new DeleteOrderCommandParser().parse(args);
//            case EditOrderCommand.COMMAND_WORD:
//                return new EditOrderCommandParser().parse(args);
        }
        return null;
    }
}
