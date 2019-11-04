package duke.logic.parsers.commandParsers;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;

import java.util.logging.Logger;

/**
 * Represents a parser that is able to parse input into a Command object.
 */
public abstract class CommandParser<T extends Command> {
    protected static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Parses the data it have into a Command object.
     * @return Command object.
     * @throws ParseException If data cannot be parsed.
     */
    public abstract T parse() throws ParseException;
}
