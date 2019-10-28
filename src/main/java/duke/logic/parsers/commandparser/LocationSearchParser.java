package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.LocationSearchCommand;

/**
 * Parses the user inputs into suitable format for LocationSearchCommand.
 */
public class LocationSearchParser extends CommandParser<LocationSearchCommand> {
    String location;

    /**
     * Parses user input into location.
     * @param input The User input
     */
    public LocationSearchParser(String input) throws DukeException {
        location = getWord(input);
    }

    /**
     * Constructs LocationSearchCommand object.
     * @return LocationSearchCommand object
     */
    @Override
    public LocationSearchCommand parse() throws DukeException {
        return new LocationSearchCommand(location);
    }
}
