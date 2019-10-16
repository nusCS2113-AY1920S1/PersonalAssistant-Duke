package duke.logic.parser.shopping;

import duke.commons.core.Message;
import duke.logic.command.shopping.*;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;

public class ShoppingCommandParser implements SubCommandParser<ShoppingCommand> {

    @Override
    public ShoppingCommand parse(String subCommandAndArgs) throws ParseException {
        String subCommand = SubCommandParser.getSubCommandWord(subCommandAndArgs);
        String args = SubCommandParser.getArgs(subCommandAndArgs);

        switch (subCommand) {
        case AddShoppingCommand.COMMAND_WORD:
            return new AddShoppingCommandParser().parse(args);
        case DeleteShoppingCommand.COMMAND_WORD:
            return new DeleteShoppingCommandParser().parse(args);
        case EditShoppingCommand.COMMAND_WORD:
            return new EditShoppingCommandParser().parse(args);
        case ClearShoppingCommand.COMMAND_WORD:
            return new ClearShoppingCommandParser().parse(args);
        default:
            throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
