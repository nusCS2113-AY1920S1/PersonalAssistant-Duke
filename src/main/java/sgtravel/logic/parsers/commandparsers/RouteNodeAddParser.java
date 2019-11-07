package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.ApiException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.api.requests.LocationSearchRequest;
import sgtravel.logic.api.requests.LocationSearchUrlRequest;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.RouteNodeAddCommand;
import sgtravel.logic.parsers.ParserUtil;
import sgtravel.model.locations.BusStop;
import sgtravel.model.locations.CustomNode;
import sgtravel.model.locations.RouteNode;
import sgtravel.model.locations.TrainStation;
import sgtravel.model.locations.Venue;

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
     *
     * @return RouteNode object.
     * @throws ParseException If RouteNode object cannot be created from user input.
     */
    private static RouteNode createRouteNode(String userInput) throws ParseException {
        try {
            String[] withinDetails = userInput.strip().split("at", TWO);
            if (withinDetails.length != TWO) {
                throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
            }

            String type = userInput.substring(withinDetails[ZERO].length()).strip().substring(ZERO, FOUR);
            if (!"at".equals(type.substring(ZERO, TWO))) {
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
                case "CUSTOM":
                    return createCustomNode(details[ZERO].strip());
                default:
                    throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
                }
            }
        } catch (ArrayIndexOutOfBoundsException | ApiException e) {
            throw new ParseException(Messages.ERROR_OBJECT_NOT_CREATED);
        }

        throw new ParseException(Messages.ERROR_OBJECT_NOT_CREATED);
    }

    /**
     * Creates a new CustomNode with either LocationSearchUrlRequest or LocationSearchRequest.
     *
     * @param location The name of the location.
     * @return node The CustomNode object.
     * @throws ApiException If the CustomNode cannot be created.
     */
    private static CustomNode createCustomNode(String location) throws ApiException {
        Venue venue;
        try {
            venue = new LocationSearchUrlRequest(location).execute();
        } catch (ApiException e) {
            LocationSearchRequest locationSearchRequest = new LocationSearchRequest();
            venue = locationSearchRequest.search(location);
        }

        return new CustomNode(venue);
    }

    /**
     * Parses the user input and constructs RouteNodeAddCommand object.
     * @return The RouteNodeAddCommand object.
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
