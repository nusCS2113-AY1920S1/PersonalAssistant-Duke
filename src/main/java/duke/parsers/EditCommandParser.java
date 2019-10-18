package duke.parsers;

import duke.commands.EditCommand;
import duke.exceptions.DukeException;
import duke.tasks.Meal;

public class EditCommandParser implements ParserInterface<EditCommand> {

    @Override
    public EditCommand parse(String userInput) throws DukeException {
        String name = userInput.split("/", 2)[0];
        String info = "/" + userInput.split("/", 2)[1];
        return new EditCommand(new Meal(name, info));
    }
}
