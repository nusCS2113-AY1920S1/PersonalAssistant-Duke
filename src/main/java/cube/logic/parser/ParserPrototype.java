package cube.logic.parser;

import cube.exception.CubeException;
import cube.logic.command.Command;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface ParserPrototype<T extends Command> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String[] userInput) throws CubeException;
}