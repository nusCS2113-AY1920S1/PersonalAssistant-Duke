package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.exceptions.ItineraryIncorrectDaysException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.Command;

import java.util.logging.Logger;

/**
 * Represents a parser that is able to parse input into a Command object.
 */
public abstract class CommandParser<T extends Command> {
    protected static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Parses the data it have into a Command object.
     *
     * @return Command object.
     * @throws ParseException If data cannot be parsed.
     * @throws ItineraryIncorrectDaysException If the number of days are wrong.
     */
    public abstract T parse() throws ParseException, ItineraryIncorrectDaysException;
}
