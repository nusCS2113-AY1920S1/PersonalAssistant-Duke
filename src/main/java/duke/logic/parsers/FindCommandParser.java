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
        try {
            InputValidator.validate(userInput);
            String[] nameAndDate = ArgumentSplitter.splitArguments(userInput, " /date ");
            return new FindCommand(nameAndDate[0], nameAndDate[1]);
        } catch (DukeException e) {
            return new FindCommand(false, e.getMessage());
        }
    }
}
