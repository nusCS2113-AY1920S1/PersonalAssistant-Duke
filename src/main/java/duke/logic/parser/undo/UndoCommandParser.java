package duke.logic.parser.undo;

import duke.commons.core.Message;
import duke.logic.command.UndoCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;

/**
 * A parser that parses {@code UndoCommand}.
 */
public class UndoCommandParser implements SubCommandParser<UndoCommand> {

    @Override
    public UndoCommand parse(String subCommandAndArgs) throws ParseException {

        if (!subCommandAndArgs.isBlank()) {
            throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
        }

        return new UndoCommand();
    }

}
