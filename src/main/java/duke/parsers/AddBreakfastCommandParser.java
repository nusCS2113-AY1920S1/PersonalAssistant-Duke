package duke.parsers;

import duke.commands.AddCommand;
import duke.exceptions.DukeException;
import duke.tasks.Breakfast;

public class AddBreakfastCommandParser implements ParserInterface<AddCommand> {

    @Override
    public AddCommand parse(String userinput) throws DukeException {
        if (userinput.trim().length() == 0) {
            throw new DukeException("\u2639 OOPS!!! The description of the command cannot be empty.");
        }
        if (userinput.contains("/")) {
            String mealName = userinput.split("/", 2)[0].trim();
            String mealInfo = "/" + userinput.split("/", 2)[1];
            return new AddCommand(new Breakfast(mealName, mealInfo));
        } else {
            //todo: handle trailing userinput without "/"
            return new AddCommand(new Breakfast(userinput, ""));
        }
    }
}
