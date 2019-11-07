package sgtravel.logic.parsers.commandparsers;

import sgtravel.logic.commands.LocationSearchCommand;

/**
 * Parses the user inputs into suitable format for LocationSearchCommand.
 */
public class LocationSearchParser extends CommandParser {
    private String location;

    /**
     * Constructs the LocationSearchParser.
     *
     * @param input The user input.
     */
    public LocationSearchParser(String input) {
        location = input;
    }

    /**
     * Parses the user input and constructs LocationSearchCommand object.
     *
     * @return LocationSearchCommand object.
     */
    @Override
    public LocationSearchCommand parse() {
        return new LocationSearchCommand(location);
    }
}
