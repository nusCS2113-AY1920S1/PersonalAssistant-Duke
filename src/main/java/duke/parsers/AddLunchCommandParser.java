package duke.parsers;

import duke.commands.AddCommand;
import duke.exceptions.DukeException;
import duke.tasks.Lunch;

public class AddLunchCommandParser implements ParserInterface<AddCommand> {

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
