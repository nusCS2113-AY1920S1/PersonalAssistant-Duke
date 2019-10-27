package duke.logic.parsers;

import duke.logic.commands.MarkDoneCommand;
import duke.commons.exceptions.DukeException;

/**
 * Parser class to handle the marking of a meal object as done in the model.
 */
public class DoneCommandParser implements ParserInterface<MarkDoneCommand> {

    /**
     * Parse user input and return MarkDoneCommand.
     * @param userInput String input by user.
     * @return <code>MarkDoneCommand</code> Command object encapsulating the index of the entry to be marked done
     * @throws DukeException If the userInput cannot be parsed
     */
    @Override
    public MarkDoneCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] indexAndDate = ArgumentSplitter.splitArguments(userInput, "/date");
        return new MarkDoneCommand(indexAndDate[0], indexAndDate[1]);
    }
}
