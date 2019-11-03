package duke.logic.parser.system;

import duke.commons.core.Message;
import duke.logic.command.ExitCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;

/**
 * A parser that parses {@code ExitCommand}.
 */
public class ExitCommandParser implements SubCommandParser<ExitCommand> {

    @Override
    public ExitCommand parse(String subCommandAndArgs) throws ParseException {

        if (!subCommandAndArgs.isBlank()) {
            throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
        }

        return new ExitCommand();
    }

}
