package sgtravel.logic.parsers.commandparsers;

import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.GetBusRouteCommand;

/**
 * Parses the user inputs into suitable format for GetBusRouteCommand.
 */
public class GetBusRouteParser extends CommandParser {
    private String busServiceNo;

    /**
     * Constructs the GetBusRouteParser.
     *
     * @param input The user input.
     */
    public GetBusRouteParser(String input) {
        busServiceNo = input;
    }

    /**
     * Parses the user input and constructs GetBusRouteCommand object.
     *
     * @return GetBusRouteCommand object.
     */
    public Command parse() {
        return new GetBusRouteCommand(busServiceNo);
    }
}
