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
        if (userInput.trim().length() != 0) {
            if (userInput.split("/date").length >= 2) {
                String[] splitArgs = userInput.split("/date", 2);
                return new MarkDoneCommand(splitArgs[0], splitArgs[1]);
            } else {
                return new MarkDoneCommand(userInput);
            }
        } else {
            throw new DukeException("Please enter index of meal to be marked done on today's list or "
                    + "date and index of meal to be marked done");
        }
    }
}
