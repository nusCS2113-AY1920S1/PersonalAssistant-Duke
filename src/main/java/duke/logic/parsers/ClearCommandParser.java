package duke.logic.parsers;

import duke.logic.commands.ClearCommand;
import duke.commons.exceptions.DukeException;

/**
 * Parser class to handle multiple deletions of meals from model.
 */
public class ClearCommandParser implements ParserInterface<ClearCommand> {

    /**
     * Parse user input and return ClearCommand.
     * @param userInput String input by user.
     * @return <code>ClearCommand</code> Command object demarcating the region of data to be deleted
     * @throws DukeException If the userInput cannot be parsed
     */
    @Override
    public ClearCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] startAndEndDates = ArgumentSplitter.splitArguments(userInput, " ");
        return new ClearCommand(startAndEndDates[0], startAndEndDates[1]);
    }
}
