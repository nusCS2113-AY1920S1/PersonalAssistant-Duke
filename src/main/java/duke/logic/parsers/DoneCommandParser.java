package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.MarkDoneCommand;

/**
 * Parser class to handle the marking of a meal object as done in the model.
 */
public class DoneCommandParser implements ParserInterface<MarkDoneCommand> {

    /**
     * Parse user input and return MarkDoneCommand.
     * @param userInput String input by user.
     * @return <code>MarkDoneCommand</code> Command object encapsulating the index of the entry to be marked done
     */
    @Override

    public MarkDoneCommand parse(String userInput) {
        try {
            InputValidator.validate(userInput);
        } catch (DukeException e) {
            return new MarkDoneCommand(false, "Please enter index of meal to be marked done on today's list or "
                    + "date and index of meal to be marked done");
        }
        String[] indexAndDate = ArgumentSplitter.splitArguments(userInput, "/date");
        return new MarkDoneCommand(indexAndDate[0], indexAndDate[1]);
    }
}
