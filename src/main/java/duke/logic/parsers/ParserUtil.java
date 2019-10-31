package duke.logic.parsers;

import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.ParseException;
import duke.logic.api.ApiParser;
import duke.logic.commands.RouteAddCommand;
import duke.logic.commands.RouteGenerateCommand;
import duke.logic.commands.RouteNodeAddCommand;
import duke.model.lists.AgendaList;
import duke.model.locations.BusStop;
import duke.model.locations.RouteNode;
import duke.model.locations.TrainStation;
import duke.model.locations.Venue;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;
import duke.model.planning.Todo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
                throw new ParseException();
            }

            String[] indexes = withinDetails[0].split(" ");

            String type = userInput.substring(withinDetails[0].length()).strip().substring(0, 4);
            if (!("with".equals(type) || "at".equals(type.substring(0, 2)))) {
                throw new ParseException();
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
                    throw new ParseException();
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
            throw new ParseException();
        }
        throw new ParseException();
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
     * @throws ParseException
     */
    public static RouteGenerateCommand createRouteGenerateCommand(String input) throws ParseException {
        String[] details = input.split(" to | by ", 3);
        if (details.length == 3) {
            try {
                return new RouteGenerateCommand(details[0], details[1], Constraint.valueOf(details[2].toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new ParseException();
            }
        }
        throw new ParseException();
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
            throw new ParseException();
        }

        throw new ParseException();
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
            throw new ParseException();
        }

        throw new ParseException();
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
            throw new ParseException();
        }
        throw new ParseException();
    }

    /**
     * Gets the integer index at index in a String list delimited by whitespace.
     *
     * @param userInput The users entered command which contains details of the Itinerary.
     * @return The Itinerary object created.
     */
    public static Itinerary createNewItinerary(String userInput) throws ParseException, ApiException {
        String[] itineraryDetails = userInput.substring("newItinerary".length()).strip().split(" ");
        LocalDateTime start = ParserTimeUtil.parseStringToDate(itineraryDetails[0].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(itineraryDetails[1].strip());
        Venue hotelLocation = ApiParser.getLocationSearch(itineraryDetails[2].strip());
        String name = itineraryDetails[3].strip();
        Itinerary itinerary = new Itinerary(start, end, hotelLocation, name);
        AgendaList agendaList = new AgendaList();
        int i = 4;
        try {
            while (i < itineraryDetails.length) {
                List<Venue> venueList = new ArrayList<>();
                List<Todo> todoList = new ArrayList<>();
                final int number = Integer.parseInt(itineraryDetails[i++]);
                while (itineraryDetails[i].equals("/venue")) {
                    i++;
                    venueList.add(ApiParser.getLocationSearch(itineraryDetails[i++]));
                    StringBuilder todos = new StringBuilder();
                    if (i == itineraryDetails.length - 1 || itineraryDetails[i].matches("-?\\d+")) {
                        throw new ParseException();
                    }
                    todos.append(itineraryDetails[++i]).append("|");
                    i++;
                    while (itineraryDetails[i].equals("/and")) {
                        i++;
                        todos.append(itineraryDetails[i++]).append("|");
                        if (i >= itineraryDetails.length) {
                            break;
                        }
                    }
                    todoList = ParserStorageUtil.getTodoListFromStorage(todos.toString());
                    if (i >= itineraryDetails.length) {
                        break;
                    }
                }
                Agenda agenda = new Agenda(todoList, venueList, number);
                agendaList.add(agenda);
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new ParseException();
        }
        itinerary.setTasks(agendaList);
        return itinerary;
    }
}
