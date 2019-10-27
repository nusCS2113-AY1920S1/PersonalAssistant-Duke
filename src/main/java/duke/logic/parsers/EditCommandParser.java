package duke.logic.parsers;

import duke.logic.commands.EditCommand;
import duke.commons.exceptions.DukeException;
import duke.model.Meal;

/**
 * Parser class to handle editing of a entry in the model.
 */
public class EditCommandParser implements ParserInterface<EditCommand> {

    /**
     * Parse user input and return EditCommand.
     * @param userInput String input by user.
     * @return <code>EditCommand</code> Command object encapsulating the Meal object to replace the old entry
     * @throws DukeException If the userInput cannot be parsed
     */
    @Override
    public EditCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInput);
        return new EditCommand(new Meal(mealNameAndInfo[0], mealNameAndInfo[1]));
    }
}
