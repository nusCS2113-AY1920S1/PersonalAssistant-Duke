package duke.logic.parsers.commandparser;

import duke.commons.Messages;
import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.RouteNodeAddCommand;
import duke.logic.parsers.ParserUtil;
import duke.model.locations.BusStop;
import duke.model.locations.RouteNode;
import duke.model.locations.TrainStation;

import java.util.ArrayList;

/**
 * Parses the user inputs into suitable format for RouteNodeAddCommand.
 */
public class RouteNodeAddParser extends CommandParser {
    private String input;
    private RouteNode routeNode;
    private int firstIndex;

    /**
     * Parses user input into parameter for RouteNodeAddCommand.
     * @param input The User input
     */
    public RouteNodeAddParser(String input) throws ParseException {
        this.input = input;
        routeNode = createRouteNode(input);
        firstIndex = ParserUtil.getIntegerIndexInList(0, 4, input);
    }

    /**
     * Returns routeNode base on user input.
     *
     */
    public static RouteNode createRouteNode(String userInput) throws ParseException {
        try {
            String[] withinDetails = userInput.strip().split("at | with ", 2);
            if (withinDetails.length != 2) {
                throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
            }

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
     * Creates a new RouteNodeAddCommand from input, factoring for empty indexNode field.
     * @return RouteNodeAddCommand The command.
     */
    @Override
    public Command parse() {
        try {
            return new RouteNodeAddCommand(routeNode, firstIndex,
                    ParserUtil.getIntegerIndexInList(1, 4, input), false);
        } catch (ParseException e) {
            return new RouteNodeAddCommand(routeNode, firstIndex, 0, true);
        }
    }
}
