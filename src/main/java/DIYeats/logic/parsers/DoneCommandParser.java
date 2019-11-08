package DIYeats.logic.parsers;

import DIYeats.commons.exceptions.DukeException;
import DIYeats.logic.commands.MarkDoneCommand;

/**
 * Parser class to handle the marking of a meal object as done in the model.
 */
public class DoneCommandParser implements ParserInterface<MarkDoneCommand> {

    /**
     * Parse user input and return MarkDoneCommand.
     * @param userInputStr String input by user.
     * @return <code>MarkDoneCommand</code> Command object encapsulating the index of the entry to be marked done
     */
    @Override

    public MarkDoneCommand parse(String userInputStr) {
        try {
            InputValidator.validate(userInputStr);
        } catch (DukeException e) {
            return new MarkDoneCommand(false, "Please enter index of meal to be marked done on today's list or "
                    + "date and index of meal to be marked done");
        }
        String[] indexAndDate = ArgumentSplitter.splitArguments(userInputStr, "/date");
        return new MarkDoneCommand(indexAndDate[0], indexAndDate[1]);
    }
}
