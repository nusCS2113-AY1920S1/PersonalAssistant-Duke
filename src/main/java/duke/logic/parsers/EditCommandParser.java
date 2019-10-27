package duke.logic.parsers;

import duke.logic.commands.EditCommand;
import duke.commons.exceptions.DukeException;
import duke.model.meal.Meal;

/**
 * Parser class to handle editing of a entry in the model.
 */
public class EditCommandParser implements ParserInterface<EditCommand> {

    /**
     * Parse user input and return EditCommand.
     * @param userInput String input by user.
     * @return <code>EditCommand</code> Command object encapsulating the Meal object to replace the old entry
     */
    @Override

    public EditCommand parse(String userInput) {
        try {
            InputValidator.validate(userInput);
            String[] mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInput);
            return new EditCommand(new Meal(mealNameAndInfo[0], mealNameAndInfo[1]));
        } catch (DukeException e) {
            return new EditCommand(false, e.getMessage());
        }
    }
}
