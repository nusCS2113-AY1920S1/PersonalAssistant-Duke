package duke.logic.parsers;

import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.DukeEmptyFieldException;
import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.commons.exceptions.DukeUnknownCommandException;
import duke.logic.commands.RouteNodeAddCommand;
import duke.model.events.Event;
import duke.model.events.Todo;
import duke.model.locations.BusStop;
import duke.model.locations.RouteNode;

import java.time.LocalDateTime;

/**
 * Parser for utility functions.
 */
public class ParserUtil {
    /**
     * Parses the userInput and return a new to-do constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new to-do object.
     */
    protected static Todo createTodo(String userInput) throws DukeException {
        String description = userInput.substring("todo".length()).strip();
        if (description.isEmpty()) {
            throw new DukeUnknownCommandException();
        }
        return new Todo(description);
    }

    /**
     * Parses the userInput and return a new Event constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new Event object.
     */
    protected static Event createEvent(String userInput) throws DukeException {
        String[] withinDetails = userInput.substring("event".length()).strip().split("between| and");
        if (withinDetails.length == 1) {
            throw new DukeUnknownCommandException();
        }
        if (withinDetails.length != 3 || withinDetails[1] == null || withinDetails[2] == null) {
            throw new DukeException(Messages.INVALID_FORMAT);
        }
        if (withinDetails[0].strip().isEmpty()) {
            throw new DukeException(Messages.EMPTY_DESCRIPTION);
        }
        LocalDateTime start = ParserTimeUtil.parseStringToDate(withinDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(withinDetails[2].strip());
        return new Event(withinDetails[0].strip(), start, end);
    }

    protected static RouteNode createRouteNode(String userInput) throws DukeException {
        try {
            String[] withinDetails = userInput.strip().split("at |with ", 2);
            assert (withinDetails.length == 2);

            String[] indexes = withinDetails[0].split(" ");

            String type = userInput.substring(withinDetails[0].length()).strip().substring(0, 4);
            assert (type.substring(0, 2) == "at" || type == "with");


            String[] details;
            Constraint constraint;

            if (type.substring(0, 2).equals("at")) {
                details = withinDetails[1].strip().split("by ");
                switch (details[1].toUpperCase()) {
                case "BUS":
                    BusStop result = new BusStop(details[0].strip(), null, null, 0, 0);
                    return result;
                default:
                    throw new DukeException(Messages.UNKNOWN_COMMAND);
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

            return null;
        } catch (Throwable e) {
            throw new DukeException(Messages.INVALID_FORMAT);
        }
    }

    /**
     * Parses the userInput and return an index extracted from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The index.
     */
    public static int getIndex(String userInput) throws DukeException {
        try {
            int index = Integer.parseInt(userInput.replaceAll("\\D+", ""));
            return index - 1;
        } catch (NumberFormatException e) {
            throw new DukeUnknownCommandException();
        }
    }

    /**
     * Parses the userInput and return an index extracted from it safely.
     *
     * @param userInput The userInput read by the user interface.
     * @return The index.
     */
    protected static int getSafeIndex(String userInput) throws DukeException {
        try {
            String index = userInput.split(" ")[1].strip();
            return Integer.parseInt(index) - 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(Messages.OUT_OF_BOUNDS);
        } catch (NumberFormatException e) {
            throw new DukeUnknownCommandException();
        }
    }

    /**
     * Parses the userInput with 2 indexes and return the first index extracted from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The index.
     */
    public static int getFirstIndex(String userInput) throws DukeException {
        try {
            String[] indexStrings = userInput.split(" ", 2);
            if (indexStrings[0].strip().matches("-?(0|[1-9]\\d*)")) {
                int index = Integer.parseInt(indexStrings[0].strip());
                return index;
            } else {
                throw new DukeException(Messages.INVALID_FORMAT);
            }
        } catch (NumberFormatException e) {
            throw new DukeUnknownCommandException();
        }
    }

    /**
     * Parses the userInput with 2 indexes and return the second index extracted from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The index.
     */
    public static int getSecondIndex(String userInput) throws DukeException {
        try {
            String[] indexStrings = userInput.split(" ", 3);
            if (indexStrings[1].strip().matches("-?(0|[1-9]\\d*)")) {
                int index = Integer.parseInt(indexStrings[1].strip());
                return index;
            } else if (indexStrings[1].strip().equals("at")) {
                throw new DukeEmptyFieldException("SECOND_INPUT");
            } else {
                throw new DukeException(Messages.INVALID_FORMAT);
            }
        } catch (NumberFormatException e) {
            throw new DukeException(Messages.INVALID_FORMAT);
        }
    }

    /**
     * Creates a new RouteNodeAddCommand from input, factoring for empty indexNode field.
     * @param input Input created by the ConversationManager object or user input.
     * @return RouteNodeAddCommand The command.
     */
    public static RouteNodeAddCommand createRouteNodeAddCommand(String input) throws DukeException {
        try {
            return new RouteNodeAddCommand(ParserUtil.createRouteNode(input),
                    ParserUtil.getFirstIndex(input), ParserUtil.getSecondIndex(input), false);
        } catch (DukeEmptyFieldException e) {
            return new RouteNodeAddCommand(ParserUtil.createRouteNode(input),
                    ParserUtil.getFirstIndex(input), 0, true);
        }
    }

    /**
     * Gets the field at index in a String list delimited by whitespace.
     * @param index The index of field.
     * @param listSize The total size of String list.
     * @param userInput The userInput read by the user interface.
     * @return The field.
     */
    public static String getFieldInList(int index, int listSize, String userInput) throws DukeException {
        String[] fields = userInput.split(" ", listSize);
        if (index >= 0 && index <= listSize) {
            return fields[index - 1].strip();
        }
        throw new DukeException(Messages.OUT_OF_BOUNDS);
    }
}
