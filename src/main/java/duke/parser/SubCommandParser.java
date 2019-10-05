package duke.parser;

import duke.commons.core.Message;
import duke.logic.command.commons.Command;
import duke.parser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface SubCommandParser<T extends Command> extends Parser<T> {

    /**
     * Used to get sub-command and args.
     * Capture group 1: Sub-command word.
     * Capture group 2: (Optional) Args.
     */
    static final Pattern SUB_COMMAND_AND_ARGS_FORMAT = Pattern.compile("^(\\w+)\\s*(.*)");

    static String getSubCommandWord(String subCommandAndArgs) {
        final Matcher matcher = SUB_COMMAND_AND_ARGS_FORMAT.matcher(subCommandAndArgs.trim());
        if (!matcher.matches()) {
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }
        return matcher.group(1).strip();
    }

    static String getArgs(String subCommandAndArgs) {
        final Matcher matcher = SUB_COMMAND_AND_ARGS_FORMAT.matcher(subCommandAndArgs.trim());
        if (!matcher.matches()) {
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }
        return matcher.group(2).strip();
    }

}
