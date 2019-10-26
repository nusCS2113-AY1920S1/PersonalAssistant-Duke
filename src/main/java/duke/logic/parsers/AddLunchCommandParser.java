package duke.logic.parsers;

import duke.logic.commands.AddCommand;
import duke.commons.exceptions.DukeException;
import duke.model.meal.Lunch;

public class AddLunchCommandParser implements ParserInterface<AddCommand> {

    @Override
    public AddCommand parse(String userInput) {
        int cost = 0;
        if (userInput.trim().length() == 0) {
            return new AddCommand(false,"\u2639 OOPS!!! The description of the command cannot be empty.");
        }
        if (userInput.contains("/cost")) {
            cost = Integer.parseInt(userInput.split("/cost")[1].trim());
        }
        if (userInput.contains("/")) {
            String mealName = userInput.split("/", 2)[0].trim();
            String mealInfo = "/" + userInput.split("/", 2)[1];
            return new AddCommand(new Lunch(mealName, mealInfo), cost);
        } else {
            //todo: handle trailing userInput without "/"
            return new AddCommand(new Lunch(userInput, ""), cost);
        }
    }
}
