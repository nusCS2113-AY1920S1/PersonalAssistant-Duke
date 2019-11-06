package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.RouteNodeAddCommand;
import sgtravel.logic.parsers.ParserUtil;
import sgtravel.model.locations.BusStop;
import sgtravel.model.locations.RouteNode;
import sgtravel.model.locations.TrainStation;

import java.util.ArrayList;

/**
 * Parses the user inputs into suitable format for RouteNodeAddCommand.
 */
public class RouteNodeAddParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int FOUR = 4;

    /**
     * Constructs the RouteNodeAddParser.
     */
    public RouteNodeAddParser(String input) {
        this.input = input;
    }

    /**
     * Parses user input and constructs a new RouteNode object.
     * @return RouteNode object.
     * @throws ParseException If RouteNode object cannot be created from user input.
     */
    private static RouteNode createRouteNode(String userInput) throws ParseException {
        try {
            String[] withinDetails = userInput.strip().split("at | with ", TWO);
            if (withinDetails.length != TWO) {
                throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
            }

            String type = userInput.substring(withinDetails[ZERO].length()).strip().substring(ZERO, FOUR);
            if (!("with".equals(type) || "at".equals(type.substring(ZERO, TWO)))) {
                throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
            }

            String[] details;
            if (type.substring(ZERO, TWO).equals("at")) {
                details = withinDetails[ONE].strip().split("by ");
                switch (details[ONE].toUpperCase()) {
                case "BUS":
                    return new BusStop(details[ZERO].strip(), null, null, ZERO, ZERO);
                case "MRT":
                    return new TrainStation(new ArrayList<>(), details[ZERO].strip(), null, ZERO, ZERO);
                default:
                    throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
                }
            } else {
                details = withinDetails[ONE].split("by ");
                String[] coordinateStrings = details[ZERO].strip().split(" ");
                assert (coordinateStrings.length == TWO);

                double[] coordinates = new double[TWO];
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
     * Parses the user input and constructs RouteNodeAddCommand object.
     * @return RouteNodeAddCommand object.
     * @throws ParseException If RouteNodeAddCommand object cannot be created.
     */
    @Override
    public Command parse() throws ParseException {
        RouteNode routeNode = createRouteNode(input);
        int firstIndex = ParserUtil.getIntegerIndexInList(ZERO, FOUR, input);
        try {
            return new RouteNodeAddCommand(routeNode, firstIndex,
                    ParserUtil.getIntegerIndexInList(ONE, FOUR, input), false);
        } catch (ParseException e) {
            return new RouteNodeAddCommand(routeNode, firstIndex, ZERO, true);
        }
    }
}
