package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddItemCommand;
import duke.model.Item;

/**
 * Parser class to handle addition of Item object to model.
 */
public class AddItemCommandParser implements ParserInterface<AddItemCommand> {

    /**
     * Parses user input and returns an AddItemCommand encapsulating a Item object.
     * @param userInputStr String input by user.
     * @return <code>AddItemCommand</code> Command object encapsulating a breakfast object
     */
    @Override
    public AddItemCommand parse(String userInputStr) {
        try {
            InputValidator.validate(userInputStr);
            String[] mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInputStr);
            return new AddItemCommand(new Item(mealNameAndInfo[0], mealNameAndInfo[1]));
        } catch (DukeException e) {
            return new AddItemCommand(false, e.getMessage());
        }
    }
}
