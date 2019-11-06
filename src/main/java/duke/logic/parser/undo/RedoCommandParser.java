package duke.logic.parser.undo;

import duke.commons.core.Message;
import duke.logic.command.RedoCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;

/**
 * A parser that parses {@code RedoCommand}.
 */
public class RedoCommandParser implements SubCommandParser<RedoCommand> {

    @Override
    public RedoCommand parse(String subCommandAndArgs) throws ParseException {

        if (!subCommandAndArgs.isBlank()) {
            throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
        }

        return new RedoCommand();
    }

}
