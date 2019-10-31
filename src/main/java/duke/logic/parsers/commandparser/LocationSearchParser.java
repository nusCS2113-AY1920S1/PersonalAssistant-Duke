package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ApiException;
import duke.logic.commands.LocationSearchCommand;

/**
 * Parses the user inputs into suitable format for LocationSearchCommand.
 */
public class LocationSearchParser extends CommandParser<LocationSearchCommand> {
    private String location;

    /**
     * Parses user input into location.
     * @param input The User input
     */
    public LocationSearchParser(String input) {
        location = input;
    }

    /**
     * Constructs LocationSearchCommand object.
     * @return LocationSearchCommand object
     */
    @Override
    public LocationSearchCommand parse() throws ApiException {
        return new LocationSearchCommand(location);
    }
}
