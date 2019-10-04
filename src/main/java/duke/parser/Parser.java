package duke.parser;

import duke.logic.command.commons.Command;
import duke.parser.exceptions.ParseException;

public interface Parser<T extends Command> {
    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput) throws ParseException;
}
