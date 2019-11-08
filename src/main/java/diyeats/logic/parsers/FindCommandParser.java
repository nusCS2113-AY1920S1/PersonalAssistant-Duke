package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.FindCommand;

/**
 * Parser class to handle finding an entry from the model.
 */
public class FindCommandParser implements ParserInterface<FindCommand> {

    /**
     * Parse user input and return FindCommand.
     * @param userInputStr String input by user.
     * @return <code>FindCommand</code> Command object encapsulating the search keywords
     * @throws ProgramException If the userInput cannot be parsed
     */
    @Override
    public FindCommand parse(String userInputStr) throws ProgramException {
        try {
            InputValidator.validate(userInputStr);
            String[] nameAndDate = ArgumentSplitter.splitArguments(userInputStr, " /date ");
            return new FindCommand(nameAndDate[0], nameAndDate[1]);
        } catch (ProgramException e) {
            return new FindCommand(false, e.getMessage());
        }
    }
}
