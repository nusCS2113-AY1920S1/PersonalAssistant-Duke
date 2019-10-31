package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.GetBusStopCommand;

/**
 * Parses the user inputs into suitable format for GetBusStopCommand.
 */
public class GetBusStopParser extends CommandParser {
    private String busStopNo;

    /**
     * Parses user input into busStopNo.
     * @param input The User input
     */
    public GetBusStopParser(String input) {
        busStopNo = input;
    }

    /**
     * Constructs GetBusStopCommand object.
     * @return GetBusStopCommand object
     */
    @Override
    public Command parse() {
        return new GetBusStopCommand(busStopNo);
    }
}
