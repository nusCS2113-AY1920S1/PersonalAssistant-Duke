package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddCommand;
import duke.model.meal.Lunch;

/**
 * Parser class to handle addition of lunch item to model.
 */
public class AddLunchCommandParser implements ParserInterface<AddCommand> {

    /**
     * Parses user input and returns an AddCommand encapsulating a Lunch object.
     * @param userInput String input by user.
     * @return <code>AddCommand</code> Command object encapsulating a breakfast object
     * @throws DukeException when the user input cannot be parsed
     */
    @Override

    public AddCommand parse(String userInput) {
        try {
            InputValidator.validate(userInput);
            String[] mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInput);
            return new AddCommand(new Lunch(mealNameAndInfo[0], mealNameAndInfo[1]), 0);
        } catch (DukeException e) {
            return new AddCommand(false, e.getMessage());
        }
    }
}
