package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.FindCommand;

/**
 * Parses the user inputs into suitable format for FindCommand.
 */
public class FindParser extends CommandParser {
    String keyword;

    /**
     * Parses user input into keyword.
     * @param input The User input
     */
    public FindParser(String input) throws DukeException {
        keyword = input;
    }

    /**
     * Constructs FindCommand object.
     * @return FindCommand object
     */
    public Command parse() throws DukeException {
        return new FindCommand(keyword);
    }
}
