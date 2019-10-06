package duke.logic.parser.commons;

import duke.logic.command.commons.Command;
import duke.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse full user input into a {@code Command} of type {@code T}.
 *
 * @param <T> the type of the command
 */
public interface Parser<T extends Command> {
    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput) throws ParseException;
}
