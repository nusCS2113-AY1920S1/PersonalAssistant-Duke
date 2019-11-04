package duke.logic.parsers.commandParsers;

import duke.logic.commands.Command;
import duke.logic.commands.GetBusRouteCommand;

/**
 * Parses the user inputs into suitable format for GetBusRouteCommand.
 */
public class GetBusRouteParser extends CommandParser {
    private String busServiceNo;

    /**
     * Constructs the GetBusRouteParser.
     */
    public GetBusRouteParser(String input) {
        busServiceNo = input;
    }

    /**
     * Parses the user input and constructs GetBusRouteCommand object.
     * @return GetBusRouteCommand object.
     */
    public Command parse() {
        return new GetBusRouteCommand(busServiceNo);
    }
}
