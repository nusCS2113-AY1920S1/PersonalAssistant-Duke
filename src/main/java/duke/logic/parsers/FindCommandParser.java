package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.FindCommand;

/**
 * Parser class to handle finding an entry from the model.
 */
public class FindCommandParser implements ParserInterface<FindCommand> {

    /**
     * Parse user input and return FindCommand.
     * @param userInput String input by user.
     * @return <code>FindCommand</code> Command object encapsulating the search keywords
     * @throws DukeException If the userInput cannot be parsed
     */
    @Override
    public FindCommand parse(String userInput) throws DukeException {
        String name = userInput.split(" /date ", 2)[0];
        if (userInput.split(" /date ").length > 1) {
            String date = userInput.split(" /date ")[1];
            return new FindCommand(name, date);
        } else {
            return new FindCommand(name);
        }
    }
}
