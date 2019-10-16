package duke.logic.parser.commons;

import duke.logic.command.Command;

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
    Pattern SUB_COMMAND_AND_ARGS_FORMAT = Pattern.compile("^(\\w+)\\s*(.*)?");

    /**
     * Returns the sub command word. If sub command is not present, returns an empty string.
     * For example, in {@code add -name jj}, {@code add} is returned.
     * @param subCommandAndArgs a string containing sub command and args.
     */
    static String getSubCommandWord(String subCommandAndArgs) {
        requireNonNull(subCommandAndArgs);

        final Matcher matcher = SUB_COMMAND_AND_ARGS_FORMAT.matcher(subCommandAndArgs);
        if (!matcher.matches()) {
            return "";
        }
        return matcher.group(1).strip();
    }

    /**
     * Returns the args. If args are not present, returns an empty string.
     * For example, in {@code add -name jj}, {@code -name jj} is returned.
     * @param subCommandAndArgs a string containing sub command and args
     * @return the args
     */
    static String getArgs(String subCommandAndArgs) {
        requireNonNull(subCommandAndArgs);

        final Matcher matcher = SUB_COMMAND_AND_ARGS_FORMAT.matcher(subCommandAndArgs.trim());
        if (!matcher.matches()) {
            return  "";
        }
        return matcher.group(2).strip();
    }

}
