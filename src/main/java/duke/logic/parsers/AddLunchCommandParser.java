package duke.logic.parsers;

import duke.logic.commands.AddCommand;
import duke.commons.exceptions.DukeException;
import duke.model.Lunch;

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
    public AddCommand parse(String userInput) throws DukeException {
        if (userInput.trim().length() == 0) {
            throw new DukeException("\u2639 OOPS!!! The description of the command cannot be empty.");
        }
        if (userInput.contains("/")) {
            String mealName = userInput.split("/", 2)[0].trim();
            String mealInfo = "/" + userInput.split("/", 2)[1];
            return new AddCommand(new Lunch(mealName, mealInfo));
        } else {
            //todo: handle trailing userInput without "/"
            return new AddCommand(new Lunch(userInput, ""));
        }
    }
}
