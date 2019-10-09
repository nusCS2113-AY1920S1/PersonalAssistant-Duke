package duke.logic.parser.commons;

import duke.commons.core.Message;
import duke.logic.command.Command;
import duke.logic.parser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Parser that is able to parse the sub command into a {@code Command} of type {@code T}.
 *
 * @param <T> the type of the sub command
 */
public interface SubCommandParser<T extends Command> extends Parser<T> {

    /**
     * Used to get sub-command and args.
     * Capture group 1: Sub-command word.
     * Capture group 2: (Optional) Args.
     */
    Pattern SUB_COMMAND_AND_ARGS_FORMAT = Pattern.compile("^(\\w+)\\s*(.*)");

    /**
     * Returns the sub command word.
     * For example, in {@code add -name jj}, {@code add} is returned.
     * @param subCommandAndArgs a string containing sub command and args
     * @return the sub command word
     */
    static String getSubCommandWord(String subCommandAndArgs) {
        requireNonNull(subCommandAndArgs);

        final Matcher matcher = SUB_COMMAND_AND_ARGS_FORMAT.matcher(subCommandAndArgs);
        if (!matcher.matches()) {
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }
        return matcher.group(1).strip();
    }

    /**
     * Returns the args.
     * For example, in {@code add -name jj}, {@code -name jj} is returned.
     * @param subCommandAndArgs a string containing sub command and args
     * @return the args
     */
    static String getArgs(String subCommandAndArgs) {
        requireNonNull(subCommandAndArgs);

        final Matcher matcher = SUB_COMMAND_AND_ARGS_FORMAT.matcher(subCommandAndArgs.trim());
        if (!matcher.matches()) {
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }
        return matcher.group(2).strip();
    }

}
