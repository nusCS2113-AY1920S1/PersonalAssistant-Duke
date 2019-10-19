package duke.logic.parsers;

import duke.logic.commands.EditCommand;
import duke.commons.exceptions.DukeException;
import duke.model.Meal;

public class EditCommandParser implements ParserInterface<EditCommand> {

    @Override
    public EditCommand parse(String userInput) throws DukeException {
        String name = userInput.split("/", 2)[0];
        String info = "/" + userInput.split("/", 2)[1];
        return new EditCommand(new Meal(name, info));
    }
}
