package duke.logic.parsers;

import duke.commons.Messages;
import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.ParseException;
import duke.logic.commands.RouteAddCommand;
import duke.logic.commands.RouteGenerateCommand;
import duke.logic.commands.RouteNodeAddCommand;
import duke.model.locations.BusStop;
import duke.model.locations.RouteNode;
import duke.model.locations.TrainStation;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Defines parsing methods for utility functions.
 */
public class ParserUtil {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Creates a new RouteGenerateCommand from input, factoring for invalid or empty fields.
     *
     * @param input The userInput read by the user interface.
     * @return The RouteGenerateCommand.
     * @throws ParseException If the input parsing fails.
     */
    public static RouteGenerateCommand createRouteGenerateCommand(String input) throws ParseException {
        String[] details = input.split(" to | by ", 3);
        if (details.length == 3) {
            try {
                return new RouteGenerateCommand(details[0], details[1], Constraint.valueOf(details[2].toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new ParseException(Messages.ERROR_CONSTRAINT_UNKNOWN);
            }
        }
        throw new ParseException(Messages.ERROR_FIELDS_EMPTY);
    }

    /**
     * Gets the field at index in a String list delimited by whitespace.
     *
     * @param index The index of field.
     * @param listSize The total size of String list.
     * @param userInput The userInput read by the user interface.
     * @return The field.
     */
    public static String getFieldInList(int index, int listSize, String userInput) throws ParseException {
        try {
            String[] fields = userInput.split(" ", listSize);
            if (index >= 0 && index < listSize) {
                return fields[index].strip();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
        }

        throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
    }

    /**
     * Gets the integer at index in a String list delimited by whitespace.
     *
     * @param index The index of field.
     * @param listSize The total size of String list.
     * @param userInput The userInput read by the user interface.
     * @return The integer.
     */
    public static int getIntegerInList(int index, int listSize, String userInput) throws ParseException {
        try {
            String[] fields = userInput.split(" ", listSize);
            if (index >= 0 && index < listSize) {
                return Integer.parseInt(fields[index].strip());
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
        }

        throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
    }

    /**
     * Gets the integer index at index in a String list delimited by whitespace.
     *
     * @param index The index of field.
     * @param listSize The total size of String list.
     * @param userInput The userInput read by the user interface.
     * @return The integer.
     */
    public static int getIntegerIndexInList(int index, int listSize, String userInput) throws ParseException {
        try {
            String[] fields = userInput.split(" ", listSize);
            if (index >= 0 && index < listSize) {
                return Integer.parseInt(fields[index].strip()) - 1;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
        }
        throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
    }
}
