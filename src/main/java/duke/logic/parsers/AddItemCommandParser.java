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
     * @param userInput String input by user.
     * @return <code>AddItemCommand</code> Command object encapsulating a breakfast object
     * @throws DukeException when the user input cannot be parsed
     */
    public AddItemCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInput);
        return new AddItemCommand(new Item(mealNameAndInfo[0], mealNameAndInfo[1]));
    }
}
