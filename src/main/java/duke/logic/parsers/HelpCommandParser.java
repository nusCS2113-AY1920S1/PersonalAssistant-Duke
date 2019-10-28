package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.HelpCommand;

/**
 * Parser class to handle the parsing of a help command.
 */
public class HelpCommandParser implements ParserInterface<HelpCommand> {

    /**
     * Parse user input and return HelpCommand.
     * @param userInput String input by user.
     * @return <code>HelpCommand</code> Command object containing the type of help sought by the user
     * @throws DukeException If the userInput cannot be parsed
     */
    @Override

    public HelpCommand parse(String userInput) {
        return new HelpCommand(userInput);
    }
}
