package duke.parser;

import duke.commons.core.Message;
import duke.logic.command.commons.Command;
import duke.logic.command.order.OrderCommand;
import duke.parser.exceptions.ParseException;
import duke.parser.order.OrderCommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parsers user input.
 */
public class BakingHomeParser {

    /**
     * Used to get primary command word.
     * Capture group 1: primary command word.
     * Capture group 2: (optional) sub-command word and args.
     */
    private static final Pattern PRIMARY_COMMAND_FORMAT = Pattern.compile("^(\\w+)\\s*(.+)?");

    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = PRIMARY_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String primaryCommand = matcher.group(1);
        String subCommandAndArgs = matcher.group(2);
        switch (primaryCommand) {
            case OrderCommand.COMMAND_WORD:
                return new OrderCommandParser().parse(subCommandAndArgs);
            default:
                throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
