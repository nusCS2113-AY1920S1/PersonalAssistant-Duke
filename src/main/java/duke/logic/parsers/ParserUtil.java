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

    protected static RouteNode createRouteNode(String userInput) throws ParseException {
        try {
            String[] withinDetails = userInput.strip().split("at | with ", 2);
            if (withinDetails.length != 2) {
                throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
            }

            String[] indexes = withinDetails[0].split(" ");

            String type = userInput.substring(withinDetails[0].length()).strip().substring(0, 4);
            if (!("with".equals(type) || "at".equals(type.substring(0, 2)))) {
                throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
            }

            String[] details;
            if (type.substring(0, 2).equals("at")) {
                details = withinDetails[1].strip().split("by ");
                switch (details[1].toUpperCase()) {
                case "BUS":
                    return new BusStop(details[0].strip(), null, null, 0, 0);
                case "MRT":
                    return new TrainStation(new ArrayList<>(), details[0].strip(), null, 0, 0);
                default:
                    throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
                }
            } else {
                details = withinDetails[1].split("by ");
                String[] coordinateStrings = details[0].strip().split(" ");
                assert (coordinateStrings.length == 2);

                double[] coordinates = new double[2];
                for (int i = 0; i < coordinates.length; i++) {
                    coordinates[i] = Double.parseDouble(coordinateStrings[i].strip());
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(Messages.ERROR_OBJECT_NOT_CREATED);
        }
        throw new ParseException(Messages.ERROR_OBJECT_NOT_CREATED);
    }

    /**
     * Creates a new RouteAddCommand from input.
     *
     * @param input The userInput read by the user interface.
     * @return RouteAddCommand The RouteAddCommand.
     */
    public static RouteAddCommand createRouteAddCommand(String input) {
        String[] details = input.split("desc", 2);
        if (details.length == 2) {
            return new RouteAddCommand(details[0], details[1]);
        } else {
            return new RouteAddCommand(details[0], "");
        }
    }

    /**
     * Creates a new RouteNodeAddCommand from input, factoring for empty indexNode field.
     *
     * @param input The userInput read by the user interface.
     * @return RouteNodeAddCommand The command.
     */
    public static RouteNodeAddCommand createRouteNodeAddCommand(String input) throws ParseException {
        return new RouteNodeAddCommand(ParserUtil.createRouteNode(input),
                ParserUtil.getIntegerIndexInList(0, 4, input), ParserUtil.getIntegerIndexInList(1, 4, input),
                false);
    }

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
