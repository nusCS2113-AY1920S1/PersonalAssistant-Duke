package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ApiException;
import duke.logic.commands.Command;
import duke.logic.commands.StaticMapCommand;

/**
 * Parses the user inputs into suitable format for StaticMapCommand.
 */
public class StaticMapParser extends CommandParser {
    private String location;

    /**
     * Parses user input into location.
     * @param input The User input
     */
    public StaticMapParser(String input) {
        this.location = input;
    }

    /**
     * Constructs StaticMapCommand object.
     * @return StaticMapCommand object
     */
    @Override
    public Command parse() {
        return new StaticMapCommand(location);
    }
}
