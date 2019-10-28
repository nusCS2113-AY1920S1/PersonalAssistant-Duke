package duke.logic.parsers;

import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.DukeEmptyFieldException;
import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.commons.exceptions.DukeUnknownCommandException;
import duke.commons.exceptions.InputNotIntException;
import duke.commons.exceptions.ObjectCreationFailedException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.commons.exceptions.UnknownConstraintException;
import duke.logic.commands.RouteAddCommand;
import duke.logic.commands.RouteGenerateCommand;
import duke.logic.commands.RouteNodeAddCommand;
import duke.model.locations.BusStop;
import duke.model.locations.RouteNode;
import duke.logic.api.ApiParser;
import duke.model.Event;
import duke.model.locations.TrainStation;
import duke.model.locations.Venue;
import duke.model.planning.Itinerary;
import duke.model.planning.Todo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Defines parsing methods for utility functions.
 */
public class ParserUtil {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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
    public static Event createEvent(String userInput) throws DukeException {
        String[] withinDetails = userInput.substring("event".length()).strip().split("between| and");
        if (withinDetails.length == 1) {
            throw new DukeUnknownCommandException();
        }
        if (withinDetails.length != 3 || withinDetails[1] == null || withinDetails[2] == null) {
            throw new DukeException(Messages.ERROR_INPUT_INVALID_FORMAT);
        }
        if (withinDetails[0].strip().isEmpty()) {
            throw new DukeException(Messages.ERROR_DESCRIPTION_EMPTY);
        }
        LocalDateTime start = ParserTimeUtil.parseStringToDate(withinDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(withinDetails[2].strip());
        return new Event(withinDetails[0].strip(), start, end);
    }

    public static RouteNode createRouteNode(String userInput) throws DukeException {
        try {
            String[] withinDetails = userInput.strip().split("at | with ", 2);
            if (withinDetails.length != 2) {
                throw new DukeException(Messages.ERROR_INPUT_INVALID_FORMAT);
            }

            String[] indexes = withinDetails[0].split(" ");

            String type = userInput.substring(withinDetails[0].length()).strip().substring(0, 4);
            if (!("with".equals(type) || "at".equals(type.substring(0, 2)))) {
                throw new DukeException(Messages.ERROR_INPUT_INVALID_FORMAT);
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
                    throw new DukeException(Messages.ERROR_COMMAND_UNKNOWN);
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
            throw new ObjectCreationFailedException("ROUTE_NODE");
        }

        throw new ObjectCreationFailedException("ROUTE_NODE");
    }

    /**
     * Parses the userInput and return a new Itinerary constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new Itinerary object.
     */
    public static Itinerary createRecommendation(String userInput) throws DukeException {
        String[] itineraryDetails = userInput.substring("recommend".length()).strip().split("between| and");
        if (itineraryDetails.length == 1) {
            throw new DukeUnknownCommandException();
        }

        if (itineraryDetails.length != 3 || itineraryDetails[1] == null || itineraryDetails[2] == null) {
            throw new DukeException(Messages.ERROR_INPUT_INVALID_FORMAT);
        }

        if (itineraryDetails[0].strip().isEmpty()) {
            throw new DukeException(Messages.ERROR_DESCRIPTION_EMPTY);
        }

        LocalDateTime start = ParserTimeUtil.parseStringToDate(itineraryDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(itineraryDetails[2].strip());
        Venue hotelLocation = ApiParser.getLocationSearch(itineraryDetails[0].strip());
        logger.log(Level.FINE, hotelLocation.getAddress());
        return new Itinerary(start, end, hotelLocation);
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
     * Creates a new RouteGenerateCommand from input, factoring for invalid or empty fields.
     *
     * @param input The userInput read by the user interface.
     * @return The RouteGenerateCommand.
     * @throws DukeEmptyFieldException If there is an empty field.
     * @throws UnknownConstraintException If the constraint is unknown.
     */
    public static RouteGenerateCommand createRouteGenerateCommand(String input) throws DukeEmptyFieldException,
            UnknownConstraintException {
        String[] details = input.split(" to | by ", 3);
        if (details.length == 3) {
            try {
                return new RouteGenerateCommand(details[0], details[1], Constraint.valueOf(details[2].toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new UnknownConstraintException();
            }
        }

        throw new DukeEmptyFieldException(Messages.ERROR_FIELDS_EMPTY);
    }

    /**
     * Gets the field at index in a String list delimited by whitespace.
     *
     * @param index The index of field.
     * @param listSize The total size of String list.
     * @param userInput The userInput read by the user interface.
     * @return The field.
     */
    public static String getFieldInList(int index, int listSize, String userInput) throws DukeException {
        try {
            String[] fields = userInput.split(" ", listSize);
            if (index >= 0 && index < listSize) {
                return fields[index].strip();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new QueryOutOfBoundsException(String.valueOf(index));
        }

        throw new DukeException(Messages.ERROR_INDEX_OUT_OF_BOUNDS);
    }

    /**
     * Gets the integer at index in a String list delimited by whitespace.
     *
     * @param index The index of field.
     * @param listSize The total size of String list.
     * @param userInput The userInput read by the user interface.
     * @return The integer.
     */
    public static int getIntegerInList(int index, int listSize, String userInput) throws InputNotIntException,
            QueryOutOfBoundsException {
        try {
            String[] fields = userInput.split(" ", listSize);
            if (index >= 0 && index < listSize) {
                return Integer.parseInt(fields[index].strip());
            }
        } catch (NumberFormatException e) {
            throw new InputNotIntException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new QueryOutOfBoundsException(String.valueOf(index));
        }

        throw new QueryOutOfBoundsException("INTEGER");
    }

    /**
     * Gets the integer index at index in a String list delimited by whitespace.
     *
     * @param index The index of field.
     * @param listSize The total size of String list.
     * @param userInput The userInput read by the user interface.
     * @return The integer.
     */
    public static int getIntegerIndexInList(int index, int listSize, String userInput) throws InputNotIntException,
            QueryOutOfBoundsException {
        try {
            String[] fields = userInput.split(" ", listSize);
            if (index >= 0 && index < listSize) {
                return Integer.parseInt(fields[index].strip()) - 1;
            }
        } catch (NumberFormatException e) {
            throw new InputNotIntException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new QueryOutOfBoundsException(String.valueOf(index));
        }

        throw new QueryOutOfBoundsException("INTEGER");
    }
}
