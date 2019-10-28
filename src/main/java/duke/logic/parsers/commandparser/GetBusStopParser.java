package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.GetBusStopCommand;

/**
 * Parses the user inputs into suitable format for GetBusStopCommand.
 */
public class GetBusStopParser extends CommandParser {
    String busStopNo;

    /**
     * Parses user input into busStopNo.
     * @param input The User input
     */
    public GetBusStopParser(String input) throws DukeException {
        busStopNo = input;
    }

    /**
     * Constructs GetBusStopCommand object.
     * @return GetBusStopCommand object
     */
    @Override
    public Command parse() throws DukeException {
        return new GetBusStopCommand(busStopNo);
    }
}
