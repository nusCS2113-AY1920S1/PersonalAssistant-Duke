package sgtravel.logic.parsers.commandparsers;

import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.GetBusStopCommand;

/**
 * Parses the user inputs into suitable format for GetBusStopCommand.
 */
public class GetBusStopParser extends CommandParser {
    private String busStopNo;

    /**
     * Constructs the GetBusStopParser.
     *
     * @param input The user input.
     */
    public GetBusStopParser(String input) {
        busStopNo = input;
    }

    /**
     * Parses the user input and constructs GetBusStopCommand object.
     *
     * @return GetBusStopCommand object.
     */
    @Override
    public Command parse() {
        return new GetBusStopCommand(busStopNo);
    }
}
