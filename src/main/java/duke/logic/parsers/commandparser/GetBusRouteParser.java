package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.GetBusRouteCommand;

/**
 * Parses the user inputs into suitable format for GetBusRouteCommand.
 */
public class GetBusRouteParser extends CommandParser {

    private String busServiceNo;

    /**
     * Parses user input into busServiceNo.
     * @param input The User input
     */
    public GetBusRouteParser(String input) {
        busServiceNo = input;
    }

    /**
     * Constructs GetBusRouteCommand object.
     * @return GetBusRouteCommand object
     */
    public Command parse() {
        return new GetBusRouteCommand(busServiceNo);
    }
}
