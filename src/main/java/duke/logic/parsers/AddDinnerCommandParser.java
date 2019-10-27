package duke.logic.parsers;

import duke.logic.commands.AddCommand;
import duke.commons.exceptions.DukeException;
import duke.model.Dinner;

/**
 * Parser class to handle addition of Dinner item to model.
 */
public class AddDinnerCommandParser implements ParserInterface<AddCommand> {

    /**
     * Parses user input and returns an AddCommand encapsulating a Dinner object.
     * @param userInput String input by user.
     * @return <code>AddCommand</code> Command object encapsulating a breakfast object
     * @throws DukeException when the user input cannot be parsed
     */
    @Override
    public AddCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInput);
        return new AddCommand(new Dinner(mealNameAndInfo[0], mealNameAndInfo[1]));
        //todo: handle trailing userInput without "/"
    }
}
