package duke.parsers;

import duke.commands.EditCommand;
import duke.exceptions.DukeException;
import duke.tasks.Meal;

public class EditCommandParser implements ParserInterface<EditCommand> {

    @Override
    public EditCommand parse(String UserInput) throws DukeException {
        String name = UserInput.split("/", 2)[0];
        String info = "/" + UserInput.split("/", 2)[1];
        return new EditCommand(new Meal(name, info));
    }
}
