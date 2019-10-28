package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ApiFailedRequestException;
import duke.commons.exceptions.ApiNullRequestException;
import duke.commons.exceptions.ApiTimeoutException;
import duke.commons.exceptions.DukeException;
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
    public StaticMapParser(String input) throws DukeException {
        this.location = input;
    }

    /**
     * Constructs StaticMapCommand object.
     * @return StaticMapCommand object
     */
    @Override
    public Command parse() throws ApiFailedRequestException, ApiTimeoutException, ApiNullRequestException {
        return new StaticMapCommand(location);
    }
}
